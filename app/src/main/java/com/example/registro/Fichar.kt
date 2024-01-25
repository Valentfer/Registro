package com.example.registro

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Fichar : AppCompatActivity() {
    lateinit var imageButtonEntradaM: ImageButton
    lateinit var imageButtonSalidaM: ImageButton
    lateinit var imageButtonEntradaT: ImageButton
    lateinit var imageButtonSalidaT: ImageButton
    lateinit var requestQueue: RequestQueue
    var nombre: String? = null
    var apellidos: String? = null
    var dni: String? = null
    var hora_salida: String? = null
    var dniuser: String? = null
    var nombreuser: String? = null
    var apellidosuser: String? = null
    var fecha = Date()
    var dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    var hourFormat: DateFormat = SimpleDateFormat("HH:mm:ss")
    var hour = Date()
    var c: Calendar = GregorianCalendar()
    var hora = 0
    var minutos = 0
    var segundos = 0
    var ok = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fichar)
        window.statusBarColor = Color.parseColor("#DC3545")
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#DC3545")))
        imageButtonEntradaM = findViewById(R.id.btnEntradaM)
        imageButtonEntradaT = findViewById(R.id.btnEntradaT)
        imageButtonSalidaM = findViewById(R.id.btnSalidaM)
        imageButtonSalidaT = findViewById(R.id.btnSalidaT)
        hora_salida = hourFormat.format(hour)
        val sharedPreferences = getSharedPreferences("datos", MODE_PRIVATE)
        dniuser = sharedPreferences.getString("dniuser", dni)
        nombreuser = sharedPreferences.getString("nombreuser", nombre)
        apellidosuser = sharedPreferences.getString("apellidosuser", apellidos)
        supportActionBar!!.title = "$nombreuser $apellidosuser"
        imageButtonSalidaM.setOnClickListener {
            Log.e("myinfo", "has pulsado salida M")
            if (!ejecutarSalidaM()) {
                Toast.makeText(
                    this,
                    "HAS FICHADO LA HORA DE SALIDA: " + setHora(),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(this, "YA HAS FICHADO", Toast.LENGTH_LONG).show()
            }
        }
        imageButtonEntradaM.setOnClickListener {
            Log.e("myinfo", "has pulsado entrada M")
            ejecutarEntradaM()
        }
        imageButtonSalidaT.setOnClickListener {
            Log.e("myinfo", "has pulsado salida T")
            ejecutarSalidaT()
            Toast.makeText(
                this,
                "HAS FICHADO LA HORA DE SALIDA: " + setHora(),
                Toast.LENGTH_LONG
            ).show()
        }
        imageButtonEntradaT.setOnClickListener {
            Log.e("myinfo", "has pulsado entrada T")
            ejecutarEntradaT()
            Toast.makeText(
                this,
                "HAS FICHADO LA HORA DE ENTRADA: " + setHora(),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    //SALIDA
    private fun ejecutarSalidaM(): Boolean {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, urlSalidaM,
            Response.Listener {
                ok = true
                println(hora_salida)
            },
            Response.ErrorListener { error -> Log.e("myifno", error.toString()) }) {

            override fun getParams(): Map<String, String> {
                val parametros: MutableMap<String, String> = HashMap()
                parametros["dni"] = dniuser!!
                parametros["fecha"] = dateFormat.format(fecha)
                parametros["hora_salida"] = setHora()
                return parametros
            }
        }
        requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
        return ok
    }

    private fun ejecutarSalidaT() {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, urlSalidaT,
            Response.Listener { Log.e("myifno", "yes") },
            Response.ErrorListener { error -> Log.e("myifno", error.toString()) }) {

            override fun getParams(): Map<String, String> {
                val parametros: MutableMap<String, String> = HashMap()
                parametros["dni"] = dniuser!!
                parametros["fecha"] = dateFormat.format(fecha)
                parametros["hora_salida_tarde"] = setHora()
                return parametros
            }
        }
        requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    //ENTRADA
    private fun ejecutarEntradaM() {
        requestQueue = Volley.newRequestQueue(this)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, urlEntradaM,
            Response.Listener {
                Toast.makeText(
                    this,
                    "HAS FICHADO LA HORA DE ENTRADA: " + setHora(),
                    Toast.LENGTH_LONG
                ).show()
            },
            Response.ErrorListener { error -> Log.e("myifno", error.toString()) }) {

            override fun getParams(): Map<String, String> {
                val parametros: MutableMap<String, String> = HashMap()
                parametros["dni"] = dniuser!!
                parametros["hora_entrada"] = setHora()
                parametros["fecha"] = dateFormat.format(fecha)
                return parametros
            }
        }
        requestQueue.add(stringRequest)
    }

    private fun ejecutarEntradaT() {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, urlEntradaT,
            Response.Listener { Log.e("myifno", "yes") },
            Response.ErrorListener { error -> Log.e("myifno", error.toString()) }) {

            override fun getParams(): Map<String, String> {
                val parametros: MutableMap<String, String> = HashMap()
                parametros["dni"] = dniuser!!
                parametros["fecha"] = dateFormat.format(fecha)
                parametros["hora_entrada_tarde"] = setHora()
                return parametros
            }
        }
        requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    fun setHora(): String {
        c.time = Date()
        hora = c[Calendar.HOUR_OF_DAY]
        minutos = c[Calendar.MINUTE]
        segundos = c[Calendar.SECOND]
        return "$hora:$minutos:$segundos"
    }

    companion object {
        private const val urlSalidaM =
        "http://paginaweb/insertarsalidaM.php"
    private const val urlSalidaT =
        "http://paginaweb/insertarsalidaT.php"
        private const val urlEntradaM =
             "http://paginaweb/insertarentradaM.php"
        private const val urlEntradaT =
          "http://paginaweb/insertarentradaT.php"
    }
}
