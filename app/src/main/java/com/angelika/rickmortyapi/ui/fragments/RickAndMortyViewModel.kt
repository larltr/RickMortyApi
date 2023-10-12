package com.angelika.rickmortyapi.ui.fragments


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angelika.rickmortyapi.models.CharacterModel
import com.angelika.rickmortyapi.data.repositories.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RickAndMortyViewModel @Inject constructor(
    private val characterRepository: RickAndMortyRepository
) : ViewModel() {

    private val _characterLiveData = MutableLiveData(CharacterUiState<List<CharacterModel>>())
    val characterLiveData: LiveData<CharacterUiState<List<CharacterModel>>> = _characterLiveData

    init {
        fetchCharacters(name = "", status = "", species = "", type = "", gender = "")
    }

    fun fetchCharacters(
        name: String,
        status: String,
        species: String,
        type: String,
        gender: String
    ) {
        characterRepository.fetchCharacter(
            onResponse = { data ->
                val newValue =
                    _characterLiveData.value?.copy(isLoading = false, success = data.results)
                _characterLiveData.value = newValue
            },
            onFailure = { message ->
                val newValue = _characterLiveData.value?.copy(isLoading = false, error = message)
                _characterLiveData.value = newValue
            },
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender
        )
    }
}

data class CharacterUiState<T>(
    val isLoading: Boolean = true,
    val error: String? = null,
    val success: T? = null
)