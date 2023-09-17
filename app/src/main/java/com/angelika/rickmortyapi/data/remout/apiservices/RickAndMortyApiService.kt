package com.angelika.rickmortyapi.data.remout.apiservices

import com.angelika.rickmortyapi.models.CharacterModel
import com.angelika.rickmortyapi.models.RickAndMortyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApiService {

    @GET("api/character")
    fun fetchCharacters(
    ): Call<RickAndMortyResponse<CharacterModel>>

    @GET("api/character")
    fun query(
        @Query("name") name:String
    ):Call<RickAndMortyResponse<CharacterModel>>
}