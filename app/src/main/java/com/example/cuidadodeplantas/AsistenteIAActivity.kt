package com.example.cuidadodeplantas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AsistenteIAActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asistente_iaactivity)

        val pregunta = findViewById<EditText>(R.id.edtPreguntaIA)
        val respuesta = findViewById<TextView>(R.id.txtRespuestaIA)
        val boton = findViewById<Button>(R.id.btnPreguntarIA)

        boton.setOnClickListener {
            val texto = pregunta.text.toString().trim().lowercase()

            if (texto.isEmpty()) {
                pregunta.error = "Escribe una pregunta"
                return@setOnClickListener
            }

            val mensaje = when {
                "riego" in texto || "agua" in texto ->
                    "Revisa la humedad de la tierra antes de regar. Evita que las raíces permanezcan encharcadas."

                "luz" in texto || "sol" in texto ->
                    "Cada planta necesita una cantidad distinta de luz. Algunas prefieren luz indirecta y otras toleran sol directo."

                "hojas" in texto || "amarillas" in texto ->
                    "Las hojas amarillas pueden indicar exceso de riego, falta de nutrientes u otros problemas. Revisa la humedad, la luz y el estado de la planta."

                "abono" in texto || "fertilizante" in texto ->
                    "Utiliza un fertilizante apropiado para la especie y sigue las dosis indicadas por el fabricante."

                "plaga" in texto || "insectos" in texto ->
                    "Revisa el envés de las hojas y aísla temporalmente la planta afectada para evitar que una posible plaga se propague."

                else ->
                    "Puedo orientarte sobre riego, luz, hojas amarillas, abono y plagas. Cuéntame qué le ocurre a tu planta."
            }

            respuesta.text = mensaje
        }
    }
}
