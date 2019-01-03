package com.example.delli5.acaeapp

import android.app.ActionBar
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.UiThread
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import com.example.delli5.acaeapp.funciones.funciones
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpPost
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import khttp.responses.Response
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import org.jetbrains.anko.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.transform.Result


class Login : AppCompatActivity() {
    lateinit var usuario: EditText
    lateinit var clave: EditText
    lateinit var inter: funciones
    var contador: Int = 0
    val preferencias = "acae"
    var editor: SharedPreferences? = null
    var cheque:Switch?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editor = this.getSharedPreferences(
            preferencias,
            0
        ) //se instancia las preferencias donde se almacenara el usuario y clave en contexto 0=privado

        var preferencias_vacias=editor!!.getString("usuario",null)
            if(preferencias_vacias!=null){

                val intent = Intent(this@Login, inicio::class.java)
                startActivity(intent)
            }

            val barra: android.support.v7.app.ActionBar? = supportActionBar
            if (barra != null) {
                barra.hide()
            }
            inter = funciones()
            usuario = textousuario
            clave = textoclave
           barraprogreso.visibility=View.GONE
            cheque = chequearme

            val internet = inter.Conexion(this)
            if (internet != true) {

                Toast.makeText(this, "Debes poseer una conexion a internet para poder usar la app", Toast.LENGTH_LONG)
                    .show()
            }

    }

   fun Ingresar(view: View) {
        var res = inter.Conexion(this)
        var vacio = CamposVacios()

        if (vacio == false) {

            Toast.makeText(this, "Por favor ingrea tu usuario o clave", Toast.LENGTH_SHORT).show()

        } else {

            if (res == true) {

                ValidacionUsuario()
            } else {
                Toast.makeText(this, "Verifica tu conexion a internet", Toast.LENGTH_LONG).show()
            }
        } //validacion de campos vacios


    } //valida que haya conexion a internet antes de hacer el logueo

    private fun CamposVacios(): Boolean {
        var respuesta: Boolean = true
        if (usuario.text.toString() == null || usuario.text.toString() == "") {
            respuesta = false

        } else if (clave.text.toString() == null || clave.text.toString() == "") {
            respuesta = false
        }

        return respuesta
    }//valida que los campos no esten vacios

    private fun ValidacionUsuario() {
        textousuario.visibility = View.GONE
        textoclave.visibility = View.GONE

        btningresar.visibility = View.GONE
        barraprogreso.visibility = View.VISIBLE
        cheque!!.visibility = View.GONE

        doAsync {


            val objecto: JSONObject = JSONObject()
            objecto.put("funcion",1)
            objecto.put("usuario", usuario.text.toString())
            objecto.put("clave", clave.toString())
            //creacion del json para interactuar con el servicio
            val url: String = "http://192.168.1.8:8888/webservice/"
            val trafico = URL(url)
            var res = ""
            with(trafico.openConnection() as HttpURLConnection) {

                requestMethod = "POST"

                val wr = OutputStreamWriter(getOutputStream())
                wr.write(objecto.toString())
                wr.flush()
                println("URL : $url")
                println("Response Code : $responseCode")
                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()
                    res = response.toString()
                }


            }

            uiThread {

                var objecto: JSONArray = JSONArray(res)
                if (objecto.length() > 0) {

                    if(chequearme.isChecked){
                        editor!!.edit().putString("usuario",usuario.text.toString())
                        editor!!.edit().putString("clave",clave.text.toString())
                        editor!!.edit().apply()
                        //se guarda el usuario y la clave para posterior uso en la aplicacion
                    } //valida si el usuario desea recordar su usuario y  clave

                    val intent = Intent(this@Login, inicio::class.java)
                    startActivity(intent)
                } else {
                    toast("Usuario o clave invalida")
                }


            }

        }


    }

    private fun Alerta(view: View){
        Toast.makeText(this,"Comunicate con el administrador de sistema para obtener una nueva clave e usuario",Toast.LENGTH_LONG).show()

    } //muestra una alerta en el area de acerca de



}









