package woowacourse.movie.presentation.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.repository.DefaultMovieRepository
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.presentation.movies.adapter.MovieAdapter
import woowacourse.movie.presentation.movies.adapter.MovieListClickListener
import woowacourse.movie.presentation.movies.adapter.MovieListItem
import woowacourse.movie.presentation.theater.TheaterFragment

class MovieListFragment :
    Fragment(),
    MovieListContract.View {
    private var _binding: FragmentMovieListBinding? = null
    private val binding: FragmentMovieListBinding get() = _binding!!
    private lateinit var presenter: MovieListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MovieListPresenter(this, DefaultMovieRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadMovieList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showMovieList(items: List<MovieListItem>) {
        val adapter =
            MovieAdapter(
                items = items,
                clickListener =
                    object : MovieListClickListener {
                        override fun onClickMovie(item: Movie) = presenter.selectMovie(item)
                    },
            )
        binding.adapter = adapter
    }

    override fun showTheaterList(movie: Movie) {
        val theaterFragment = TheaterFragment.newInstance(movie)
        theaterFragment.show(childFragmentManager, theaterFragment.tag)
    }
}
