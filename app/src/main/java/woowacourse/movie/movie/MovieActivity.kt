package woowacourse.movie.movie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.domain.Movie

class MovieActivity : AppCompatActivity(), Movies.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MoviesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        presenter = MoviesPresenter(this@MovieActivity)
        presenter.loadMovies()
    }

    override fun showMovies(movies: List<Movie>) {
        binding.movies.adapter =
            MovieListAdapter(
                movies,
                { movie -> presenter.selectedMovie(movie) },
                { presenter.selectedAd() },
            )
        binding.movies.layoutManager = LinearLayoutManager(this)
    }

    override fun navigateToBook(movie: Movie) {
        val dialog = TheaterBottomSheetDialogFragment()
        val bundle =
            Bundle().apply {
                putParcelable("movie", movie)
            }
        dialog.arguments = bundle
        dialog.show(supportFragmentManager, "TheaterBottomSheet")
    }

    override fun navigateToAdPage() {
        val intent =
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://www.woowacourse.io/")
            }
        binding.root.context.startActivity(intent)
    }

    override fun showError(messageResId: Int) {
        AlertDialog.Builder(this)
            .setMessage(getString(messageResId))
            .setPositiveButton(R.string.error_dialog_okay, null)
            .show()
            .setCancelable(false)
    }
}
