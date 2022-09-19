package com.stevenhia.blixmov.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.stevenhia.blixmov.adapter.HomeAdapter
import com.stevenhia.blixmov.adapter.SliderHomeAdapter
import com.stevenhia.blixmov.databinding.FragmentHomeBinding
import com.stevenhia.blixmov.utils.APIStatus

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var sliderHomeAdapter: SliderHomeAdapter
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var topRatedAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sliderHomeAdapter = SliderHomeAdapter()
        homeAdapter = HomeAdapter()
        topRatedAdapter = HomeAdapter()

        setViewModel()
        setSliderView()
        setRecyclerView()
    }

    private fun setViewModel() {
        homeViewModel.upComingMovie().observe(viewLifecycleOwner) {
            sliderHomeAdapter.setData(it)
        }

        homeViewModel.getListPopularMovies().observe(viewLifecycleOwner) {
            when (it.status) {
                APIStatus.SUCCESS -> {
                    if (it.data != null) {
                        homeAdapter.setData(it.data)
                    }
                }

                APIStatus.ERROR404 -> {
                    binding.layoutConstNoNetwork.visibility = View.VISIBLE
                }

                APIStatus.ERROR -> {}
                else -> {}
            }
        }

        homeViewModel.getListTopRatedMovies.observe(viewLifecycleOwner) {
            when (it.status) {
                APIStatus.SUCCESS -> {
                    if (it.data != null) {
                        topRatedAdapter.setData(it.data)
                    }
                }

                APIStatus.ERROR404 -> {
                    binding.layoutConstNoNetwork.visibility = View.VISIBLE
                }

                APIStatus.ERROR -> {}
                else -> {}
            }
        }
    }

    private fun setSliderView() {
        binding.imageSliderMovieFragment.apply {
            setSliderAdapter(sliderHomeAdapter)
            setIndicatorAnimation(IndicatorAnimationType.DROP)
            setSliderTransformAnimation(SliderAnimations.GATETRANSFORMATION)
            startAutoCycle()
        }
    }

    private fun setRecyclerView() {
        //RecyclerView Popular Movie
        binding.rvPopularMovie.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homeAdapter
        }

        binding.rvTopRatedMovie.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = topRatedAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}