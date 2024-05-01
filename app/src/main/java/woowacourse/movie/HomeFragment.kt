package woowacourse.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.list.adapter.MovieAdapter
import woowacourse.movie.list.contract.MovieListContract
import woowacourse.movie.list.model.Advertisement
import woowacourse.movie.list.model.Movie
import woowacourse.movie.list.presenter.MovieListPresenter
import woowacourse.movie.list.view.MovieListActivity

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

//        binding.fragment = this

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
                    bundle.putLong(MovieListActivity.EXTRA_MOVIE_ID_KEY_TO_FRAGMENT, movieId)
                    theaterFragment.arguments = bundle
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, theaterFragment)
                        .commit()
                    binding.fragmentContainerView.visibility = View.VISIBLE
                }
            },
        )
    }
}
