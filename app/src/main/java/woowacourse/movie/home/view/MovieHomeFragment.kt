package woowacourse.movie.home.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentMovieHomeBinding
import woowacourse.movie.home.presenter.MovieHomePresenter
import woowacourse.movie.home.presenter.contract.MovieHomeContract
import woowacourse.movie.home.view.adapter.movie.HomeContent
import woowacourse.movie.home.view.adapter.movie.HomeContent.Advertisement
import woowacourse.movie.home.view.adapter.movie.HomeContentAdapter
import woowacourse.movie.home.view.listener.MovieHomeClickListener

class MovieHomeFragment : Fragment(), MovieHomeContract.View, MovieHomeClickListener {
    private lateinit var binding: FragmentMovieHomeBinding
    private lateinit var movieHomePresenter: MovieHomePresenter
    private lateinit var homeContentAdapter: HomeContentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentMovieHomeBinding.inflate(inflater, container, false)
        initializeHomeFragment()
        return binding.root
    }

    private fun initializeHomeFragment() {
        homeContentAdapter = HomeContentAdapter(this)
        binding.recyclerViewHome.adapter = homeContentAdapter

        movieHomePresenter = MovieHomePresenter(this)
        movieHomePresenter.loadHomeContents()
    }

    override fun displayHomeContents(homeContents: List<HomeContent>) {
        homeContentAdapter.updateHomeContents(homeContents)
    }

    override fun onReservationButtonClick(movieId: Long) {
        val theaterSelectionInstance = TheaterSelectionFragment.newInstance(movieId)
        theaterSelectionInstance.show(parentFragmentManager, theaterSelectionInstance.tag)
    }

    override fun onAdvertisementClick(advertisement: Advertisement) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(advertisement.link))
        startActivity(intent)
    }
}
