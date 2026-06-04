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
import com.google.android.material.bottomnavigation.BottomNavigationView

class MateriasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materias)

        val lvMaterias = findViewById<ListView>(R.id.lvMaterias)
        val materias = resources.getStringArray(R.array.materias_sexto)

        val adapter = object : ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1, materias
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                view.findViewById<TextView>(android.R.id.text1).setTextColor(Color.WHITE)
                return view
            }
        }
        lvMaterias.adapter = adapter

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavMaterias)
        bottomNav.selectedItemId = R.id.nav_materias
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_alumnos -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_materias -> true
                R.id.nav_nombres -> {
                    startActivity(Intent(this, NombresActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
