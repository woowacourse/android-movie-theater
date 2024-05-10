package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.RoomMovieRepository
import woowacourse.movie.data.dao.MovieDao
import woowacourse.movie.data.dao.MovieReservationDao
import woowacourse.movie.data.dao.MovieTheaterDao
import woowacourse.movie.data.dao.ScreeningMovieDao
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.entity.Movie
import woowacourse.movie.data.entity.MovieTheater
import woowacourse.movie.data.entity.ScreeningMovie
import kotlin.concurrent.thread

class TheaterApp : Application() {
    private lateinit var db: MovieDatabase
    private lateinit var movieDao: MovieDao
    private lateinit var movieTheaterDao: MovieTheaterDao
    private lateinit var screeningMovieDao: ScreeningMovieDao
    private lateinit var reservationDao: MovieReservationDao

    override fun onCreate() {
        super.onCreate()
        initRoomRepo()
    }

    private fun initRoomRepo() {
        initRoomDb()
        createDao()
        initRepo()
        addInitData()
    }

    private fun initRoomDb() {
        thread {
            db = MovieDatabase.database(this)
        }.join()
    }

    private fun createDao() {
        thread {
            movieDao = db.movieDao()
            movieTheaterDao = db.movieTheaterDao()
            screeningMovieDao = db.screeningMovieDao()
            reservationDao = db.movieReservationDao()
        }.join()
    }

    private fun initRepo() {
        thread {
            RoomMovieRepository.initialize(
                movieDao,
                movieTheaterDao,
                screeningMovieDao,
                reservationDao,
            )
        }.join()
    }

    private fun addInitData() {
        thread {
            movieTheaterDao.insert(MovieTheater.STUB_A)
            movieTheaterDao.insert(MovieTheater.STUB_B)
            movieTheaterDao.insert(MovieTheater.STUB_C)
        }.join()
        thread {
            movieDao.insert(Movie.STUB)
        }.join()

        thread {
            screeningMovieDao.insertAll(
                ScreeningMovie.STUB_A,
                ScreeningMovie.STUB_B,
                ScreeningMovie.STUB_C,
            )
        }.join()
    }
}
