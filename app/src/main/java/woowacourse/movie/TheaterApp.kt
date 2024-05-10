package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.RoomMovieRepository
import woowacourse.movie.data.dao.MovieDao
import woowacourse.movie.data.dao.MovieTheaterDao
import woowacourse.movie.data.dao.ScreeningMovieDao
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.entity.Movie
import woowacourse.movie.data.entity.MovieTheater
import woowacourse.movie.data.entity.ScreeningMovie

class TheaterApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initRoomRepo()
    }

    private fun initRoomRepo() {
        Thread {
            val database = MovieDatabase.database(this)
            val movieDao = database.movieDao()
            val movieTheaterDao = database.movieTheaterDao()
            val screeningMovieDao = database.screeningMovieDao()
            val reservationDao = database.movieReservationDao()

            addInitData(movieDao, movieTheaterDao, screeningMovieDao)

            RoomMovieRepository.initialize(
                movieDao,
                movieTheaterDao,
                screeningMovieDao,
                reservationDao,
            )
        }.start()
    }

    private fun addInitData(
        movieDao: MovieDao,
        movieTheaterDao: MovieTheaterDao,
        screeningMovieDao: ScreeningMovieDao,
    ) {
        movieDao.insert(Movie.STUB)
        movieTheaterDao.insertAll(
            MovieTheater.STUB_A,
            MovieTheater.STUB_B,
            MovieTheater.STUB_C,
        )
        screeningMovieDao.insertAll(
            ScreeningMovie.STUB_A,
            ScreeningMovie.STUB_B,
            ScreeningMovie.STUB_C,
        )
    }
}
