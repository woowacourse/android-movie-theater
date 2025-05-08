package woowacourse.movie.model.ticket

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import woowacourse.movie.model.movie.MovieTime
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.presenter.MOVIE
import java.time.LocalDate
import java.time.LocalTime

class MovieTicketTest {
    @Test
    fun `좌석들을 통해서 총 결제 금액을 반환한다`() {
        // given
        val ticket =
            MovieTicket(
                movie = MOVIE,
                movieDate = LocalDate.of(2025, 4, 22),
                movieTime = MovieTime(LocalTime.of(10, 0)),
                seats = listOf(Seat(0, 0), Seat(1, 1), Seat(2, 2), Seat(4, 0)),
                theater = Theater(1, "JAY 극장"),
            )

        // when
        val price = ticket.price

        // then
        Assertions.assertThat(price).isEqualTo(47000)
    }
}
