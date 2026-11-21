package com.example.cuidadodeplantas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.widget.Button

class DetallePlantaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_planta)

        val nombre = intent.getStringExtra("nombre") ?: "Planta"
        val riego = intent.getStringExtra("riego") ?: "Sin información de riego"
        val tipo = intent.getStringExtra("tipo") ?: "Sin especificar"
        val luz = intent.getStringExtra("luz") ?: "Sin especificar"

        findViewById<TextView>(R.id.txtDetalleNombre).text = nombre
        findViewById<TextView>(R.id.txtDetalleRiego).text = riego
        findViewById<TextView>(R.id.txtDetalleTipo).text = "Tipo: $tipo"
        findViewById<TextView>(R.id.txtDetalleLuz).text = "Luz: $luz"


        findViewById<Button>(R.id.btnVolverPlantas).setOnClickListener {
            finish()
        }


        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { view, insets ->

            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }
    }
}
