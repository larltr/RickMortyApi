package com.angelika.rickmortyapi.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angelika.rickmortyapi.models.CharacterModel
import com.angelika.rickmortyapi.models.RickAndMortyResponse
import com.angelika.rickmortyapi.repositories.RickAndMortyRepository

class RickAndMortyViewModel : ViewModel() {

    private val characterRepository = RickAndMortyRepository()

    private val _characterLiveData = MutableLiveData<RickAndMortyResponse<CharacterModel>>()
    val characterLiveData: LiveData<RickAndMortyResponse<CharacterModel>> get() = _characterLiveData
    private val _errorLiveData = MutableLiveData<String>()

    val errorLiveData: LiveData<String> get() = _errorLiveData

    init {
        fetchCharacters()
    }

    fun fetchCharacters() {
        characterRepository.fetchCharacter(onResponse = { data ->
            _characterLiveData.value = data
        },
            onFailure = { message ->
                _errorLiveData.value = message
            }
        )
    }

    fun query(
        name: String,
        status: String,
        species: String,
        type: String,
        gender: String,
    ) {
        characterRepository.query(name = name,
            status = status,
            species = species,
            type = type,
            gender = gender,
            onResponse = { data ->
                _characterLiveData.value = data
            },
            onFailure = { message ->
                _errorLiveData.value = message
            }
        )
    }
}