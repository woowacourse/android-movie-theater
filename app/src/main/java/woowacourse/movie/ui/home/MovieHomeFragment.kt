package woowacourse.movie.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.database.movie.MovieContentDao
import woowacourse.movie.databinding.FragmentMovieHomeBinding
import woowacourse.movie.domain.MovieContent
import woowacourse.movie.ui.home.adapter.MovieContentAdapter

class MovieHomeFragment : Fragment(), MovieHomeContract.View, ReservationButtonClickListener {
    private var _binding: FragmentMovieHomeBinding? = null
    private val binding: FragmentMovieHomeBinding
        get() = _binding!!
    private val presenter: MovieHomePresenter by lazy { generatePresenter() }
    private val dao: MovieContentDao by lazy {
        MovieDatabase.getDatabase(requireContext()).movieContentDao()
    }
    private lateinit var movieContents: List<MovieContent>
    private val adapter: MovieContentAdapter by lazy { generateMovieContentAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadMovieContents()
        binding.movieContentList.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

    private fun generateMovieContentAdapter(): MovieContentAdapter {
        return MovieContentAdapter(this).apply { submitList(movieContents) }
    }

    private fun generatePresenter() = MovieHomePresenter(this, dao)
}
