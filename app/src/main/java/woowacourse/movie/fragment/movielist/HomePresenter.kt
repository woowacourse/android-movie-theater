package woowacourse.movie.fragment.movielist

import com.woowacourse.domain.Ad
import woowacourse.movie.R
import woowacourse.movie.fragment.movielist.adapter.MovieRecyclerViewAdapter
import woowacourse.movie.model.AdUIModel
import woowacourse.movie.model.toPresentation
import woowacourse.movie.data.MovieMockData
import woowacourse.movie.movie.MovieUIModel

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
}
