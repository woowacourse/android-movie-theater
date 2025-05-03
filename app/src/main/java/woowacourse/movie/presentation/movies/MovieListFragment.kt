package woowacourse.movie.presentation.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.MovieData
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.presentation.movies.adapter.MovieAdapter
import woowacourse.movie.presentation.movies.adapter.MovieListClickListener
import woowacourse.movie.presentation.movies.adapter.MovieListItem
import woowacourse.movie.presentation.theater.TheaterFragment

class MovieListFragment :
    Fragment(),
    MovieListContract.View {
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var presenter: MovieListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MovieListPresenter(this, MovieData)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadMovieList()
    }

    override fun showMovieList(items: List<MovieListItem>) {
        val adapter =
            MovieAdapter(
                items = items,
                clickListener =
                    object : MovieListClickListener {
                        override fun onClickMovie(item: Movie) = presenter.selectTheater(item)
                    },
            )
        binding.adapter = adapter
    }

    override fun showTheaterList(movie: Movie) {
        val theaterFragment = TheaterFragment.newInstance(movie)
        theaterFragment.show(childFragmentManager, theaterFragment.tag)
    }
}
