package woowacourse.movie.presentation.view.main.home.theater

import android.util.Log
import woowacourse.movie.data.TheaterData
import woowacourse.movie.presentation.model.Theater

class TheaterPresenter(private val view: TheaterContract.View) : TheaterContract.Presenter {
    override fun getTheaters(movieName: String) {
        val theaters = TheaterData.findByMovieName(movieName)
        Log.d("theatersDataSize", theaters.size.toString())
        val movieSchedule = TheaterData.getMovieSchedule(movieName)
        view.showTheatersView(theaters.map {
            Theater.from(it)
        }, movieSchedule)
    }
}