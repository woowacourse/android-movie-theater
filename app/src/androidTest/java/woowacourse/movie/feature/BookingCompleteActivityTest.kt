package woowacourse.movie.feature

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.feature.bookingcomplete.view.BookingCompleteActivity
import woowacourse.movie.feature.bookingcomplete.view.BookingCompleteActivity.Companion.newIntent
import woowacourse.movie.feature.model.BookingInfoUiModel
import woowacourse.movie.feature.model.MovieDateUiModel
import woowacourse.movie.feature.model.MovieSeatUiModel
import woowacourse.movie.feature.model.MovieTimeUiModel
import woowacourse.movie.feature.model.MovieUiModel
import woowacourse.movie.feature.model.SeatTypeUiModel

@Suppress("ktlint:standard:function-naming")
class BookingCompleteActivityTest {
    private lateinit var activityScenario: ActivityScenario<BookingCompleteActivity>

    @Before
    fun setup() {
        val intent =
            newIntent(
                context = getApplicationContext(),
                bookingInfo =
                    BookingInfoUiModel(
                        movie =
                            MovieUiModel(
                                title = "해리 포터와 마법사의 돌",
                                startDate = MovieDateUiModel(2025, 4, 1),
                                endDate = MovieDateUiModel(2025, 4, 25),
                                runningTime = 152,
                            ),
                        theaterName = "혜화",
                        date = MovieDateUiModel(2025, 4, 1),
                        movieTime = MovieTimeUiModel(9, 0),
                        selectedSeats =
                            setOf<MovieSeatUiModel>(
                                MovieSeatUiModel(1, 1, SeatTypeUiModel.RANK_B),
                                MovieSeatUiModel(2, 2, SeatTypeUiModel.RANK_B),
                            ),
                        ticketCount = 2,
                        totalPrice = 20_000,
                        isSeatAllSelected = true,
                    ),
            )

        activityScenario = ActivityScenario.launch(intent)
    }

    @Test
    fun 선택된_정보에_따라_금액을_출력한다() {
        onView(withId(R.id.tv_booking_complete_ticket_total_price))
            .check(matches(withText("20,000원 (현장 결제)")))
    }

    @Test
    fun 선택된_좌석_정보가_출력된다() {
        onView(withId(R.id.tv_booking_complete_count_seat_theater))
            .check(matches(withText("일반 2명ㅣA1, B2ㅣ혜화 극장")))
    }
}
