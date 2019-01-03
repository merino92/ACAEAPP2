package com.example.delli5.acaeapp.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.delli5.acaeapp.modelos.InventarioModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

private var DB_PATH = "/data/data/com.example.delli5.acaeapp.database/databases/" //ruta por defecto donde se almacena la bd en el android

//private val DB_NAME = "acae.s3db" //nombre de nuestra base

private val buscador: SQLiteDatabase.CursorFactory? = null
private val curson:SQLiteDatabase.CursorFactory?=null

class base (var context:Context): SQLiteOpenHelper(context, DB_NAME, curson,1) {

    companion object {

        private val DB_NAME = "databases/acae.s3db"
    }

    fun openDatabase(): SQLiteDatabase {
        val dbFile = context.getDatabasePath(DB_NAME)


        if (!dbFile.exists()) {
            try {
                val checkDB = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,null)

                checkDB?.close()
                copyDatabase(dbFile)
            } catch (e: IOException) {
                throw RuntimeException("Error creating source database", e)
            }

        }
        return SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    @SuppressLint("WrongConstant")
    private fun copyDatabase(dbFile: File) {
        val `is` = context.assets.open(DB_NAME)
        val os = FileOutputStream(dbFile)

        val buffer = ByteArray(1024)
        while (`is`.read(buffer) > 0) {
            os.write(buffer)
            Log.d("#DB", "writing>>")
        }

        os.flush()
        os.close()
        `is`.close()
        Log.d("#DB", "completed..")
    }


    fun InsertarInventario(listado: ArrayList<InventarioModel>,context: Context): Int {

        val adb = CopiarDatabase(context)
        val db=adb.openDatabase()
        var contador = 0

        for (i in listado) {
            var nuevoprecio= i.precio_iva!!.split("$")
            var data = ContentValues()
            data.put("id_inventario", i.idinventario)
            data.put("cod_producto", i.codigo)
            data.put("codigo_barra", i.codigo_barra)
            data.put("descripcion", i.descripcion)
            data.put("precio", nuevoprecio[1].toDouble())
            var resultado = db.insert("inventario", null, data)

            if (resultado == -1.toLong()) {

                contador++
                break
            }
        } //funcion que inserta los datos recibidos en la bd
        db.close()

        return contador


    }



    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


}