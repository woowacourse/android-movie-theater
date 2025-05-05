package woowacourse.movie.view.reservation.result

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.domain.model.Cinema
import woowacourse.movie.view.fixture.TestData
import woowacourse.movie.view.matchers.isEllipsized

@RunWith(AndroidJUnit4::class)
@Suppress("FunctionName")
class ReservationResultActivityTest {
    val intent =
        ReservationResultActivity.newIntent(
            ApplicationProvider.getApplicationContext(),
            TestData.reservationInfo,
        )

    @get:Rule
    val activityRule = ActivityScenarioRule<ReservationResultActivity>(intent)

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
            .check(matches(withText("2025.5.1 09:00")))
    }

    @Test
    fun `예매한_영화의_예매_인원_수를_보여준다`() {
        onView(withId(R.id.tv_reservation_count_info))
            .check(matches(withText("일반 2명")))
    }

    @Test
    fun `예매한_영화의_인원수에_맞는_총_티켓_가격을_보여준다`() {
        onView(withId(R.id.tv_reservation_total_price))
            .check(matches(withText("25,000원 (현장 결제)")))
    }

    @Test
    fun `예매한_영화의_극장을_보여준다`() {
        onView(withId(R.id.tv_reservation_cinema))
            .check(matches(withText("잠실 극장")))
    }

    @Test
    fun `예매한_영화의_극장이_너무_길_경우_말줄침표로_표시한다`() {
        activityRule.scenario.onActivity {
            it.showReservationResult(
                TestData.reservationInfo.copy(
                    cinema = Cinema(1, "잠실잠실잠실잠실잠실잠실잠실잠실잠실잠실잠실잠실잠실잠실잠실잠실잠실잠실잠실잠실잠실잠실잠실"),
                ),
            )
        }
        onView(withId(R.id.tv_reservation_cinema))
            .check(isEllipsized())
    }
}
