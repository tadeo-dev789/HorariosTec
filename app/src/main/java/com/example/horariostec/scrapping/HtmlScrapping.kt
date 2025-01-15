package com.example.horariostec.scrapper

import android.content.Context
import com.example.horariostec.scrapping.Materia
import org.jsoup.Jsoup
import org.jsoup.nodes.Element


class HtmlScrapper(private val context: Context) {

    fun readHtmlFromAssets(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
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

    fun extractTableDataFromFile(fileName: String): MutableList<Map<String, String>> {
        val html = readHtmlFromAssets(fileName)
        return extractTableData(html)
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
