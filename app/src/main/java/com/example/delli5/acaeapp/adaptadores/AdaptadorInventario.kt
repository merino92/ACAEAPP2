package com.example.delli5.acaeapp.adaptadores

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.delli5.acaeapp.R
import com.example.delli5.acaeapp.modelos.InventarioModel

class AdaptadorInventario(private val lista: ArrayList<InventarioModel>?):RecyclerView.Adapter<AdaptadorInventario.MyViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AdaptadorInventario.MyViewHolder {
       val vista=LayoutInflater.from(p0.context).inflate(R.layout.card_producto,p0,false)
        return MyViewHolder(vista)
    } //infla la carta donde se mostrara el contenido de cada producto

    override fun getItemCount(): Int {

            return lista!!.size

    } //retorna la talla de la lista

    override fun onBindViewHolder(vista: AdaptadorInventario.MyViewHolder, conta: Int) {

        vista.nombre_producto.text= lista!![conta].descripcion
        vista.codigo.text=lista[conta].codigo
        vista.precio.text=lista[conta].precio_iva.toString()
        if(lista[conta].Imagen==null ){
         vista.Imagen.setImageResource(R.drawable.no_disponible)
        }else {
            vista.Imagen.setImageBitmap(lista[conta].Imagen)
        }
    } //asigna los datos a sus respectivos textos

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var nombre_producto: TextView
        internal var codigo:TextView
        internal  var precio:TextView
        internal var Imagen:ImageView

        init {
            nombre_producto = itemView.findViewById<View>(R.id.titulo) as TextView
            codigo =itemView.findViewById<View>(R.id.descripcion)  as TextView
            precio=itemView.findViewById<View>(R.id.texto2) as TextView
            Imagen=itemView.findViewById(R.id.imagen) as ImageView
        }
    }
}