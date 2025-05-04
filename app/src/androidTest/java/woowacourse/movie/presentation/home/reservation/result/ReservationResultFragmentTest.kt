package woowacourse.movie.presentation.home.reservation.result

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.presentation.common.model.SeatTypeUiModel
import woowacourse.movie.presentation.common.model.SeatUiModel
import woowacourse.movie.presentation.common.model.TicketUiModel
import java.time.LocalDateTime

class ReservationResultFragmentTest {
    private val ticket =
        TicketUiModel(
            "해리 포터와 마법사의 돌",
            "선릉 극장",
            LocalDateTime.of(2025, 4, 15, 11, 0),
            listOf(
                SeatUiModel(0, 1, SeatTypeUiModel.B_CLASS),
                SeatUiModel(0, 2, SeatTypeUiModel.B_CLASS),
            ),
            2,
            20_000,
        )

    @Before
    fun setUp() {
        launchFragmentInContainer(bundleOf("ticket" to ticket)) {
            ReservationResultFragment()
        }
    }

    @Test
    fun `예매_취소_가능_시간을_보여준다`() {
        onView(withId(R.id.tv_cancel_description))
            .check(matches(withText("영화 상영 시작 시간 15분 전까지\n취소가 가능합니다.")))
    }

    @Test
    fun `예매한_영화의_제목을_보여준다`() {
        onView(withId(R.id.tv_movie_title))
            .check(matches(withText("해리 포터와 마법사의 돌")))
    }

    @Test
    fun `예매한_영화의_상영일을_보여준다`() {
        onView(withId(R.id.tv_movie_date))
            .check(matches(withText("2025.4.15 11:00")))
    }

    @Test
    fun `예매한_영화_예매_인원_수와_좌석_번호와_극장_이름을_보여준다`() {
        onView(withId(R.id.tv_reservation_count_info))
            .check(matches(withText("일반 2명 | A1, A2 | 선릉 극장")))
    }

    @Test
    fun `예매한_영화의_인원수에_맞는_총_티켓_가격을_보여준다`() {
        onView(withId(R.id.tv_reservation_total_price))
            .check(matches(withText("20,000원 (현장 결제)")))
    }
}
