package woowacourse.movie.domain.reservationNotificationPolicy

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class MovieReservationNotificationTest {
    @Test
    fun `영화 시작 30분전에 알람을 설정한다`() {
        // given
        val movieDateTime = LocalDateTime.of(2023, 4, 26, 16, 31)

        // when
        val policy = MovieReservationNotification
        val actual = policy.calculateTime(movieDateTime)

        // then
        val expect = LocalDateTime.of(2023, 4, 26, 16, 1)
        assertEquals(actual, expect)
    }
}
