package woowacourse.movie.presentation.view.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.presentation.view.main.home.data.MovieData

class MovieListFragment : Fragment() {
    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        setListView()
        return binding.root
    }

    private fun setListView() {
        val listView = binding.rvMovieList
        val movies = MovieData.getData()
        val adapter = MovieListAdapter(
            movies,
            ContextCompat.getDrawable(requireContext(), R.drawable.advertise_wooteco)!!
        )
        listView.adapter = adapter
    }
}
