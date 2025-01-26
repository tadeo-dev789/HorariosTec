package com.example.horariostec

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.horariostec.scrapping.MateriasAdapter

class MateriasSeleccionadasFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var adapter: MateriasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {


        // Inflar la vista raíz
        val rootView = inflater.inflate(R.layout.fragment_materias_seleccionadas, container, false)

        // Inicializar el ViewModel
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        // Configurar el RecyclerView
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerViewMateriasSeleccionadas)
        adapter = MateriasAdapter(mutableListOf()) { materia ->
            // Eliminar materia del ViewModel
            sharedViewModel.eliminarMateria(materia)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observar cambios en las materias seleccionadas y ordenarlas por hora
        sharedViewModel.materiasSeleccionadas.observe(viewLifecycleOwner) { materiasSeleccionadas ->
            val materiasOrdenadas = materiasSeleccionadas.sortedBy { materia ->
                extraerHoraMinuto(materia.lunes)
            }
            adapter.updateList(materiasOrdenadas)
        }

        return rootView
    }

    private fun extraerHoraMinuto(horario: String): Int {
        return try {
            val horarioSinAula = horario.split("/")[0]

            val horaMinuto = horarioSinAula.split("-")[0].split(":")

            val hora = horaMinuto[0].toInt()
            val minuto = horaMinuto[1].toInt()
            hora * 100 + minuto  // Convertimos a formato HHMM para fácil ordenación
        } catch (e: Exception) {
            Int.MAX_VALUE  // Si hay error, poner al final de la lista
        }
    }


}
