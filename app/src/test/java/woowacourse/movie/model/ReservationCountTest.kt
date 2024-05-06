package woowacourse.movie.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ReservationCountTest {
    @Test
    fun `기본값은 1이다`() {
        assertThat(ReservationCount().count).isEqualTo(1)
    }

    @Test
    fun `plus를 진행하면 1이 늘어난다`() {
        var reservationCount = ReservationCount()

        reservationCount = reservationCount.inc()

        assertThat(reservationCount.count).isEqualTo(2)
    }

    @Test
    fun `minus를 진행하면 1 줄어든다`() {
        var reservationCount = ReservationCount()

        reservationCount = reservationCount.inc()
        reservationCount = reservationCount.inc()
        reservationCount = reservationCount.dec()

        assertThat(reservationCount.count).isEqualTo(2)
    }

    @Test
    fun `minus를 진행해도 최소 예매 인원수의 1 미만으로 내려가지 않는다`() {
        var reservationCount = ReservationCount()

        reservationCount = reservationCount.dec()

        assertThat(reservationCount.count).isEqualTo(1)
    }

    @Test
    fun `plus를 진행해도 최대 예매 인원수의 10 초과로 올라가지 않는다`() {
        var reservationCount = ReservationCount(10)

        reservationCount = reservationCount.inc()

        assertThat(reservationCount.count).isEqualTo(10)
    }
}
