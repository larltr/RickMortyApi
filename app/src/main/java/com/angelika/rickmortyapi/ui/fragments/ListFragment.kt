package com.angelika.rickmortyapi.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelika.rickmortyapi.databinding.FragmentListBinding
import com.angelika.rickmortyapi.ui.adapters.CharacterAdapter

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val characterAdapter = CharacterAdapter()
    private val viewModel by viewModels<RickAndMortyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setUpRequests()
        search()
    }

    private fun setUpRequests() = with(binding) {
        viewModel.characterLiveData.observe(viewLifecycleOwner) {
            characterAdapter.submitList(it.results)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "pon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun search() {
        binding.searchViewBackground.setOnQueryTextListener((object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.query(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.query(newText)
                }
                return true
            }

        }))
    }

    private fun initialize() {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = characterAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}