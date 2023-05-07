package woowacourse.movie.presentation.activities.main.fragments.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.main.fragments.theaterPicker.TheaterPickerDialog
import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.Movie
import woowacourse.movie.presentation.model.item.Reservation
import woowacourse.movie.presentation.model.item.Theater

class HomeFragment : Fragment(R.layout.fragment_home), HomeContract.View {
    override lateinit var presenter: HomePresenter
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = HomePresenter(this)
        initMovieListAdapter(view)
    }

    private fun initMovieListAdapter(view: View) {
        val movieRecyclerView = view.findViewById<RecyclerView>(R.id.movies_rv)
        movieListAdapter = MovieListAdapter(
            adTypes = Ad.provideDummy(),
            onItemClick = { item ->
                when (item) {
                    is Movie -> presenter.onClickedMovie(item)
                    is Ad -> presenter.onClickedAd(item)
                    is Reservation -> {}
                    is Theater -> {}
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

    override fun selectTheater(movie: Movie) {
        val theaterPickerDialog = TheaterPickerDialog.getInstance(movie)
        theaterPickerDialog.show(parentFragmentManager, TheaterPickerDialog.TAG)
    }

    override fun accessAdWebPage(ads: Ad) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ads.url))
        startActivity(intent)
    }

    companion object {
        private const val DOWN_DIRECTION = 1
    }
}
