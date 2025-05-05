package woowacourse.movie.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieBinding
import woowacourse.movie.movie.adapter.MovieAdapter
import woowacourse.movie.theater.TheaterFragment
import woowacourse.movie.theater.TheaterFragment.Companion.KEY_MOVIE
import woowacourse.movie.theater.TheaterFragment.Companion.KEY_THEATERS
import woowacourse.movie.ui.model.MovieFeedUiModel
import woowacourse.movie.ui.model.MovieUiModel
import woowacourse.movie.ui.model.TheaterUiModel

class MovieFragment : Fragment(), MovieContract.View {
    private val presenter = MoviePresenter(this)
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        presenter.initializeData()
    }

    override fun setupMovieList(movies: List<MovieFeedUiModel>) {
        val adapter =
            MovieAdapter(movies) { movie ->
                presenter.selectMovie(movie)
            }
        val recyclerView = binding.recyclerViewLayout
        recyclerView.adapter = adapter
    }

    override fun showTheaterDialog(
        theaters: ArrayList<TheaterUiModel>,
        movie: MovieUiModel,
    ) {
        val fragment = TheaterFragment()
        fragment.arguments =
            Bundle().apply {
                putParcelableArrayList(KEY_THEATERS, theaters)
                putParcelable(KEY_MOVIE, movie)
            }
        fragment.show(parentFragmentManager, fragment.tag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
