package woowacourse.movie.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieBinding
import woowacourse.movie.domain.Movie

class MovieFragment : Fragment(), Movies.View {
    private lateinit var binding: FragmentMovieBinding
    private lateinit var presenter: MoviesPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        setUpPresenter()
        return binding.root
    }

    override fun showMovies(movies: List<Movie>) {
        object : MovieClickListener {
            override fun navigateToBook(movie: Movie) {
                presenter.selectedMovie(movie)
            }

            override fun navigateToAd() {
                presenter.selectedAd()
            }
        }
        binding.movies.adapter =
            MovieListAdapter(
                movies,
                object : MovieClickListener {
                    override fun navigateToBook(movie: Movie) {
                        presenter.selectedMovie(movie)
                    }

                    override fun navigateToAd() {
                        presenter.selectedAd()
                    }
                }
            )
        binding.movies.layoutManager = LinearLayoutManager(this.context)
    }

    override fun navigateToBook(movie: Movie) {
        val dialogFragment = TheaterBottomSheetDialogFragment.newInstance(movie)
        dialogFragment.show(parentFragmentManager, TAG_THEATER_DIALOG)
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

    private fun setUpPresenter() {
        presenter = MoviesPresenter(this@MovieFragment)
        presenter.loadMovies()
    }

    companion object {
        private const val TAG_THEATER_DIALOG = "TheaterBottomSheet"
    }
}
