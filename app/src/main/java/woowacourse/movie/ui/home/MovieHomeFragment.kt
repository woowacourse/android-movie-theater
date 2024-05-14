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
import woowacourse.movie.ui.home.theater.TheaterSelectionBottomSheetFragment

class MovieHomeFragment : Fragment(), MovieHomeContract.View {
    private lateinit var binding: FragmentMovieHomeBinding
    private val adapter by lazy { movieContentAdapter() }
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

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        presenter.loadMovieContents()
    }

    override fun showMovieContents(movieContents: List<MovieContent>) {
        binding.movieContentList.adapter = adapter
        adapter.updateMovieContents(movieContents)
    }

    private fun movieContentAdapter() =
        MovieContentAdapter { id ->
            val fragment = TheaterSelectionBottomSheetFragment()
            val bundle = Bundle()
            bundle.putLong(MovieHomeKey.MOVIE_CONTENT_ID, id)
            fragment.arguments = bundle
            fragment.show(parentFragmentManager, fragment.tag)
        }
}
