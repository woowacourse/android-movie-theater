package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.RoomMovieRepository
import woowacourse.movie.data.database.MovieDatabase

class TheaterApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initRoomRepo()
    }

    private fun initRoomRepo() {
        Thread {
            val database = MovieDatabase.database(this)
            RoomMovieRepository.initialize(
                database.movieDao(),
                database.movieTheaterDao(),
                database.screeningMovieDao(),
                database.movieReservationDao(),
            )
        }.start()
    }
}
