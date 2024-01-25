package com.example.registro

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.registro.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.LocalTime

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.iBtnEntrar.setOnClickListener {

            val consulta = Volley.newRequestQueue(this)
            val stringRequest: StringRequest = object: StringRequest(Method.POST, "http://web/appFichar/insertarentradaM.php",
                Response.Listener {
                    Toast.makeText(this, "ENVIADO", Toast.LENGTH_SHORT).show()


                }, Response.ErrorListener {
                    Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
                }){
                override fun getParams(): MutableMap<String, String> {
                    val datos: MutableMap<String, String> = java.util.HashMap()
                    datos["dni"] = "09009253S"
                    datos["hora_entrada"] = LocalTime.now().toString()
                    datos["fecha"] = LocalDate.now().toString()
                    return datos
                }
            }

            consulta.add(stringRequest)
        }
    }


}