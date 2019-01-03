package com.example.delli5.acaeapp.aplicativos

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.delli5.acaeapp.R

class Cargando {

    companion object {
        var vista:View?=null
        fun progressDialog(context: Context): AlertDialog {
            val builder = AlertDialog.Builder(context)
            builder.setCancelable(false) // if you want user to wait for some process to finish,
            builder.setView(R.layout.barra_carga)
            val dialog = builder.create()
            return dialog
        } //retorna el dialogo de carga
        fun ActualizarCarga(dato:Int){

            var dato=vista!!.findViewById<TextView>(R.id.txtcargando) as TextView

            dato.text="Cargando $dato%"



        }//actualiza el porcentaje de carga

    }


}