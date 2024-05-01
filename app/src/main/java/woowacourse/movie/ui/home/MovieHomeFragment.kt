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
import woowacourse.movie.ui.home.adapter.MovieContentAdapter

class MovieHomeFragment : Fragment(), MovieHomeContract.View {
    private lateinit var binding: FragmentMovieHomeBinding
    private val presenter: MovieHomePresenter by lazy { MovieHomePresenter(this, MovieContentsImpl) }

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
            MovieContentAdapter(movieContents) { view, id ->
                val fragment = TheaterSelectionBottomSheetFragment()
                val bundle = Bundle()
                bundle.putLong(MovieHomeKey.MOVIE_CONTENT_ID, id)
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
}
