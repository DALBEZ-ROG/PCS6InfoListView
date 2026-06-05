package com.uteq.pcs6infolistview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class AlumnoAdapter(context: Context, var alumnos: ArrayList<Alumno>) :
    ArrayAdapter<Alumno>(context, R.layout.item_alumno, alumnos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_alumno, parent, false)

        val alumno = alumnos[position]

        val imgAvatar  = view.findViewById<ImageView>(R.id.imgAvatar)
        val txtNombre  = view.findViewById<TextView>(R.id.txtNombre)
        val txtCorreo  = view.findViewById<TextView>(R.id.txtCorreo)
        val txtTelefono = view.findViewById<TextView>(R.id.txtTelefono)

        txtNombre.text   = alumno.nombres
        txtCorreo.text   = alumno.correo
        txtTelefono.text = alumno.telefono

        val urlFoto = "https://sga.uteq.edu.ec" + (alumno.foto ?: "")
        Glide.with(context)
            .load(urlFoto)
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_person)
            .transform(CircleCrop())
            .into(imgAvatar)

        return view
    }
}
