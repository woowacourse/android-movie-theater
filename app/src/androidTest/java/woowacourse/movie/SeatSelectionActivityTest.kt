package woowacourse.movie

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import woowacourse.movie.booking.detail.BookingDetailActivity
import woowacourse.movie.fixture.HARRY_POTTER
import woowacourse.movie.fixture.SEOLLEUNG
import woowacourse.movie.fixture.createTicket
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.seat.SeatSelectionActivity

class SeatSelectionActivityTest {
    private lateinit var scenario: ActivityScenario<BookingDetailActivity>

    @Before
    fun setUp() {
        Intents.init()

        val ticket = createTicket(SEOLLEUNG, listOf(), 2).toUiModel()
        val intent =
            Intent(
                ApplicationProvider.getApplicationContext(),
                SeatSelectionActivity::class.java,
            ).apply {
                putExtra("TICKET_DATA", ticket)
            }

        scenario = ActivityScenario.launch(intent)
    }

    @After
    fun tearDown() {
        Intents.release()
        scenario.close()
    }

    @Test
    fun `Ticket_데이터를_바탕으로_화면에_티켓의_기본_정보가_보인다`() {
        onView(withId(R.id.tv_seat_movie_title)).check(
            matches(
                allOf(
                    withText(HARRY_POTTER),
                    isDisplayed(),
                ),
            ),
        )
    }

    @Test
    fun `화면에_좌석_테이블레이아웃이_보인다`() {
        onView(withId(R.id.seat_table))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `좌석_A1_C1이_화면에_보인다`() {
        onView(withText("A1"))
            .check(matches(isDisplayed()))
        onView(withText("C1"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `화면의_좌석을_선택하면_가격이_오른다`() {
        onView(withId(R.id.tv_seat_amount))
            .check(
                matches(
                    allOf(
                        withText("0원"),
                        isDisplayed(),
                    ),
                ),
            )

        onView(withText("A1"))
            .perform(click())

        onView(withId(R.id.tv_seat_amount))
            .check(
                matches(
                    allOf(
                        withText("10,000원"),
                        isDisplayed(),
                    ),
                ),
            )
    }

    @Test
    fun `화면의_좌석을_선택하면_좌석의_배경색이_변경된다`() {
        onView(withText("A1"))
            .perform(click())

        onView(withText("A1")).check { view, _ ->
            val background = view.background

            val color = (background as ColorDrawable).color
            val expectedColor = ContextCompat.getColor(view.context, R.color.seat_selected_background)
            assertEquals(expectedColor, color)
        }
    }

    @Test
    fun `예매_인원보다_적은_인원의_좌석을_선택하면_버튼이_활성화되지_않는다`() {
        onView(withText("A1"))
            .perform(click())

        onView(withText("확인")).check { view, _ ->
            val background = view.background

            val color = (background as ColorDrawable).color
            val expectedColor = ContextCompat.getColor(view.context, R.color.btn_deactivate_background)
            assertEquals(expectedColor, color)
        }
    }

    @Test
    fun `예매_인원에_맞는_좌석들을_선택하면_버튼이_활성화된다`() {
        onView(withText("A1"))
            .perform(click())

        onView(withText("C1"))
            .perform(click())

        onView(withText("확인")).check { view, _ ->
            val background = view.background

            val color = (background as ColorDrawable).color
            val expectedColor = ContextCompat.getColor(view.context, R.color.btn_activate_background)
            assertEquals(expectedColor, color)
        }
    }

    @Test
    fun `활성화된_버튼을_클릭하면_예매관련_다이알로그가_뜬다`() {
        onView(withText("A1"))
            .perform(click())

        onView(withText("C1"))
            .perform(click())

        onView(withId(R.id.btn_booking_confirm))
            .perform(click())

        onView(withText("예매 확인"))
            .check(matches(isDisplayed()))

        onView(withText("정말 예매하시겠습니까?"))
            .check(matches(isDisplayed()))

        onView(withText("예매 완료"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `다이알로그에서_취소를_누르면_화면이_닫힌다`() {
        onView(withText("A1"))
            .perform(click())

        onView(withText("C1"))
            .perform(click())

        onView(withId(R.id.btn_booking_confirm))
            .perform(click())

        onView(withText("예매 확인"))
            .check(matches(isDisplayed()))

        onView(withText("정말 예매하시겠습니까?"))
            .check(matches(isDisplayed()))

        onView(withText("취소"))
            .perform(click())
    }
}
