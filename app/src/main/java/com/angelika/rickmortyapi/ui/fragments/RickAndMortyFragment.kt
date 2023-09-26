package com.angelika.rickmortyapi.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.angelika.rickmortyapi.databinding.FragmentRickAndMortyBinding
import com.angelika.rickmortyapi.ui.adapters.CharacterAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RickAndMortyFragment : Fragment() {

    private var _binding: FragmentRickAndMortyBinding? = null
    private val binding get() = _binding!!
    private val characterAdapter = CharacterAdapter()
    private val viewModel by viewModels<RickAndMortyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRickAndMortyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        subscribeToCharacter()
        setupListeners()
    }

    private fun initialize() {
        binding.recycler.apply {
            adapter = characterAdapter
        }
    }

    private fun subscribeToCharacter() {
        viewModel.characterLiveData.observe(viewLifecycleOwner) {
            it.error?.let {
                Log.e("error", "You have big problems bro")
            }
            it.success?.let { character ->
                characterAdapter.submitList(character)
            }
        }
    }

    private fun setupListeners() = with(binding) {
        btnVisible.setOnClickListener {
            fmEditText.isVisible = true
            btnVisible.isGone = true
        }

        btnInvisible.setOnClickListener {
            viewModel.fetchCharacters(
                "",
                gender = etGender.text.toString().trim(),
                species = etSpecies.text.toString().trim(),
                status = etStatus.text.toString().trim(),
                type = eteType.text.toString().trim()
            )
            fmEditText.isGone = true
            btnVisible.isVisible = true
        }

        searchViewBackground.setOnQueryTextListener((object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.fetchCharacters(
                        query,
                        gender = etGender.text.toString().trim(),
                        species = etSpecies.text.toString().trim(),
                        status = etStatus.text.toString().trim(),
                        type = eteType.text.toString().trim()
                    )
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.fetchCharacters(
                        newText, gender = etGender.text.toString().trim(),
                        species = etSpecies.text.toString().trim(),
                        status = etStatus.text.toString().trim(),
                        type = eteType.text.toString().trim()
                    )
                }
                return true
            }

        }))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}