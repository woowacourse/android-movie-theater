package woowacourse.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.databinding.FragmentMovieBinding
import woowacourse.movie.movie.MovieContract
import woowacourse.movie.movie.MoviePresenter
import woowacourse.movie.movie.MovieUiModel
import woowacourse.movie.movie.TheaterFragment
import woowacourse.movie.movie.TheaterFragment.Companion.KEY_MOVIE
import woowacourse.movie.movie.TheaterFragment.Companion.KEY_THEATERS
import woowacourse.movie.movie.TheaterUiModel
import woowacourse.movie.movie.adapter.MovieAdapter

class MovieFragment : Fragment(), MovieContract.View {
    private lateinit var presenter: MoviePresenter
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

        presenter = MoviePresenter(this)
        presenter.initializeData()
    }

    override fun setupMovieList(movies: List<MovieUiModel>) {
        val adapter =
            MovieAdapter(movies) { movie ->
                presenter.setTheaters(movie)
            }
        val recyclerView = binding.recyclerViewLayout
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
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
