package com.example.horariostec.scrappin

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface HorariosApi {
    @FormUrlEncoded
    @POST("horarios.asp")

    fun obtenerHorario(
        @Field("ESPECIALIDAD")especialidad: String,
        @Field("BotConsultar")consultar:String="Consultar"
        ):Call<String>
}