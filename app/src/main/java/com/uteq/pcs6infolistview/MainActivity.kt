package com.uteq.pcs6infolistview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.uteq.pcs6infolistview.utils.SupabaseErrorHandler
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var actvSemestre: AutoCompleteTextView
    private lateinit var actvMaterias: AutoCompleteTextView
    private lateinit var lvAlumnos: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar  = findViewById(R.id.progressBar)
        actvSemestre = findViewById(R.id.actvSemestre)
        actvMaterias = findViewById(R.id.actvMaterias)
        lvAlumnos    = findViewById(R.id.lvAlumnos)

        val listaSemestre = resources.getStringArray(R.array.semestres)
        val adapterSemestre = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item, listaSemestre)
        actvSemestre.setAdapter(adapterSemestre)

        val listaMaterias = resources.getStringArray(R.array.materias_sexto)
        val adapterMaterias = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item, listaMaterias)
        actvMaterias.setAdapter(adapterMaterias)

        actvSemestre.setOnItemClickListener { _, _, position, _ ->
            val itemSeleccionado = listaSemestre[position]
            Toast.makeText(this, "Semestre seleccionado: $itemSeleccionado", Toast.LENGTH_SHORT).show()
        }

        actvMaterias.setOnItemClickListener { _, _, _, _ ->
            cargarAlumnos()
        }

        cargarAlumnos()

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_alumnos -> true
                R.id.nav_materias -> {
                    startActivity(Intent(this, MateriasActivity::class.java))
                    true
                }
                R.id.nav_nombres -> {
                    startActivity(Intent(this, NombresActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun cargarAlumnos() {
        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE
            var lstAlumnos = ArrayList<Alumno>()
            try {
                lstAlumnos = ArrayList(
                    SupabaseManager.client
                        .from("alumnos")
                        .select { order("nombres", Order.ASCENDING) }
                        .decodeList<Alumno>()
                )
            } catch (e: RestException) {
                SupabaseErrorHandler.show(this@MainActivity, e)
            } finally {
                progressBar.visibility = View.GONE
                val adapter = AlumnoAdapter(this@MainActivity, lstAlumnos)
                lvAlumnos.adapter = adapter
            }
        }
    }
}
