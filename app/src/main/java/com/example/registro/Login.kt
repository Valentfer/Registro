package com.example.registro

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class Login : AppCompatActivity() {
    var textDNI: EditText? = null
    var txtNombre: EditText? = null
    var butonbs: Button? = null
    var radioButton: RadioButton? = null
    var requestQueue: RequestQueue? = null
    var dni: String? = null
    var nombre: String? = null
    var apellidos: String? = null
    private var activado = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (obteneresadobutton()) {
            val intent = Intent(this, Fichar::class.java)
            startActivity(intent)
            finish()
        }
        requestQueue = Volley.newRequestQueue(this)
        textDNI = findViewById<View>(R.id.editxtDNI) as EditText
        butonbs = findViewById<View>(R.id.btnini) as Button
        radioButton = findViewById<View>(R.id.radioButton) as RadioButton
        activado = radioButton!!.isChecked
        butonbs!!.setOnClickListener {

            buscar("http://web/busqueda.php?dni='" + textDNI!!.text + "'")
        }
        window.statusBarColor = Color.parseColor("#DC3545")
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#DC3545")))
        radioButton!!.setOnClickListener {
            if (activado) {
                radioButton!!.isChecked = false
            }
            activado = radioButton!!.isChecked
        }
    }

    fun guardarestadobutton() {
        val preferences = getSharedPreferences("preferencias", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("estadobutton", radioButton!!.isChecked).apply()
        editor.apply()
    }

    fun obteneresadobutton(): Boolean {
        val preferences = getSharedPreferences("preferencias", MODE_PRIVATE)
        return preferences.getBoolean("estadobutton", false)
    }


    private fun buscar(url: String) {
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.POST, url, null,
            { response ->
                guardarestadobutton()
                var jsonObject: JSONObject?
                for (i in 0 until response.length()) {
                    try {
                        jsonObject = response.getJSONObject(i)
                        dni = jsonObject.getString("dni")
                        nombre = jsonObject.getString("nombre")
                        apellidos = jsonObject.getString("apellidos")
                        if (textDNI!!.text.toString() == dni.toString()) {
                            val intent = Intent(this, Fichar::class.java)
                            val preferences = getSharedPreferences("datos", MODE_PRIVATE)
                            val editor = preferences.edit()
                            editor.putString("dniuser", dni)
                            editor.putString("nombreuser", nombre)
                            editor.putString("apellidosuser", apellidos)
                            editor.apply()
                            startActivity(intent)
                            finish()
                        } else {
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }) { error ->
            println(url)
            println("ERROR: " + error.message)
        }
        requestQueue = Volley.newRequestQueue(this)
        requestQueue!!.add(jsonArrayRequest)
    }

}