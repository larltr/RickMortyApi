package com.angelika.rickmortyapi.data.remout.apiservices

import com.angelika.rickmortyapi.models.CharacterModel
import com.angelika.rickmortyapi.models.RickAndMortyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApiService {

    @GET("character")
    fun fetchCharacters(
        @Query("name") name: String,
        @Query("status") status: String,
        @Query("species") species: String,
        @Query("type") type: String,
        @Query("gender") gender: String
    ): Call<RickAndMortyResponse<CharacterModel>>
}