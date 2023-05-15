package woowacourse.movie.presentation.view.main.home.theater

import woowacourse.movie.data.TheaterData
import woowacourse.movie.presentation.model.Movie
import woowacourse.movie.presentation.model.Theater

class TheaterPresenter(private val view: TheaterContract.View, private val movie: Movie?) :
    TheaterContract.Presenter {
    init {
        if (movie == null) {
            view.finishErrorView()
        }
    }

    override fun getTheaters() {
        val theaters = TheaterData.findByMovieName(movie!!.title)
        val movieSchedule = TheaterData.getMovieSchedule(movie.title)
        view.showTheatersView(theaters.map {
            Theater.from(it)
        }, movieSchedule)
    }
}