package woowacourse.movie.presentation.view.bottom_navigation_bar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.admodel.Ad
import woowacourse.movie.presentation.uimodel.MovieUiModel
import woowacourse.movie.presentation.view.screening.ScreeningContract
import woowacourse.movie.presentation.view.screening.ScreeningPresenterImpl
import woowacourse.movie.presentation.view.screening.adapter.MovieListAdapter
import woowacourse.movie.presentation.view.screening.theater.TheaterBottomSheetDialogFragment

class Home : Fragment(), ScreeningContract.View, ScreeningContract.ViewActions {
    private lateinit var adapter: MovieListAdapter
    private lateinit var presenter: ScreeningContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MovieListAdapter(emptyList(), emptyList(), this)
        presenter = ScreeningPresenterImpl()
        presenter.attachView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieListView = view.findViewById<RecyclerView>(R.id.movieList)
        movieListView.layoutManager = LinearLayoutManager(context)
        movieListView.adapter = adapter
    }

    override fun onUpdateMovies(movies: List<MovieUiModel>) {
        adapter.updateMovieList(movies)
    }

    override fun onUpdateAds(ads: List<Ad>) {
        adapter.updateAdsList(ads)
    }

    override fun showTheaterBottomSheet(movieId: Int) {
        val theaterDialog = TheaterBottomSheetDialogFragment()
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID_KEY, movieId)
        theaterDialog.arguments = bundle
        theaterDialog.show(parentFragmentManager, null)
    }

    override fun reserveMovie(movieId: Int) {
        presenter.onReserveButtonClicked(movieId)
    }

    override fun showAdContent(content: String) {
        activity?.runOnUiThread {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val MOVIE_ID_KEY = "movieId"
        const val DEFAULT_MOVIE_ID = -1
    }
}
