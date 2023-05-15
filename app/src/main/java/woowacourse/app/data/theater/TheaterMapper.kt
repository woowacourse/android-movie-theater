package woowacourse.app.data.theater

import woowacourse.domain.movie.Movie
import woowacourse.domain.theater.ScreeningMovie
import woowacourse.domain.theater.SeatStructure
import woowacourse.domain.theater.Theater

object TheaterMapper {
    fun TheaterEntity.toTheater(screeningMovies: List<ScreeningMovie>): Theater {
        return Theater(
            id = this.id,
            name = this.name,
            seatStructure = SeatStructure(
                rowSize = this.rowSize,
                columnSize = this.columnSize,
                sRankRange = TheaterEntity.convertToSeatRanges(this.sRankRange),
                aRankRange = TheaterEntity.convertToSeatRanges(this.aRankRange),
                bRankRange = TheaterEntity.convertToSeatRanges(this.bRankRange),
            ),
            screeningMovies = screeningMovies,
        )
    }

    fun MovieTimeEntity.toScreeningMovie(movie: Movie): ScreeningMovie {
        return ScreeningMovie(
            movie = movie,
            times = MovieTimeEntity.convertToLocalTimes(this.times),
        )
    }
}
