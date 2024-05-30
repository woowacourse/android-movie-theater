package woowacourse.movie.data.repository

import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.data.source.ScreenDataSource
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Grade
import woowacourse.movie.domain.model.Position
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import java.time.LocalDate

class FakeScreenDataSource : ScreenDataSource {
    private val screens = listOf(fakeScreen1Data, fakeScreen2Data, fakeScreen3Data)

    override fun load(): List<ScreenData> {
        return screens
    }

    override fun findById(id: Int): Result<ScreenData> = runCatching { screens.find { it.id == id } ?: throw NoSuchElementException() }

    override fun seats(screenId: Int): Seats =
        Seats(
            Seat(Position(0, 0), Grade.S),
            Seat(Position(1, 1), Grade.A),
            Seat(Position(2, 2), Grade.B),
        )

    companion object {
        val fakeScreen1Data =
            ScreenData(
                1,
                FakeMovieDataSource.fakeMovie1Data,
                DateRange(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 3)),
            )
        val fakeScreen2Data =
            ScreenData(
                2,
                FakeMovieDataSource.fakeMovie2Data,
                DateRange(LocalDate.of(2024, 3, 2), LocalDate.of(2024, 3, 4)),
            )
        val fakeScreen3Data =
            ScreenData(
                3,
                FakeMovieDataSource.fakeMovie3Data,
                DateRange(LocalDate.of(2024, 3, 3), LocalDate.of(2024, 3, 5)),
            )
    }
}
