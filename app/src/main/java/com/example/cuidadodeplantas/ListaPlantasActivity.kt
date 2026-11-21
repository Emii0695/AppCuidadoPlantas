package com.example.cuidadodeplantas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

class ListaPlantasActivity : AppCompatActivity() {

    private val listaPlantas = mutableListOf(
        Planta("Tulipán rosado", "Riego: cada 3 días"),
        Planta("Monstera", "Riego: cada 7 días"),
        Planta("Lavanda", "Riego: cada 5 días")
    )

    private lateinit var adaptador: PlantaAdapter

    private val agregarPlantaLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { resultado ->

            if (resultado.resultCode == Activity.RESULT_OK) {

                val datos = resultado.data
                val nombre = datos?.getStringExtra("nombre")
                val dias = datos?.getIntExtra("riego", 0) ?: 0
                val tipo = datos?.getStringExtra("tipo") ?: "Sin especificar"
                val luz = datos?.getStringExtra("luz") ?: "Sin especificar"

                if (!nombre.isNullOrBlank() && dias in 1..365) {
                    val nuevaPlanta = Planta(
                        nombre = nombre,
                        riego = "Riego: cada $dias días",
                        tipo = tipo,
                        luz = luz
                    )

                    listaPlantas.add(nuevaPlanta)
                    adaptador.notifyItemInserted(listaPlantas.lastIndex)
                    guardarPlantas()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_plantas)
        cargarPlantas()

        val recyclerView =
            findViewById<RecyclerView>(R.id.rvPlantas)


        adaptador = PlantaAdapter(listaPlantas) { planta ->
            val intent = Intent(this, DetallePlantaActivity::class.java)
            intent.putExtra("nombre", planta.nombre)
            intent.putExtra("riego", planta.riego)
            intent.putExtra("tipo", planta.tipo)
            intent.putExtra("luz", planta.luz)
            startActivity(intent)
        }


        recyclerView.layoutManager =
            LinearLayoutManager(this)

        recyclerView.adapter = adaptador

        findViewById<Button>(R.id.btnAgregarPlanta)
            .setOnClickListener {

                val intent = Intent(
                    this,
                    AgregarPlantaActivity::class.java
                )

                agregarPlantaLauncher.launch(intent)
            }


        findViewById<Button>(R.id.btnAsistenteIA).setOnClickListener {
            val intent = Intent(this, AsistenteIAActivity::class.java)
            startActivity(intent)
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


    private fun guardarPlantas() {
        val preferencias = getSharedPreferences(
            "mis_plantas",
            MODE_PRIVATE
        )

        val arreglo = JSONArray()

        listaPlantas.forEach { planta ->
            val objeto = JSONObject()
            objeto.put("nombre", planta.nombre)
            objeto.put("riego", planta.riego)
            objeto.put("tipo", planta.tipo)
            objeto.put("luz", planta.luz)
            arreglo.put(objeto)
        }

        preferencias.edit()
            .putString("lista", arreglo.toString())
            .apply()
    }

    private fun cargarPlantas() {
        val preferencias = getSharedPreferences(
            "mis_plantas",
            MODE_PRIVATE
        )

        val datos = preferencias.getString("lista", null)
            ?: return

        val arreglo = JSONArray(datos)
        listaPlantas.clear()

        for (i in 0 until arreglo.length()) {
            val objeto = arreglo.getJSONObject(i)

            listaPlantas.add(
                Planta(
                    nombre = objeto.getString("nombre"),
                    riego = objeto.getString("riego"),
                    tipo = objeto.optString("tipo", "Sin especificar"),
                    luz = objeto.optString("luz", "Sin especificar")
                )
            )
        }
    }
}
