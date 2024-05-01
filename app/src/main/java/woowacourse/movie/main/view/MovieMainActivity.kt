package woowacourse.movie.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieMainBinding
import woowacourse.movie.main.presenter.MovieMainPresenter
import woowacourse.movie.main.presenter.contract.MovieMainContract
import woowacourse.movie.main.view.adapter.movie.MovieAdapter
import woowacourse.movie.model.Movie
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_ID

class MovieMainActivity : AppCompatActivity(), MovieMainContract.View {
    private lateinit var movieMainPresenter: MovieMainPresenter

    private lateinit var binding: ActivityMovieMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_main)
        movieMainPresenter = MovieMainPresenter(this)
        movieMainPresenter.loadMovies()
    }

    override fun displayMovies(movies: List<Movie>) {
        binding.movieRecyclerView.adapter = MovieAdapter(movies, ::displayTheaterSelectionDialog)
        binding.movieRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun displayTheaterSelectionDialog(id: Long) {
        val bundle = Bundle()
        bundle.putLong(KEY_MOVIE_ID, id)
        val theaterSelectionFragment = TheaterSelectionFragment()
        theaterSelectionFragment.arguments = bundle
        theaterSelectionFragment.show(supportFragmentManager, theaterSelectionFragment.tag)
    }
}
