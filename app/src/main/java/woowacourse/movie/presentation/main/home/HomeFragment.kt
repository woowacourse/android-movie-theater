package woowacourse.movie.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.domain.model.home.Advertisement
import woowacourse.movie.domain.model.home.Movie
import woowacourse.movie.presentation.main.home.adapter.HomeMovieAdapter
import woowacourse.movie.presentation.main.home.theater.TheaterFragment

class HomeFragment : Fragment(), HomeContract.View {
    override val presenter = HomePresenter(this)
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMovieAdapter: HomeMovieAdapter

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
        homeMovieAdapter = HomeMovieAdapter(movies, advertisements)

        binding.movieRecyclerView.adapter = homeMovieAdapter
        binding.movieRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun setOnListViewClickListener() {
        homeMovieAdapter.setItemClickListener(
            object : HomeMovieAdapter.OnItemClickListener {
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
