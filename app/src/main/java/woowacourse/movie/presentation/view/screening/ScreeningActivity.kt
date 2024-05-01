package woowacourse.movie.presentation.view.screening

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.admodel.Ad
import woowacourse.movie.presentation.view.screening.adapter.MovieListAdapter
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.uimodel.MovieUiModel
import woowacourse.movie.presentation.view.reservation.detail.MovieDetailActivity
import woowacourse.movie.presentation.view.screening.theater.TheaterBottomSheetDialogFragment

class ScreeningActivity : BaseActivity(), ScreeningContract.View, ScreeningContract.ViewActions {
    private lateinit var adapter: MovieListAdapter
    private lateinit var presenter: ScreeningContract.Presenter

    override fun getLayoutResId(): Int = R.layout.activity_screening

    override fun onCreateSetup(savedInstanceState: Bundle?) {
        adapter = MovieListAdapter(emptyList(), emptyList(), this)
        presenter = ScreeningPresenterImpl()
        presenter.attachView(this)
        showMovieList()
    }

    override fun onUpdateMovies(movies: List<MovieUiModel>) {
        adapter.updateMovieList(movies)
    }

    override fun onUpdateAds(ads: List<Ad>) {
        adapter.updateAdsList(ads)
    }

    override fun showMovieList() {
        val movieListView = findViewById<RecyclerView>(R.id.movieList)
        movieListView.layoutManager = LinearLayoutManager(this)
        movieListView.adapter = adapter
    }

    override fun showTheaterBottomSheet(movieId: Int) {
        val theaterDialog = TheaterBottomSheetDialogFragment()
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID_KEY, movieId)
        theaterDialog.arguments = bundle
        theaterDialog.show(supportFragmentManager, "")
    }

    override fun reserveMovie(movieId: Int) {
        presenter.onReserveButtonClicked(movieId)
    }

    override fun showAdContent(content: String) {
        runOnUiThread {
            Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
        }
    }

    override fun moveToMovieDetail(movieId: Int) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.INTENT_MOVIE_ID, movieId)
        startActivity(intent)
    }

    companion object {
        const val MOVIE_ID_KEY = "movieId"
        const val DEFAULT_MOVIE_ID = -1
    }
}
