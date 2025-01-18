package com.example.horariostec.scrapping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.horariostec.R
import org.w3c.dom.Text

class MateriasAdapter(private var ListaMaterias: MutableList<Materia>,private val onClick: (Materia)->Unit
):RecyclerView.Adapter<MateriasAdapter.MateriaViewHolder>(){

    //Debemos de crear las vistas de cada tarjera
    class MateriaViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textNombreMateria: TextView = itemView.findViewById(R.id.tvMateriaNombre)
        val textGrupo: TextView = itemView.findViewById(R.id.tvGrupo)
        val textCated: TextView = itemView.findViewById(R.id.tvCatedratico)
        val textHorario: TextView = itemView.findViewById(R.id.tvHorario)
    }

    //Crear la vista para cada tarjeta
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MateriaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item,parent,
            false)
        return MateriaViewHolder(view)
    }

    //Llenar los datos de cada tarjeta
    override fun onBindViewHolder(holder: MateriaViewHolder, position: Int) {
        val materia = ListaMaterias[position]
        holder.textNombreMateria.text = "${materia.materia}"
        holder.textGrupo.text = "Grupo: ${materia.grupo}"
        holder.textCated.text = "Catedrático: ${materia.catedratico}"
        holder.textHorario.text = "Horario: ${materia.lunes}"

        //Manejar clics
        holder.itemView.setOnClickListener{
            onClick(materia)
        }
    }

    //Regresa el total de las materias
    override fun getItemCount(): Int {
        return ListaMaterias.size
    }

    // Método para actualizar la lista
    fun updateList(newList: List<Materia>) {
        ListaMaterias.clear()
        ListaMaterias.addAll(newList)
        notifyDataSetChanged()
    }


}