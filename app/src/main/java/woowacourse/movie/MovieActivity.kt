package woowacourse.movie

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.databinding.ActivityMovieBinding
import woowacourse.movie.movie.MovieContract
import woowacourse.movie.movie.MoviePresenter
import woowacourse.movie.movie.MovieUiModel
import woowacourse.movie.movie.TheaterFragment
import woowacourse.movie.movie.TheaterFragment.Companion.KEY_MOVIE
import woowacourse.movie.movie.TheaterFragment.Companion.KEY_THEATERS
import woowacourse.movie.movie.TheaterUiModel
import woowacourse.movie.movie.adapter.MovieAdapter

class MovieActivity : AppCompatActivity(), MovieContract.View {
    private lateinit var presenter: MovieContract.Presenter
    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)
        setUpUi()

        presenter = MoviePresenter(this)
        presenter.initializeData(intent)
    }

    private fun setUpUi() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun setupMovieList(movies: List<MovieUiModel>) {
        val adapter =
            MovieAdapter(movies) { movie ->
                presenter.setTheaters(movie)
            }
        val recyclerView = binding.recyclerViewLayout
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showTheaterDialog(
        theaters: ArrayList<TheaterUiModel>,
        movie: MovieUiModel,
    ) {
        val fragment = TheaterFragment()
        val bundle = Bundle()

        bundle.putParcelableArrayList(KEY_THEATERS, theaters)
        bundle.putParcelable(KEY_MOVIE, movie)
        fragment.arguments = bundle

        fragment.show(supportFragmentManager, fragment.tag)
    }
}
