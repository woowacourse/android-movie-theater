package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.RoomMovieRepository
import woowacourse.movie.data.database.MovieDatabase
import kotlin.concurrent.thread

class TheaterApp : Application() {
    private lateinit var db: MovieDatabase

    override fun onCreate() {
        super.onCreate()
        initRoomRepo()
    }

    private fun initRoomRepo() {
        initRoomDb()
        initRepo()
    }

    private fun initRoomDb() {
        thread {
            db = MovieDatabase.database(this)
        }.join()
    }

    private fun initRepo() {
        thread {
            val movieDao = db.movieDao()
            val movieTheaterDao = db.movieTheaterDao()
            val screeningMovieDao = db.screeningMovieDao()
            val reservationDao = db.movieReservationDao()

            RoomMovieRepository.initialize(
                movieDao,
                movieTheaterDao,
                screeningMovieDao,
                reservationDao,
            )
        }.join()
    }
}
