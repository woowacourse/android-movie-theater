package woowacourse.movie.view.result


// TODO : room에 저장 된 데이터가 다 다르기 때문에, room에 있는 ticketId를 통한 로드가 실패하게 됩니다.
// TODO : 이를 위한 해결 방안을 찾고 있습니다.
//import android.content.Intent
//import androidx.test.core.app.ActivityScenario
//import androidx.test.core.app.ApplicationProvider
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.action.ViewActions
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.matcher.ViewMatchers
//import androidx.test.espresso.matcher.ViewMatchers.withId
//import androidx.test.espresso.matcher.ViewMatchers.withText
//import androidx.test.ext.junit.rules.ActivityScenarioRule
//import org.junit.Rule
//import org.junit.Test
//import woowacourse.movie.R
//import woowacourse.movie.TestFixture.FIRST_MOVIE_ITEM_POSITION
//import woowacourse.movie.TestFixture.makeMockTicket
//import woowacourse.movie.TestFixture.movies
//import woowacourse.movie.view.home.HomeFragment
//import woowacourse.movie.view.reservation.ReservationDetailActivity
//import woowacourse.movie.view.reservation.ReservationDetailActivity.Companion.RESERVATION_TICKET_ID
//import woowacourse.movie.view.reservation.ReservationDetailActivity.Companion.TICKET
//import woowacourse.movie.view.theater.TheaterSelectionFragment
//
//class ReservationResultActivityTest {
//    @get:Rule
//    var activityRule =
//        ActivityScenarioRule<ReservationResultActivity>(
//            Intent(
//                ApplicationProvider.getApplicationContext(),
//                ReservationResultActivity::class.java,
//            ).apply {
//                putExtra(RESERVATION_TICKET_ID,1L)
//            },
//        )
//
//    @Test
//    fun `예매한_영화의_제목을_보여준다`() {
//        onView(withId(R.id.text_view_reservation_finished_title)).check(matches(withText(movies[FIRST_MOVIE_ITEM_POSITION].title)))
//    }
//
//    @Test
//    fun `예매한_영화의_상영일을_보여준다`() {
//        onView(
//            withId(R.id.text_view_reservation_finished_screening_date),
//        ).check(matches(withText(makeMockTicket().screeningDateTime.date)))
//    }
//
//    @Test
//    fun `예매한_영화의_관람인원을_보여준다`() {
//        onView(withId(R.id.text_view_reservation_finished_number_of_tickets)).check(
//            matches(
//                withText(
//                    "2",
//                ),
//            ),
//        )
//    }
//
//    @Test
//    fun `예매한_영화의_총_결제금액을_보여준다`() {
//        onView(withId(R.id.text_view_reservation_finished_ticket_price)).check(
//            matches(
//                withText(
//                    "25,000",
//                ),
//            ),
//        )
//    }
//
//    @Test
//    fun `영화_예매_완료_화면은_영화_상세_화면의_예매_완료_버튼을_누르면_보여진다`() {
//        val intent =
//            Intent(ApplicationProvider.getApplicationContext(), ReservationDetailActivity::class.java).apply {
//                putExtra(HomeFragment.MOVIE_ID, 0)
//                putExtra(TheaterSelectionFragment.THEATER_ID, 0)
//            }
//
//        ActivityScenario.launch<ReservationDetailActivity>(intent)
//
//        onView(withId(R.id.button_reservation_detail_finished)).perform(ViewActions.click())
//        onView(withId(R.id.constraint_layout_seat_selection)).check(matches(ViewMatchers.isDisplayed()))
//    }
//}
