package woowacourse.movie.fragment.movielist

import com.woowacourse.domain.Ad
import com.woowacourse.domain.Theater
import woowacourse.movie.R
import woowacourse.movie.data.MovieMockData
import woowacourse.movie.fragment.movielist.adapter.MovieRecyclerViewAdapter
import woowacourse.movie.fragment.movielist.adapter.TheaterRecyclerViewAdapter
import woowacourse.movie.model.AdUIModel
import woowacourse.movie.model.MovieUIModel
import woowacourse.movie.model.toPresentation

class HomePresenter(val view: HomeContract.View) : HomeContract.Presenter {

    override fun setMovieRecyclerView(onClickMovie: (Int) -> Unit, onClickAd: (AdUIModel) -> Unit) {

        val movieRecyclerViewAdapter = MovieRecyclerViewAdapter(
            getMovies(),
            getAd(),
            onClickMovie,
            onClickAd,
        )
        view.setMovieRecyclerView(movieRecyclerViewAdapter)
    }

    private fun getMovies(): List<MovieUIModel> {
        return MovieMockData.movies10000.map { it.toPresentation() }
    }

    private fun getAd(): AdUIModel {
        return Ad(R.drawable.ad, "https://woowacourse.github.io/").toPresentation()
    }

    override fun setTheaterRecyclerView(theaters: List<Theater>, onClickTheater: (Int) -> Unit) {
        val theaterRecyclerViewAdapter = TheaterRecyclerViewAdapter(
            theaters,
            onClickTheater
        )
        view.setTheaterRecyclerView(theaterRecyclerViewAdapter)
    }
}
