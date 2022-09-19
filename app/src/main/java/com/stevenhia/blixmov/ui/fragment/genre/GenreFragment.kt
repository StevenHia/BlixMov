package com.stevenhia.blixmov.ui.fragment.genre

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.stevenhia.blixmov.adapter.GenreAdapter
import com.stevenhia.blixmov.databinding.FragmentGenreBinding
import com.stevenhia.blixmov.utils.APIStatus

class GenreFragment : Fragment() {

    private var _binding: FragmentGenreBinding? = null
    private val binding get() = _binding!!

    private lateinit var genreAdapter: GenreAdapter
    private val genreViewModel: GenreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        genreAdapter = GenreAdapter()

        setViewModel()
        setRecyclerView()
    }

    private fun setViewModel() {
        genreViewModel.getListGenre.observe(viewLifecycleOwner) {
            when (it.status) {
                APIStatus.SUCCESS -> {
                    if (it.data != null) {
                        genreAdapter.setData(it.data)
                        Log.d("CHECK GENRE IN VIEW MODEL", it.data.toString())
                        binding.layoutConstNoNetwork.visibility = View.GONE
                    }
                }

                APIStatus.ERROR404 -> {
                    binding.layoutConstNoNetwork.visibility = View.VISIBLE
                }
                else -> {}
            }
        }
    }

    private fun setRecyclerView() {
        binding.rvGenre.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = genreAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}