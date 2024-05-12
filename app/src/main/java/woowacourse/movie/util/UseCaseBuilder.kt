package woowacourse.movie.util

import woowacourse.movie.data.AppDatabase
import woowacourse.movie.data.movie.MovieRepositoryImpl
import woowacourse.movie.data.reservationref.ReservationRefRepositoryImpl
import woowacourse.movie.data.screeningref.ScreeningRefRepositoryImpl
import woowacourse.movie.data.theater.TheaterRepositoryImpl
import woowacourse.movie.usecase.FetchAllMoviesUseCase
import woowacourse.movie.usecase.FetchAllReservationsUseCase
import woowacourse.movie.usecase.FetchReservationWithIdUseCase
import woowacourse.movie.usecase.FetchScreeningScheduleWithMovieIdAndTheaterIdUseCase
import woowacourse.movie.usecase.FetchScreeningWithIdUseCase
import woowacourse.movie.usecase.FetchScreeningsWithMovieIdAndTheaterIdUseCase
import woowacourse.movie.usecase.FetchTheatersWithMovieIdUseCase
import woowacourse.movie.usecase.PutReservationUseCase

fun buildFetchTheatersWithMovieIdUseCase(db: AppDatabase): FetchTheatersWithMovieIdUseCase {
    val screeningRefRepository = ScreeningRefRepositoryImpl(db.screeningDao())
    val theaterRepository = TheaterRepositoryImpl(db.theaterDao())
    return FetchTheatersWithMovieIdUseCase(screeningRefRepository, theaterRepository)
}

fun buildFetchReservationWithIdUseCase(db: AppDatabase): FetchReservationWithIdUseCase {
    val fetchScreeningWithIdUseCase =
        FetchScreeningWithIdUseCase(
            MovieRepositoryImpl(db.movieDao()),
            TheaterRepositoryImpl(db.theaterDao()),
            ScreeningRefRepositoryImpl(db.screeningDao()),
        )

    return FetchReservationWithIdUseCase(
        fetchScreeningWithIdUseCase,
        ReservationRefRepositoryImpl(db.reservationDao()),
    )
}

fun buildFetchAllReservationsUseCase(db: AppDatabase): FetchAllReservationsUseCase {
    val reservationRefRepository = ReservationRefRepositoryImpl(db.reservationDao())
    val movieRepository = MovieRepositoryImpl(db.movieDao())
    val theaterRepository = TheaterRepositoryImpl(db.theaterDao())
    val screeningRefRepository = ScreeningRefRepositoryImpl(db.screeningDao())
    val screeningWithIdUseCase =
        FetchScreeningWithIdUseCase(
            movieRepository,
            theaterRepository,
            screeningRefRepository,
        )
    return FetchAllReservationsUseCase(reservationRefRepository, screeningWithIdUseCase)
}

fun buildFetchScreeningsWithMovieIdAndTheaterIdUseCase(db: AppDatabase): FetchScreeningsWithMovieIdAndTheaterIdUseCase {
    val movieRepository = MovieRepositoryImpl(db.movieDao())
    val theaterRepository = TheaterRepositoryImpl(db.theaterDao())
    val screeningRefRepository = ScreeningRefRepositoryImpl(db.screeningDao())
    return FetchScreeningsWithMovieIdAndTheaterIdUseCase(
        movieRepository,
        theaterRepository,
        screeningRefRepository,
    )
}

fun buildFetchScreeningScheduleWithMovieIdAndTheaterId(db: AppDatabase): FetchScreeningScheduleWithMovieIdAndTheaterIdUseCase {
    val fetchScreeningsWithMovieIdAndTheaterIdUseCase =
        buildFetchScreeningsWithMovieIdAndTheaterIdUseCase(db)
    return FetchScreeningScheduleWithMovieIdAndTheaterIdUseCase(
        fetchScreeningsWithMovieIdAndTheaterIdUseCase,
    )
}

fun buildFetchScreeningWithId(db: AppDatabase): FetchScreeningWithIdUseCase {
    val movieRepository = MovieRepositoryImpl(db.movieDao())
    val theaterRepository = TheaterRepositoryImpl(db.theaterDao())
    val screeningRefRepository = ScreeningRefRepositoryImpl(db.screeningDao())
    return FetchScreeningWithIdUseCase(movieRepository, theaterRepository, screeningRefRepository)
}

fun buildPutReservationUseCase(db: AppDatabase): PutReservationUseCase {
    val reservationRefRepository = ReservationRefRepositoryImpl(db.reservationDao())
    return PutReservationUseCase(reservationRefRepository)
}

fun buildFetchAllMoviesUseCase(db: AppDatabase): FetchAllMoviesUseCase {
    val movieRepository = MovieRepositoryImpl(db.movieDao())
    return FetchAllMoviesUseCase(movieRepository)
}
