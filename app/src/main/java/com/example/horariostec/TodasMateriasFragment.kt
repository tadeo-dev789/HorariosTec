package com.example.horariostec

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.horariostec.scrapper.HtmlScrapper
import com.example.horariostec.scrapping.MateriasAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TodasMateriasFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val rootView = inflater.inflate(R.layout.fragment_todas_materias, container, false)


        // Obtén el RecyclerView del layout
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerViewMaterias)
        val editTextSearch = rootView.findViewById<EditText>(R.id.editTextSearch)

        val adapter = MateriasAdapter(mutableListOf())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        //Aqui debo descargar el html
        val htmlScrapper = HtmlScrapper(requireContext())
        val especialidad = "1"

        //Descargar el html
        CoroutineScope(Dispatchers.IO).launch {
            try{
                //USar la api creada para extraer los html
                val html = htmlScrapper.fetchHtmlFromServer(especialidad)

                //Procesar el html y extraer los datos

                val tableData = htmlScrapper.extractTableData(html)
                val tableMaterias = htmlScrapper.convertToMateriasList(tableData)

                //ACtualizar en el hilo principal

                withContext(Dispatchers.Main){
                    adapter.updateList(tableMaterias)

                    //BUSCAR MATERIAS<
                    editTextSearch.addTextChangedListener { text->
                        val query = text.toString().uppercase()

                        //Filtrar las materias
                        val materiasFiltradas = tableMaterias.filter { materia->
                            materia.materia.uppercase().contains(query)||
                                    materia.grupo.uppercase().contains(query)||
                                    materia.catedratico.uppercase().contains(query)
                        }

                        //Actualizar el adaptador
                        adapter.updateList(materiasFiltradas)
                }
            }
        }catch (e: Exception){
            Log.e("TodasMateriasFragment","Error al descargar el horario")
        }

        }

        return rootView
    }
}
