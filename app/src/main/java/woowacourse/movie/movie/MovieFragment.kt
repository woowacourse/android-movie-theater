package woowacourse.movie.movie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieBinding
import woowacourse.movie.domain.Movie
import androidx.core.net.toUri

class MovieFragment : Fragment(), Movies.View {
    private lateinit var binding: FragmentMovieBinding
    private lateinit var presenter: MoviesPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)

        presenter = MoviesPresenter(this@MovieFragment)
        presenter.loadMovies()
        return binding.root
    }

    override fun showMovies(movies: List<Movie>) {
        binding.movies.adapter =
            MovieListAdapter(
                movies,
                { movie -> presenter.selectedMovie(movie) },
                { presenter.selectedAd() },
            )
        binding.movies.layoutManager = LinearLayoutManager(this.context)
    }

    override fun navigateToBook(movie: Movie) {
        val dialog = TheaterBottomSheetDialogFragment()
        val bundle =
            Bundle().apply {
                putParcelable("movie", movie)
            }
        dialog.arguments = bundle
        dialog.show(parentFragmentManager, "TheaterBottomSheet")
    }

    override fun navigateToAdPage() {
        val intent =
            Intent(Intent.ACTION_VIEW)
        val uri = "https://www.woowacourse.io/".toUri()
        intent.setData(uri)
        binding.root.context.startActivity(intent)
    }

    override fun showError(messageResId: Int) {
        AlertDialog.Builder(binding.root.context)
            .setMessage(getString(messageResId))
            .setPositiveButton(R.string.error_dialog_okay, null)
            .show()
            .setCancelable(false)
    }
}
