package woowacourse.movie.detail.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class DetailTicketCountDataTest {
    private lateinit var movieReservationTicketCountData: DetailTicketCountData

    @BeforeEach
    fun setup() {
        movieReservationTicketCountData = DetailTicketCountData
    }

    @AfterEach
    fun tearDown() {
        movieReservationTicketCountData.initTicketCount(1)
    }

    @Test
    fun `수량의 기본 값은 1이다`() {
        assertThat(movieReservationTicketCountData.ticketCount.number).isEqualTo(1)
    }

    @Test
    fun `플러스 버튼을 누르면 티켓개수가 증가해야한다`() {
        // when
        movieReservationTicketCountData.plusTicketCount()

        // then
        assertThat(movieReservationTicketCountData.ticketCount.number).isEqualTo(2)
    }

    @Test
    fun `마이너스 버튼을 누르면 티켓개수가 감소해야한다`() {
        // when
        movieReservationTicketCountData.plusTicketCount()
        movieReservationTicketCountData.plusTicketCount()
        movieReservationTicketCountData.minusTicketCount()

        // then
        assertThat(movieReservationTicketCountData.ticketCount.number).isEqualTo(2)
    }

    @Test
    fun `티켓 개수가 1일 때 마이너스 버튼을 누르면 예외를 던져야 한다`() {
        // then
        assertThrows<IllegalArgumentException> {
            movieReservationTicketCountData.minusTicketCount()
        }
    }
}
