package com.example.delli5.acaeapp.modelos

import android.graphics.Bitmap

data class ClienteModel(
    var idcliente:Int?,
    var Codigo:Int?,
    var nombre_cliente:String?,
    var apellido_cliente:String?,
    var direccion:String?,
    var telefono:String?,
    var departamento:String?,
    var municipio:String?,
    var imagen:Bitmap?,
    var coordenadas:String?
)