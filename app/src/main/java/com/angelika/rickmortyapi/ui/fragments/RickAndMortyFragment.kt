package com.angelika.rickmortyapi.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelika.rickmortyapi.R
import com.angelika.rickmortyapi.databinding.FragmentRickAndMortyBinding
import com.angelika.rickmortyapi.ui.adapters.CharacterAdapter
import kotlinx.coroutines.NonDisposableHandle.parent

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
        setUpRequests()
        search()
        setupListeners()
    }

    private fun initialize() {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = characterAdapter
        }
    }

    private fun setUpRequests() = with(binding) {
        viewModel.characterLiveData.observe(viewLifecycleOwner) {
            characterAdapter.submitList(it.results)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "pon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun search() = with(binding) {
        binding.searchViewBackground.setOnQueryTextListener((object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.query(
                        query,
                        gender = etGender.text.toString(),
                        species = etSpecies.text.toString(),
                        status = etStatus.text.toString(),
                        type = eteType.text.toString()
                    )
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.query(
                        newText, gender = etGender.text.toString(),
                        species = etSpecies.text.toString(),
                        status = etStatus.text.toString(),
                        type = eteType.text.toString()
                    )
                }
                return true
            }

        }))
    }

    private fun setupListeners() = with(binding) {
        btnVisible.setOnClickListener {
            fmEditText.isVisible = true
            btnVisible.isGone = true
        }
        btnInvisible.setOnClickListener {
            viewModel.query(
                " ",
                gender = etGender.text.toString(),
                species = etSpecies.text.toString(),
                status = etStatus.text.toString(),
                type = eteType.text.toString()
            )
            fmEditText.isGone = true
            btnVisible.isVisible = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
