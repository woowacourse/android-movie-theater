package woowacourse.movie.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieHomeBinding
import woowacourse.movie.model.data.MovieContentsImpl
import woowacourse.movie.model.movie.MovieContent
import woowacourse.movie.ui.ReservationButtonClickListener
import woowacourse.movie.ui.home.adapter.MovieContentAdapter
import java.lang.IllegalStateException

class MovieHomeFragment : Fragment(), MovieHomeContract.View, ReservationButtonClickListener {
    private lateinit var binding: FragmentMovieHomeBinding
    private lateinit var movieContents: List<MovieContent>
    private val presenter: MovieHomePresenter by lazy { generatePresenter() }
    private val adapter: MovieContentAdapter by lazy { generateMovieContentAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_home, container, false)
        presenter.loadMovieContents()
        binding.movieContentList.adapter = adapter
        return binding.root
    }

    override fun showMovieContents(movieContents: List<MovieContent>) {
        runCatching {
            this.movieContents = movieContents
        }.onFailure {
            presenter.handleError(it)
        }
    }

    override fun onReservationButtonClick(movieContentId: Long) {
        val fragment = TheaterSelectionBottomSheetFragment()
        val bundle = Bundle()
        bundle.putLong(MovieHomeKey.MOVIE_CONTENT_ID, movieContentId)
        fragment.arguments = bundle
        fragment.show(
            parentFragmentManager.apply {
                setFragmentResult(MovieHomeKey.FRAGMENT_REQUEST_KEY, bundle)
                commit {
                    addToBackStack(null)
                }
            },
            fragment.tag,
        )
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.toast_invalid_key),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun generateMovieContentAdapter(): MovieContentAdapter {
        return MovieContentAdapter(this).apply {
            addMovieContents(movieContents)
        }
    }

    private fun generatePresenter() = MovieHomePresenter(this, MovieContentsImpl)
}
