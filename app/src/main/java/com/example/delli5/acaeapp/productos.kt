package com.example.delli5.acaeapp

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.delli5.acaeapp.adaptadores.AdaptadorInventario
import com.example.delli5.acaeapp.aplicativos.Cargando
import com.example.delli5.acaeapp.database.base
import com.example.delli5.acaeapp.funciones.funciones
import com.example.delli5.acaeapp.modelos.InventarioModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import org.jetbrains.anko.support.v4.uiThread
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [productos.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [productos.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class productos : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var listado:ArrayList<InventarioModel>? = ArrayList()
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private val f=funciones()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ListarProductos(activity!!.applicationContext)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val vista:View=inflater.inflate(R.layout.fragment_productos, container, false)

        mRecyclerView = vista.findViewById(R.id.listador)


        // Inflate the layout for this fragment
        return vista

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment productos.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            productos().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    fun ListarProductos(context: Context){
    /*var dialogo=Cargando.progressDialog(context)
        dialogo.show() */

        doAsync {




            val url: String = "http://192.168.1.8:8888/webservice/index.php"
            val trafico = URL(url)
            var res=""
            with(trafico.openConnection() as HttpURLConnection){

                requestMethod = "GET"


                println("URL : $url")
                println("Response Code : $responseCode")
                BufferedReader(InputStreamReader(inputStream) as Reader?).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()
                    res= response.toString() //convierte la respuesta json en una cadena para su posterios tratamiento
                }



            }

            var obje: JSONObject= JSONObject(res) //convierto la respuesta en un arreglo de JSON
            var data=obje.get("data").toString()
            var arreglo1=JSONArray(data)
            val total:Int=arreglo1.length()
            val progreso:Int=(total/100).toInt()

           // var actualizar=Cargando.ActualizarCarga(0)

            var arreglo:JSONObject?=null
            for (i in 0..(arreglo1.length() - 1)) {  //Recorre el jsonarray
                val item = arreglo1.getJSONObject(i) //obtiene el primer dato del array json
                var p=item.get("Precio_iva").toString().toDouble()
                var precio="%.2f".format(p)
                var nuevo="$"+precio
                var imagen=f.TransformarImagen(item.get("Foto_imagen").toString(),context)
                var lista:InventarioModel?= InventarioModel(
                    item.get("Id").toString().toInt(),
                    item.get("Codigo").toString(),
                    item.get("Codigo_de_barra").toString(),
                    item.get("Descripcion").toString(),
                    nuevo,
                    imagen


                )

                if (lista != null) {
                    listado!!.add(lista)
                   //Cargando.ActualizarCarga(progreso)
                }



            }


                var p=base(context)
               p.InsertarInventario(listado!!,context)






            uiThread {


                var mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                mRecyclerView!!.layoutManager = mLayoutManager
                println(listado)
                mAdapter = AdaptadorInventario(listado)
                mRecyclerView!!.adapter = mAdapter
               // dialogo.dismiss()



            }

        }
    }



}
