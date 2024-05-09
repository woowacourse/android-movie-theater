package woowacourse.movie.presentation.main.home

import android.os.Bundle
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.domain.model.home.HomeItem
import woowacourse.movie.presentation.base.BaseFragment
import woowacourse.movie.presentation.main.home.adapter.HomeMovieAdapter
import woowacourse.movie.presentation.main.home.adapter.OnMovieItemClickListener
import woowacourse.movie.presentation.main.home.theater.TheaterFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), HomeContract.View {
    override val presenter = HomePresenter(this)
    private lateinit var homeMovieAdapter: HomeMovieAdapter

    override fun onViewCreatedSetup() {
        presenter.setMoviesInfo()
    }

    override fun showMoviesInfo(items: List<HomeItem>) {
        homeMovieAdapter = HomeMovieAdapter(
            items,
            object : OnMovieItemClickListener {
                override fun onClick(movieId: Long) {
                    navigateToTheater(movieId)
                }
            }
        )

        binding.movieRecyclerView.adapter = homeMovieAdapter
    }

    private fun navigateToTheater(movieId: Long) {
        val theaterFragment = TheaterFragment()
        val bundle = Bundle()
        bundle.putLong(EXTRA_MOVIE_ID_KEY_TO_FRAGMENT, movieId)
        theaterFragment.arguments = bundle
        theaterFragment.show(parentFragmentManager, "dialog")
    }

    companion object {
        const val EXTRA_MOVIE_ID_KEY_TO_FRAGMENT = "movie_id_key_to_fragment"
    }
}
