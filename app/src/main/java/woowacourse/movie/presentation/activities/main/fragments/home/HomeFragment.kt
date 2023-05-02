package woowacourse.movie.presentation.activities.main.fragments.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.ticketing.TicketingActivity
import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.Movie
import woowacourse.movie.presentation.model.item.Reservation

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMovieListAdapter(view)
    }

    private fun initMovieListAdapter(view: View) {
        val movieRecyclerView = view.findViewById<RecyclerView>(R.id.movies_rv)
        movieListAdapter = MovieListAdapter(
            adTypes = Ad.provideDummy(),
            onItemClick = { item ->
                when (item) {
                    is Movie -> startTicketingActivity(item)
                    is Ad -> accessAdWebPage(item)
                    is Reservation -> {}
                }
            },
        ).also { it.appendAll(Movie.provideDummy()) }

        movieRecyclerView.adapter = movieListAdapter
        movieRecyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(DOWN_DIRECTION) && dy > 0) {
                    movieListAdapter.appendAll(Movie.provideDummy())
                }
            }
        })
    }

    private fun startTicketingActivity(movie: Movie) {
        val intent = Intent(requireContext(), TicketingActivity::class.java)
            .putExtra(MOVIE_KEY, movie)
        startActivity(intent)
    }

    private fun accessAdWebPage(ads: Ad) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ads.url))
        startActivity(intent)
    }

    companion object {
        internal const val MOVIE_KEY = "movie_key"
        private const val DOWN_DIRECTION = 1

        private val homeFragment = HomeFragment()

        fun getInstance(): HomeFragment = homeFragment
    }
}
