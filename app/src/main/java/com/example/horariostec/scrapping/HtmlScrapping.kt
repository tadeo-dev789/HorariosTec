package com.example.horariostec.scrapper

import android.content.Context
import com.example.horariostec.scrappin.HorariosApi
import com.example.horariostec.scrapping.Materia
import com.example.horariostec.scrapping.RetroFitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element


class HtmlScrapping(private val context: Context) {

    suspend fun fetchHtmlFromServer(especialidad: String):String{
        return withContext(Dispatchers.IO){

            val api = RetroFitClient.instance.create(HorariosApi::class.java)
            val response = api.obtenerHorario(especialidad).execute()
            if(response.isSuccessful){
                response.body()?:throw Exception("El servidor devolvió una respuesta vacía.")
            }else {
                throw Exception("Error al obtener horario: ${response.code()}")
            }
        }
    }

    fun extractTableData(html:String): MutableList<Map<String,String>>{

        val result = mutableListOf<Map<String,String>>()
        val doc = Jsoup.parse(html)

        val table : Element? = doc.select("table").first()

        if(table != null){
            val rows = table.select("tr")
            val headers  = mutableListOf<String>()

            headers.add(0,"Materia")
            headers.add(1,"Grupo")
            headers.add(2,"Lunes")
            headers.add(3,"Martes")
            headers.add(4,"Miercoles")
            headers.add(5,"Jueves")
            headers.add(6,"Viernes")
            headers.add(7,"Catedratico")

            for ((index,row) in rows.withIndex()){
                val columns = row.select("th,td").map { it.text().trim() }

                if (index > 0){
                    val rowMap = headers.zip(columns).toMap()
                    result.add(rowMap)
                }
            }
        }
        return result
    }

    fun convertToMateriasList(tableData: MutableList<Map<String,String>>):MutableList<Materia>{
        return tableData.map { row->
            Materia(
                materia = row["Materia"]?:"",
                grupo = row["Grupo"]?:"",
                lunes = row["Lunes"]?:"",
                martes = row["Martes"]?:"",
                miercoles = row["Miercoles"]?:"",
                jueves = row["Jueves"]?:"",
                viernes = row["Viernes"]?:"",
                catedratico = row["Catedratico"]?:"",
            )
        }.toMutableList()
    }
}
