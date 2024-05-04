package woowacourse.movie.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieHomeBinding
import woowacourse.movie.model.data.MovieContentsImpl
import woowacourse.movie.model.movie.MovieContent
import woowacourse.movie.ui.ReservationButtonClickListener
import woowacourse.movie.ui.home.adapter.MovieContentAdapter

class MovieHomeFragment : Fragment(), MovieHomeContract.View, ReservationButtonClickListener {
    private lateinit var binding: FragmentMovieHomeBinding
    private lateinit var movieContents: List<MovieContent>
    private val presenter: MovieHomePresenter by lazy { MovieHomePresenter(this, MovieContentsImpl) }
    private val adapter: MovieContentAdapter by lazy { MovieContentAdapter(movieContents, this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_home, container, false)
        presenter.loadMovieContents()
        return binding.root
    }

    override fun showMovieContents(movieContents: List<MovieContent>) {
        this.movieContents = movieContents
        binding.movieContentList.adapter = adapter
    }

    override fun onClick(movieContentId: Long) {
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
}
