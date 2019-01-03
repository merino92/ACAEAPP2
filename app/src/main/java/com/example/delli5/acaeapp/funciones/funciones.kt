package com.example.delli5.acaeapp.funciones

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v4.content.ContextCompat.getSystemService
import java.io.File
import java.util.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.os.Build
import android.support.v4.content.ContextCompat
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import android.util.Base64
import com.example.delli5.acaeapp.R
import android.graphics.drawable.BitmapDrawable




class funciones {


      fun Conexion(context: Context):Boolean {
          val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
          return if(connectivityManager is ConnectivityManager){
              val informacion=connectivityManager.activeNetworkInfo
              informacion.isConnected

          }else false



     }

    fun TransformarImagen(imagen:String,context: Context): Bitmap? {


            // var imagenes=imagen.split(',')[1]
            val baos = ByteArrayOutputStream() //creo un inicilizador del decodificador
            var imageBytes = Base64.decode(imagen, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            return decodedImage

    }





}

