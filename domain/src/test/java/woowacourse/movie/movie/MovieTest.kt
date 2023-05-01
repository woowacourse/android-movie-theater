package woowacourse.movie.movie

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.ticket.Position
import woowacourse.movie.ticket.Seat
import woowacourse.movie.ticket.SeatRank
import woowacourse.movie.ticket.Ticket
import woowacourse.movie.util.Movie
import java.time.LocalDate
import java.time.LocalDateTime

class MovieTest {
    @Test
    fun `상영 기간을 계산해서 리스트로 반환한다`() {
        // given
        val movie = Movie(
            startDate = LocalDate.of(2023, 4, 12),
            endDate = LocalDate.of(2023, 4, 15)
        )

        val expected = listOf<LocalDate>(
            LocalDate.of(2023, 4, 12),
            LocalDate.of(2023, 4, 13),
            LocalDate.of(2023, 4, 14),
            LocalDate.of(2023, 4, 15),
        )

        // when
        val actual = movie.screeningDates

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `날짜시간, 좌석을 입력하면 티켓을 반환한다`() {
        // given
        val movie = Movie(
            startDate = LocalDate.of(2023, 4, 12),
            endDate = LocalDate.of(2023, 4, 15)
        )
        val screeningDateTime = LocalDateTime.of(2023, 4, 14, 10, 0)
        val seat = Seat(
            rank = SeatRank.B,
            position = Position(1, 1)
        )

        // when
        val actual = movie.reserve(screeningDateTime, seat)

        // then
        val expected = Ticket(1, "해리 포터와 마법사의 돌", screeningDateTime, seat)
        assertThat(actual).isEqualTo(expected)
    }

//    @Test
//    fun `평일에는 오전 10시부터 두 시간 간격으로 상영한다`() {
//        // given
//        val weekDay = LocalDate.of(2023, 4, 12)
//        val expected = (10..23 step 2).map { LocalTime.of(it, 0) }
//
//        // when
//        val actual = Movie.getScreeningTime(weekDay)
//
//        // then
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `주말에는 오전 9시부터 두 시간 간격으로 상영한다`() {
//        // given
//        val weekDay = LocalDate.of(2023, 4, 15)
//        val expected = (9..23 step 2).map { LocalTime.of(it, 0) }
//
//        // when
//        val actual = Movie.getScreeningTime(weekDay)
//
//        // then
//        assertThat(actual).isEqualTo(expected)
//    }
}
