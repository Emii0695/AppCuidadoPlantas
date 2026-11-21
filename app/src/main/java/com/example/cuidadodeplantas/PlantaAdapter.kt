
package com.example.cuidadodeplantas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlantaAdapter(
    private val plantas: List<Planta>,
    private val alSeleccionarPlanta: (Planta) -> Unit = {}
) : RecyclerView.Adapter<PlantaAdapter.PlantaViewHolder>() {

    class PlantaViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val nombre: TextView =
            itemView.findViewById(R.id.txtNombrePlanta)

        val riego: TextView =
            itemView.findViewById(R.id.txtRiegoPlanta)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlantaViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_planta, parent, false)

        return PlantaViewHolder(vista)
    }

    override fun onBindViewHolder(
        holder: PlantaViewHolder,
        position: Int
    ) {
        val planta = plantas[position]
        holder.nombre.text = planta.nombre
        holder.riego.text = planta.riego
        holder.itemView.setOnClickListener {
            alSeleccionarPlanta(planta)
        }
    }

    override fun getItemCount(): Int {
        return plantas.size
    }
}
