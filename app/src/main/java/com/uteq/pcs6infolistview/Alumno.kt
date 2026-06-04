package com.uteq.pcs6infolistview

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Alumno(
    val id: Long,
    @SerialName("foto")
    val foto: String? = null,
    @SerialName("nombres")
    val nombres: String? = null,
    @SerialName("correo")
    val correo: String? = null,
    @SerialName("paralelo")
    val paralelo: String? = null,
    @SerialName("telefono")
    val telefono: String? = null
)
