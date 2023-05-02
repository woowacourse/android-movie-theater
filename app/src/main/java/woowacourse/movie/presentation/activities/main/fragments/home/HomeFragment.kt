package woowacourse.movie.presentation.activities.main.fragments.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.main.fragments.home.contract.HomeContract
import woowacourse.movie.presentation.activities.main.fragments.home.contract.presenter.HomePresenter
import woowacourse.movie.presentation.activities.main.fragments.home.recyclerview.MovieListAdapter
import woowacourse.movie.presentation.activities.main.fragments.home.recyclerview.OnEndScrollListener
import woowacourse.movie.presentation.activities.ticketing.TicketingActivity
import woowacourse.movie.presentation.model.movieitem.Ad
import woowacourse.movie.presentation.model.movieitem.ListItem

class HomeFragment : Fragment(R.layout.fragment_home), HomeContract.View {
    override val presenter: HomeContract.Presenter = HomePresenter(this)

    private lateinit var movieListAdapter: MovieListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        presenter.loadMoreMovies()
    }

    private fun initMovieListAdapter() {
        movieListAdapter = MovieListAdapter(
            adTypes = presenter.loadAds(),
            onItemClick = presenter::onMovieClick,
        )
    }

    private fun initRecyclerView() {
        val movieRecyclerView = requireView().findViewById<RecyclerView>(R.id.movies_rv)

        initMovieListAdapter()
        movieRecyclerView.adapter = movieListAdapter
        movieRecyclerView.addOnScrollListener(
            OnEndScrollListener { presenter.loadMoreMovies() }
        )
    }

    override fun showMoreMovies(items: List<ListItem>) {
        movieListAdapter.appendAll(items)
    }

    override fun showTicketScreen(item: ListItem) {
        val intent = Intent(requireContext(), TicketingActivity::class.java)
            .putExtra(MOVIE_KEY, item)
        startActivity(intent)
    }

    override fun showAdWebSite(item: ListItem) {
        if (item !is Ad) return

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
        startActivity(intent)
    }

    companion object {
        internal const val MOVIE_KEY = "movie_key"
        private val homeFragment = HomeFragment()

        fun newInstance(): HomeFragment = homeFragment
    }
}
