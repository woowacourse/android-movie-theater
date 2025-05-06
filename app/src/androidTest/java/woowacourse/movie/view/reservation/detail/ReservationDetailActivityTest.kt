package woowacourse.movie.view.reservation.detail

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.view.Extras
import woowacourse.movie.view.model.MovieDateUiModel
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.model.TheaterUiModel
import java.time.LocalDate

class ReservationDetailActivityTest {
    private lateinit var scenario: ActivityScenario<ReservationDetailActivity>
    private val fakeMovie: MovieUiModel =
        MovieUiModel(
            "라라랜드",
            R.drawable.lalaland,
            MovieDateUiModel(
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 30),
            ),
            120,
        )
    private val fakeTheater: TheaterUiModel =
        TheaterUiModel("선릉", 20)

    private val fakeContext: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        scenario =
            ActivityScenario.launch(
                Intent(
                    fakeContext,
                    ReservationDetailActivity::class.java,
                ).apply {
                    putExtra(Extras.MovieData.MOVIE_KEY, fakeMovie)
                    putExtra(
                        Extras.TheaterData.THEATER_UI_MODEL_KEY,
                        fakeTheater,
                    )
                },
            )
    }

    @Test
    fun 영화_티켓_개수_플러스_버튼을_누르면_티켓_개수가_1_증가한다() {
        // given: 영화 개수 초기값은 1이고
        // when: 사용자가 플러스 버튼을 누르면
        onView(withId(R.id.btn_reservation_plus_ticket_count))
            .perform(click())

        // then: 화면의 영화 개수 값은 2가 된다
        onView(withId(R.id.tv_reservation_ticket_count))
            .check(matches(withText("2")))
    }

    @Test
    fun 영화_티켓_개수_마이너스_버튼을_누르면_티켓_개수가_1_감소한다() {
        // given: 선택한 영화 개수가 2이고
        onView(withId(R.id.btn_reservation_plus_ticket_count))
            .perform(click())

        // when: 사용자가 마이너스 버튼을 누르면
        onView(withId(R.id.btn_reservation_minus_ticket_count))
            .perform(click())

        // then: 화면의 영화 개수 값은 1이 된다
        onView(withId(R.id.tv_reservation_ticket_count))
            .check(matches(withText("1")))
    }

    @Test
    fun `화면을_회전해도_티켓_개수가_유지된다`() {
        // when: 티켓 개수를 2 증가 시키고 가로모드로 회전했을 때
        onView(withId(R.id.btn_reservation_plus_ticket_count))
            .perform(click())
            .perform(click())

        scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        // then: 티켓 개수 3이 유지된다
        onView(withId(R.id.tv_reservation_ticket_count))
            .check(matches(withText("3")))
    }
}
