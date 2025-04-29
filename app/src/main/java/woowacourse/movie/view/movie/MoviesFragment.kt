package woowacourse.movie.view.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.Movie
import woowacourse.movie.view.movie.adapter.MovieAdapter

class MoviesFragment : Fragment(),MovieContract.View {
    private val presenter: MoviePresenter by lazy { MoviePresenter(this) }
    private lateinit var moviesAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMovieAdapter(view)
    }


    override fun showMovies(movies: List<Movie>) {
        moviesAdapter.submitList(movies)
    }

    override fun navigateToReservation(movie: Movie) {
//        TODO("Not yet implemented")
    }

    private fun setupMovieAdapter(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_movies)
        moviesAdapter =
            MovieAdapter(
                object : MovieClickListener {
                    override fun onReservationClick(movie: Movie) {
                        navigateToReservation(movie)
                    }
                },
            )
        recyclerView.adapter = moviesAdapter
    }
}