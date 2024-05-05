package woowacourse.movie.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.DummyMovieRepository
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.movielist.theaters.TheaterBottomSheetDialogFragment
import woowacourse.movie.movielist.uimodel.ListItemUiModel

class MovieListFragment : Fragment(), MovieListContract.View, AdapterClickListener {
    private lateinit var presenter: MovieListPresenter
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        movieAdapter = MovieAdapter(kotlin.collections.listOf(), this)
        val listView = binding.rcvScreening
        listView.adapter = movieAdapter
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MovieListPresenter(this, DummyMovieRepository)
        presenter.loadMovies()
    }

    override fun showMovies(movies: List<ListItemUiModel>) {
        movieAdapter.updateData(movies)
    }

    override fun showTheaters(movieId: Long) {
        val fragment = TheaterBottomSheetDialogFragment()
        val bundle = TheaterBottomSheetDialogFragment.getBundle(movieId)
        fragment.arguments = bundle
        fragment.show(parentFragmentManager, "theaterBottomSheet")
    }

    override fun onClick(id: Long) {
        presenter.selectMovie(id)
    }
}
