package com.uteq.pcs6infolistview

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.uteq.pcs6infolistview.utils.SupabaseErrorHandler
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.launch

class NombresActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nombres)

        val lvNombres = findViewById<ListView>(R.id.lvNombres)

        lifecycleScope.launch {
            var lstNombres = ArrayList<String>()
            try {
                val alumnos = ArrayList(
                    SupabaseManager.client
                        .from("alumnos")
                        .select { order("nombres", Order.ASCENDING) }
                        .decodeList<Alumno>()
                )
                for (alumno in alumnos) {
                    lstNombres.add(alumno.nombres ?: "")
                }
            } catch (e: RestException) {
                SupabaseErrorHandler.show(this@NombresActivity, e)
            } finally {
                val adapter = object : ArrayAdapter<String>(
                    this@NombresActivity, android.R.layout.simple_list_item_1, lstNombres
                ) {
                    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                        val view = super.getView(position, convertView, parent)
                        view.findViewById<TextView>(android.R.id.text1).setTextColor(Color.WHITE)
                        return view
                    }
                }
                lvNombres.adapter = adapter
            }
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavNombres)
        bottomNav.selectedItemId = R.id.nav_nombres
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_alumnos -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_materias -> {
                    startActivity(Intent(this, MateriasActivity::class.java))
                    true
                }
                R.id.nav_nombres -> true
                else -> false
            }
        }
    }
}
