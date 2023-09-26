package com.angelika.rickmortyapi.repositories

import com.angelika.rickmortyapi.data.remout.apiservices.RickAndMortyApiService
import com.angelika.rickmortyapi.models.CharacterModel
import com.angelika.rickmortyapi.models.RickAndMortyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RickAndMortyRepository @Inject constructor(
    private val rickAndMortyApiService: RickAndMortyApiService
) {

    fun fetchCharacter(
        name: String,
        status: String,
        species: String,
        type: String,
        gender: String,
        onResponse: (data: RickAndMortyResponse<CharacterModel>) -> Unit,
        onFailure: (errorMassage: String) -> Unit
    ) {
        rickAndMortyApiService.fetchCharacters(
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender
        )
            .enqueue(object : Callback<RickAndMortyResponse<CharacterModel>> {
                override fun onResponse(
                    call: Call<RickAndMortyResponse<CharacterModel>>,
                    response: Response<RickAndMortyResponse<CharacterModel>>
                ) {
                    if (response.isSuccessful)
                        response.body()?.let(onResponse)
                }

                override fun onFailure(
                    call: Call<RickAndMortyResponse<CharacterModel>>,
                    t: Throwable
                ) {
                    onFailure(t.localizedMessage ?: "Error")
                }
            })
    }
}