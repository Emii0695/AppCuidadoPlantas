package com.example.cuidadodeplantas

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AgregarPlantaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_planta)

        val nombre = findViewById<EditText>(R.id.edtNombreNuevaPlanta)
        val tipo = findViewById<EditText>(R.id.edtTipoPlanta)
        val riego = findViewById<EditText>(R.id.edtFrecuenciaRiego)
        val grupoLuz = findViewById<RadioGroup>(R.id.rgLuz)
        val guardar = findViewById<Button>(R.id.btnGuardarPlanta)

        guardar.setOnClickListener {

            val nombreTexto = nombre.text.toString().trim()
            val tipoTexto = tipo.text.toString().trim()
            val dias = riego.text.toString().toIntOrNull()

            when {
                nombreTexto.isEmpty() -> {
                    nombre.error = "Ingresa el nombre de la planta"
                }

                tipoTexto.isEmpty() -> {
                    tipo.error = "Ingresa el tipo de planta"
                }

                dias == null || dias !in 1..365 -> {
                    riego.error = "Ingresa entre 1 y 365 días"
                }

                grupoLuz.checkedRadioButtonId == -1 -> {
                    Toast.makeText(
                        this,
                        "Selecciona el tipo de luz",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val luz = when (grupoLuz.checkedRadioButtonId) {
                        R.id.rbSol -> "Sol directo"
                        R.id.rbIndirecta -> "Luz indirecta"
                        R.id.rbSombra -> "Sombra"
                        else -> ""
                    }

                    val resultado = intent
                    resultado.putExtra("nombre", nombreTexto)
                    resultado.putExtra("tipo", tipoTexto)
                    resultado.putExtra("riego", dias)
                    resultado.putExtra("luz", luz)

                    setResult(Activity.RESULT_OK, resultado)
                    finish()
                }
            }
        }
    }
}
