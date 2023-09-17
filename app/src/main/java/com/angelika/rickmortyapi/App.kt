package com.angelika.rickmortyapi

import android.app.Application
import com.angelika.rickmortyapi.data.remout.RetrofitClient
import com.angelika.rickmortyapi.data.remout.apiservices.RickAndMortyApiService

class App : Application() {

    companion object {
        @JvmStatic
        lateinit var retrofitClient: RetrofitClient
        var characterApi: RickAndMortyApiService? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        retrofitClient = RetrofitClient()
        characterApi = retrofitClient.pokemonApiService
    }
}