package com.uteq.pcs6infolistview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import java.security.MessageDigest

class AlumnoAdapter(
    private val context: Context,
    private val alumnos: List<Alumno>
) : BaseAdapter() {

    override fun getCount(): Int = alumnos.size

    override fun getItem(position: Int): Alumno = alumnos[position]

    override fun getItemId(position: Int): Long = alumnos[position].id

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val view: View

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_alumno, parent, false)
            holder = ViewHolder(
                imgAvatar = view.findViewById(R.id.imgAvatar),
                tvNombre  = view.findViewById(R.id.tvNombre),
                tvCorreo  = view.findViewById(R.id.tvCorreo),
                tvCedula  = view.findViewById(R.id.tvCedula)
            )
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val alumno = alumnos[position]
        holder.tvNombre.text  = alumno.apellidosNombres  ?: ""
        holder.tvCorreo.text  = alumno.correoInstitucional ?: ""
        holder.tvCedula.text  = alumno.cedula ?: ""

        val email = alumno.correoInstitucional?.lowercase() ?: ""
        val hash = md5(email)
        val gravatarUrl = "https://www.gravatar.com/avatar/$hash?d=identicon&s=100"

        Glide.with(context)
            .load(gravatarUrl)
            .transform(CircleCrop())
            .into(holder.imgAvatar)

        return view
    }

    private fun md5(input: String): String {
        val digest = MessageDigest.getInstance("MD5").digest(input.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }

    private data class ViewHolder(
        val imgAvatar: ImageView,
        val tvNombre: TextView,
        val tvCorreo: TextView,
        val tvCedula: TextView
    )
}
