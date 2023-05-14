package woowacourse.app.data.theater

import android.util.Log
import woowacourse.app.data.movie.MovieDataSource
import woowacourse.app.data.movie.MovieMapper.toMovie
import woowacourse.app.data.theater.TheaterMapper.toScreeningMovie
import woowacourse.app.data.theater.TheaterMapper.toTheater
import woowacourse.domain.theater.ScreeningMovie
import woowacourse.domain.theater.SeatStructure
import woowacourse.domain.theater.Theater
import woowacourse.domain.theater.TheaterRepository
import woowacourse.domain.util.CgvError.DataSourceError.DataSourceNoSuchId
import woowacourse.domain.util.CgvResult

class TheaterRepositoryImpl(
    private val theaterDataSource: TheaterDataSource,
    private val movieTimeDataSource: MovieTimeDataSource,
    private val movieDataSource: MovieDataSource,
) : TheaterRepository {
    override fun getTheaters(): List<Theater> {
        val theaterEntities = theaterDataSource.getTheaterEntities()
        return theaterEntities.map { it.toTheater(getScreeningMovies(it.id)) }
    }

    override fun getTheater(theaterId: Long): CgvResult<Theater> {
        val theaterEntity: TheaterEntity = theaterDataSource.getTheaterEntity(theaterId)
            ?: return CgvResult.Failure(DataSourceNoSuchId())
        return CgvResult.Success(theaterEntity.toTheater(getScreeningMovies(theaterId)))
    }

    override fun addTheater(
        theaterName: String,
        screeningMovies: List<ScreeningMovie>,
        seatStructure: SeatStructure,
    ) {
        val movieIds = screeningMovies.map { it.movie.id }
        val theaterId = theaterDataSource.addTheaterEntity(theaterName, movieIds, seatStructure)
        screeningMovies.map {
            movieTimeDataSource.addMovieTimeEntity(
                theaterId,
                it.movie.id,
                it.times.joinToString(",", it.toString()),
            )
        }
        Log.d("극장", "TheaterRepositoryImpl의 addTheater 들어옴")
    }

    private fun getScreeningMovies(theaterId: Long): List<ScreeningMovie> {
        return movieTimeDataSource.getMovieTimeEntities(theaterId)
            .map {
                it.toScreeningMovie(
                    (movieDataSource.getMovieEntity(it.movieId) ?: return emptyList()).toMovie(),
                )
            }
    }
}
