package woowacourse.movie.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.list.adapter.MovieAdapter
import woowacourse.movie.list.contract.MovieListContract
import woowacourse.movie.list.model.Advertisement
import woowacourse.movie.list.model.Movie
import woowacourse.movie.list.presenter.MovieListPresenter

class HomeFragment : Fragment(), MovieListContract.View {
    override val presenter = MovieListPresenter(this)
    private lateinit var binding: FragmentHomeBinding
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        presenter.setMoviesInfo()
        presenter.setListViewClickListenerInfo()

        return binding.root
    }

    override fun showMoviesInfo(
        movies: List<Movie>,
        advertisements: List<Advertisement>,
    ) {
        movieAdapter = MovieAdapter(movies, advertisements)

        binding.movieRecyclerView.adapter = movieAdapter
        binding.movieRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun setOnListViewClickListener() {
        movieAdapter.setItemClickListener(
            object : MovieAdapter.OnItemClickListener {
                override fun onClick(movieId: Long) {
                    val theaterFragment = TheaterFragment()
                    val bundle = Bundle()
                    bundle.putLong(EXTRA_MOVIE_ID_KEY_TO_FRAGMENT, movieId)
                    theaterFragment.arguments = bundle
                    binding.fragmentContainerView.visibility = View.VISIBLE
                    theaterFragment.show(parentFragmentManager, "dailog")
                }
            },
        )
    }

    companion object {
        const val EXTRA_MOVIE_ID_KEY = "movie_id_key"
        const val EXTRA_MOVIE_ID_KEY_TO_FRAGMENT = "movie_id_key_to_fragment"
    }
}
