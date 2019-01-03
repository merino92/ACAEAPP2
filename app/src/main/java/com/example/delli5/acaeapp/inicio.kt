package com.example.delli5.acaeapp

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_inicio.*
import kotlinx.android.synthetic.main.app_bar_inicio.*
import org.jetbrains.anko.toast
import android.provider.MediaStore
import android.content.Intent
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v4.content.FileProvider
import android.os.Build
import android.R.attr.path
import android.os.Environment
import java.io.File.separator
import android.os.Environment.getExternalStorageDirectory
import java.io.File
import java.nio.file.Files.exists




class inicio : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener ,
    estadisticas.OnFragmentInteractionListener, pedidos.OnFragmentInteractionListener,productos.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }//funcion que sirve para la interaccion de el fragmento con la actividad

    val manage=supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        setSupportActionBar(toolbar)

        fab1.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        val menu_flotante=findViewById<View>(R.id.fab1)
        menu_flotante.visibility=View.GONE


        nav_view.setNavigationItemSelectedListener(this)
        //retorno la vista del menu

        val transiscion=manage.beginTransaction()
        val fragmento= estadisticas()
        transiscion.add(R.id.estadis,fragmento)
        transiscion.addToBackStack(null)
        transiscion.commit()

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.inicio, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val menu_flotante=findViewById<View>(R.id.fab1)
        //menu_flotante.visibility=View.GONE
        when (item.itemId) {
            R.id.inicio -> {
                val transiscion=manage.beginTransaction()
                val fragmento= estadisticas()
                transiscion.replace(R.id.estadis,fragmento)
                transiscion.addToBackStack(null)
                transiscion.commit() //muestra el fragmento de estadistica
                menu_flotante.visibility=View.GONE //esconde el menu flotante
            }
            R.id.pedido-> {

                val transiscion=manage.beginTransaction()
                val fragmento= pedidos()
                transiscion.replace(R.id.estadis,fragmento)
                transiscion.addToBackStack(null)
                transiscion.commit()
                menu_flotante.visibility=View.VISIBLE
                //CAMBIA LA PANTALLA POR EL NUEVO MENU
            }
            R.id.lista_producto -> {
                val transiscion=manage.beginTransaction()
                val fragmento= productos()
                transiscion.replace(R.id.estadis,fragmento)
                transiscion.addToBackStack(null)
                transiscion.commit()
                menu_flotante.visibility=View.VISIBLE
                //CAMBIA LA PANTALLA POR EL NUEVO MENU
            }
            R.id.saldo-> {

            }
            R.id.his ->{

            }

            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun CambiarImagen(view: View){




    }



}
