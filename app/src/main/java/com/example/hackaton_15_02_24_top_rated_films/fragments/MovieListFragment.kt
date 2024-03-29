package com.example.hackaton_15_02_24_top_rated_films.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hackaton_15_02_24_top_rated_films.R
import com.example.hackaton_15_02_24_top_rated_films.adapters.MovieListAdapter
import com.example.hackaton_15_02_24_top_rated_films.databinding.FragmentMovieListBinding
import com.example.hackaton_15_02_24_top_rated_films.di.DaggerMovieApplicationComponent
import com.example.hackaton_15_02_24_top_rated_films.models.Movie
import com.example.hackaton_15_02_24_top_rated_films.mvvm.movieList.MovieListViewModel
import com.example.hackaton_15_02_24_top_rated_films.mvvm.movieList.MovieListViewModelFactory
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class MovieListFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: MovieListViewModelFactory
    private lateinit var viewModel: MovieListViewModel

    private lateinit var _binding: FragmentMovieListBinding
    private var loadMovieListDef: Deferred<Unit>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // DI create our viewModel
        val app = DaggerMovieApplicationComponent.create()
        app.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[MovieListViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.movieRV.layoutManager = LinearLayoutManager(requireContext())
        _binding.movieRV.adapter = MovieListAdapter(){movie ->
            clickOnItemList(movie)
        }
        setupListeners()
        setupObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater)
        return _binding.root
    }

    private fun reloadMovieList() {
        loadMovieListDef?.cancel()
        loadMovieListDef = viewLifecycleOwner.lifecycleScope.async {
            viewModel.moviesList.collectLatest { pagingData ->
                (_binding.movieRV.adapter as MovieListAdapter).submitData(PagingData.from(listOf()))
                _binding.movieRV.scrollToPosition(0)
                (_binding.movieRV.adapter as MovieListAdapter).submitData(pagingData)
            }
        }
        loadMovieListDef?.start()
    }

    private fun setupObservers() {

        viewModel.currentPageLiveData.observe(viewLifecycleOwner) { _ ->
            reloadMovieList()
        }

    }

    private fun setupListeners() {
        _binding.gotoPageBtn.setOnClickListener {
            val pageNum = _binding.pageNumEditText.text.toString()
            if (pageNum.toIntOrNull() != null && pageNum.toInt() >= 1) {
                viewModel.gotoPage(pageNum.toInt())
                print(getLoadedMovies())
            }
        }
        _binding.btnGoToFilterList.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack("")
                .replace(R.id.fragmentContainerView, MovieFilteredFragment.newInstance(getLoadedMovies()))
                .commit()
        }
    }

    private fun getLoadedMovies(): List<Movie> {
        return (_binding.movieRV.adapter as MovieListAdapter).snapshot().items
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieListFragment()
    }
    private fun clickOnItemList(movie: Movie){
        val fragment = MovieDetailFragment.newInstance(movie)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack("")
            .commit()
    }
}