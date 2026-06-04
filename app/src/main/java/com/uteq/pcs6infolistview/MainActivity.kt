package com.uteq.pcs6infolistview

import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var listViewAlumnos: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar     = findViewById(R.id.progressBar)
        listViewAlumnos = findViewById(R.id.listViewAlumnos)

        cargarAlumnos()
    }

    private fun cargarAlumnos() {
        lifecycleScope.launch {
            try {
                progressBar.visibility = View.VISIBLE
                val alumnos = SupabaseManager.client
                    .from("Alumnos")
                    .select()
                    .decodeList<Alumno>()
                listViewAlumnos.adapter = AlumnoAdapter(this@MainActivity, alumnos)
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
