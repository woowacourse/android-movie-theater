package woowacourse.movie.presentation.view.screening

import android.os.Bundle
import android.widget.Toast
import woowacourse.movie.R
import woowacourse.movie.domain.admodel.Ad
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.uimodel.MovieUiModel
import woowacourse.movie.presentation.view.screening.adapter.MovieListAdapter
import woowacourse.movie.presentation.view.screening.theater.TheaterBottomSheetDialogFragment

class ScreeningActivity : BaseActivity(), ScreeningContract.View, ScreeningContract.ViewActions {
    private lateinit var adapter: MovieListAdapter
    private lateinit var presenter: ScreeningContract.Presenter

    override fun getLayoutResId(): Int = R.layout.activity_screening

    override fun onCreateSetup(savedInstanceState: Bundle?) {
        adapter = MovieListAdapter(emptyList(), emptyList(), this)
        presenter = ScreeningPresenterImpl()
        presenter.attachView(this)
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

    companion object {
        const val MOVIE_ID_KEY = "movieId"
    }
}
