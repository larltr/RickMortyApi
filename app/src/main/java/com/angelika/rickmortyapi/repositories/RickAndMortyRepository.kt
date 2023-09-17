package com.angelika.rickmortyapi.repositories

import com.angelika.rickmortyapi.App
import com.angelika.rickmortyapi.models.CharacterModel
import com.angelika.rickmortyapi.models.RickAndMortyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RickAndMortyRepository {

    fun fetchCharacter(
        onResponse: (data: RickAndMortyResponse<CharacterModel>) -> Unit,
        onFailure: (errorMassage: String) -> Unit
    ) {
        App.characterApi?.fetchCharacters()
            ?.enqueue(object : Callback<RickAndMortyResponse<CharacterModel>> {
                override fun onResponse(
                    call: Call<RickAndMortyResponse<CharacterModel>>,
                    response: Response<RickAndMortyResponse<CharacterModel>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        onResponse(response.body()!!)
                    }
                }

                override fun onFailure(
                    call: Call<RickAndMortyResponse<CharacterModel>>,
                    t: Throwable
                ) {
                    onFailure(t.localizedMessage ?: "Error")
                }

            })
    }

    fun query(
        name: String,
        onResponse: (data: RickAndMortyResponse<CharacterModel>) -> Unit,
        onFailure: (errorMassage: String) -> Unit
    ) {
        App.characterApi?.query(name)
            ?.enqueue(object : Callback<RickAndMortyResponse<CharacterModel>> {
                override fun onResponse(
                    call: Call<RickAndMortyResponse<CharacterModel>>,
                    response: Response<RickAndMortyResponse<CharacterModel>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        onResponse(response.body()!!)
                    }
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