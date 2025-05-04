package woowacourse.movie.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieBinding
import woowacourse.movie.movie.adapter.MovieAdapter
import woowacourse.movie.theater.TheaterFragment
import woowacourse.movie.theater.TheaterFragment.Companion.KEY_MOVIE
import woowacourse.movie.theater.TheaterFragment.Companion.KEY_THEATERS
import woowacourse.movie.ui.model.MovieUiModel
import woowacourse.movie.ui.model.TheaterUiModel

class MovieFragment : Fragment(), MovieContract.View {
    private val presenter = MoviePresenter(this)
    private lateinit var binding: FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        presenter.initializeData()
    }

    override fun setupMovieList(movies: List<MovieUiModel>) {
        binding.recyclerViewLayout.adapter =
            MovieAdapter(movies) { movie ->
                presenter.setTheaters(movie)
            }
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showTheaterDialog(
        theaters: ArrayList<TheaterUiModel>,
        movie: MovieUiModel,
    ) {
        val fragment = TheaterFragment()
        val bundle = Bundle()

        bundle.putParcelableArrayList(KEY_THEATERS, theaters)
        bundle.putParcelable(KEY_MOVIE, movie)
        fragment.arguments = bundle

        fragment.show(parentFragmentManager, fragment.tag)
    }
}
