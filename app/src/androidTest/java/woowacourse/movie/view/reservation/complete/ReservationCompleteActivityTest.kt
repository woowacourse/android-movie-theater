package woowacourse.movie.view.reservation.complete

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.view.Extras
import woowacourse.movie.view.model.ReservationInfoUiModel
import woowacourse.movie.view.model.SeatUiModel
import woowacourse.movie.view.model.SeatsUiModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ReservationCompleteActivityTest {
    private lateinit var scenario: ActivityScenario<ReservationCompleteActivity>
    private val dummySeats =
        SeatsUiModel(listOf(SeatUiModel("A1", 10000), SeatUiModel("C1", 15000)))
    private val fakeReservationInfoUiModel =
        ReservationInfoUiModel(
            "라라랜드",
            LocalDateTime.of(LocalDate.of(2025, 4, 1), LocalTime.of(14, 0)),
            dummySeats,
            20000,
            "선릉",
        )
    private val fakeContext: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        scenario =
            ActivityScenario.launch(
                Intent(fakeContext, ReservationCompleteActivity::class.java).putExtra(
                    Extras.ReservationInfoData.RESERVATION_KEY,
                    fakeReservationInfoUiModel,
                ),
            )
    }

    @Test
    fun `티켓_취소정책이_화면에_표시된다`() {
        onView(withId(R.id.tv_reservation_complete_information))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `예매한_영화_제목이_화면에_표시된다`() {
        onView(withId(R.id.tv_reservation_complete_title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `예매한_영화의_날짜와_시간이_화면에_표시된다`() {
        onView(withId(R.id.tv_reservation_complete_timestamp))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `예매한_영화의_좌석과_극장_정보가_화면에_표시된다`() {
        onView(withId(R.id.tv_reservation_complete_count_seats))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `예매한_티켓_총_가격이_화면에_표시된다`() {
        onView(withId(R.id.tv_reservation_complete_ticket_price))
            .check(matches(isDisplayed()))
    }
}
