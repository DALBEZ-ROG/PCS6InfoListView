package com.uteq.pcs6infolistview

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Alumno(
    @SerialName("id")
    val id: Long,
    @SerialName("cedula")
    val cedula: String? = null,
    @SerialName("apellidos_nombres")
    val apellidosNombres: String? = null,
    @SerialName("correo_institucional")
    val correoInstitucional: String? = null,
    @SerialName("correo_microsoft")
    val correoMicrosoft: String? = null
)
