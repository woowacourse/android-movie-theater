package woowacourse.movie.fragment.movielist

import com.woowacourse.domain.Ad
import com.woowacourse.domain.TheaterMovie
import woowacourse.movie.R
import woowacourse.movie.data.MovieMockData
import woowacourse.movie.model.AdUIModel
import woowacourse.movie.model.MovieUIModel
import woowacourse.movie.model.toPresentation

class HomePresenter(val view: HomeContract.View) : HomeContract.Presenter {

    override fun setMovieRecyclerView() {
        view.setMovieRecyclerView(getMovies(), getAd())
    }

    private fun getMovies(): List<MovieUIModel> {
        return MovieMockData.movies10000.map { it.toPresentation() }
    }

    private fun getAd(): AdUIModel {
        return Ad(R.drawable.ad, "https://woowacourse.github.io/").toPresentation()
    }

    override fun setTheaterRecyclerView(position: Int) {
        val theaterMovie = mutableListOf<TheaterMovie>()
        for (theater in MovieMockData.theaterData) {
            val hasMovie =
                theater.schedules.filter { it.movie == MovieMockData.movies10000[position] }
            if (hasMovie.isNotEmpty()) theaterMovie.add(
                TheaterMovie(
                    theater.name,
                    hasMovie.first()
                )
            )
        }

        view.setTheaterRecyclerView(theaterMovie)
    }
}
