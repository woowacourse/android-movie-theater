package woowacourse.movie.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieHomeBinding
import woowacourse.movie.model.data.MovieContentsImpl
import woowacourse.movie.model.movie.MovieContent
import woowacourse.movie.ui.home.adapter.MovieContentAdapter

class MovieHomeFragment : Fragment(), MovieHomeContract.View {
    private lateinit var binding: FragmentMovieHomeBinding
    private val presenter: MovieHomePresenter by lazy {
        MovieHomePresenter(
            this,
            MovieContentsImpl,
        )
    }

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
        binding.movieContentList.adapter =
            MovieContentAdapter(movieContents) { id ->
                val bundle = Bundle()
                val fragment = TheaterSelectionBottomSheetFragment()
                fragment.apply {
                    bundle.putLong(MovieHomeKey.MOVIE_CONTENT_ID, id)
                    arguments = bundle
                    show(parentFragmentManager, fragment.tag)
                }
            }
    }
}
