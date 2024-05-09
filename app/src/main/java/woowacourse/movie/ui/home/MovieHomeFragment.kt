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
import woowacourse.movie.model.movie.MovieContent
import woowacourse.movie.model.movie.MovieContentDao
import woowacourse.movie.model.movie.MovieDatabase
import woowacourse.movie.ui.ReservationButtonClickListener
import woowacourse.movie.ui.home.adapter.MovieContentAdapter

class MovieHomeFragment : Fragment(), MovieHomeContract.View, ReservationButtonClickListener {
    private var _binding: FragmentMovieHomeBinding? = null
    private val binding: FragmentMovieHomeBinding
        get() = _binding!!
    private val presenter: MovieHomePresenter by lazy { generatePresenter() }
    private val dao: MovieContentDao by lazy { MovieDatabase.getDatabase(requireContext()).movieContentDao() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_home, container, false)
        presenter.loadMovieContents()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showMovieContents(movieContents: List<MovieContent>) {
        view?.post {
            runCatching {
                val adapter = MovieContentAdapter(this).apply { submitList(movieContents) }
                binding.movieContentList.adapter = adapter
            }.onFailure {
                presenter.handleError(it)
            }
        }
    }

    override fun onReservationButtonClick(movieContentId: Long) {
        val fragment = TheaterSelectionBottomSheetFragment()
        val bundle = Bundle()
        bundle.putLong(MovieHomeKey.MOVIE_CONTENT_ID, movieContentId)
        fragment.arguments = bundle
        fragment.show(
            childFragmentManager.apply {
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
            Toast.LENGTH_LONG,
        ).show()
    }

    private fun generatePresenter() = MovieHomePresenter(this, dao)
}
