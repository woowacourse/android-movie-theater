package woowacourse.movie.presentation.views.main.fragments.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.presentation.model.movieitem.Ad
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.views.main.fragments.home.contract.HomeContract
import woowacourse.movie.presentation.views.main.fragments.home.contract.presenter.HomePresenter
import woowacourse.movie.presentation.views.main.fragments.home.recyclerview.MovieListAdapter
import woowacourse.movie.presentation.views.main.fragments.home.recyclerview.OnEndScrollListener
import woowacourse.movie.presentation.views.main.fragments.theater.TheaterPickerDialog

class HomeFragment : Fragment(R.layout.fragment_home), HomeContract.View {
    override val presenter: HomeContract.Presenter = HomePresenter()

    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attach(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        presenter.loadMoreMovies()
    }

    private fun initMovieListAdapter() {
        movieListAdapter = MovieListAdapter(
            adTypes = presenter.loadAds(),
            onItemClick = presenter::onItemClick,
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

    override fun showTheaterPickerScreen(item: Movie) {
        val theaterPickerDialog = TheaterPickerDialog.getInstance(item)
        theaterPickerDialog.show(childFragmentManager, TheaterPickerDialog.TAG)
    }

    override fun showAdWebSite(item: Ad) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
        startActivity(intent)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    companion object {
        internal const val TAG = "HomeFragment"
    }
}
