package woowacourse.movie.feature.detail

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.feature.firstMovieId
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.KEY_SELECTED_THEATER_INDEX

@RunWith(AndroidJUnit4::class)
class MovieDetailActivityTest {
    private val intent =
        Intent(
            ApplicationProvider.getApplicationContext(),
            MovieDetailActivity::class.java,
        )
            .putExtra(KEY_MOVIE_ID, firstMovieId)
            .putExtra(KEY_SELECTED_THEATER_INDEX, 0)

    @get:Rule
    val activityRule = ActivityScenarioRule<MovieDetailActivity>(intent)

    @Test
    fun `선택된_영화의_제목이_표시된다`() {
        onView(withId(R.id.tv_title))
            .check(matches(withText("타이타닉 0")))
    }

    @Test
    fun `선택된_영화의_상영_기간이_표시된다`() {
        onView(withId(R.id.tv_screening_date_range))
            .check(matches(withText("상영일: 2024.4.1 ~ 2024.4.28")))
    }

    @Test
    fun `선택된_영화의_러닝타임이_표시된다`() {
        onView(withId(R.id.tv_running_time))
            .check(matches(withText("러닝타임: 152분")))
    }

    @Test
    fun `선택된_영화의_설명이_표시된다`() {
        onView(withId(R.id.tv_description))
            .check(
                matches(
                    withText(
                        "우연한 기회로 티켓을 구해 타이타닉호에 올라탄 자유로운 영혼을 가진 화가 ‘잭’(레오나르도 디카프리오)은 막강한 재력의 약혼자와 함께 1등실에 승선한 ‘로즈’(케이트 윈슬렛)에게 한눈에 반한다. " +
                            "진실한 사랑을 꿈꾸던 ‘로즈’ 또한 생애 처음 황홀한 감정에 휩싸이고, 둘은 운명 같은 사랑에 빠지는데… 가장 차가운 곳에서 피어난 뜨거운 사랑! 영원히 가라앉지 않는 세기의 사랑이 펼쳐진다!",
                    ),
                ),
            )
    }

    @Test
    fun `선택된_영화의_예매_인원수의_초기값이_표시된다`() {
        onView(withId(R.id.tv_reservation_count))
            .check(matches(withText("1")))
    }

    @Test
    fun `화면이_가로로_회전되어도_예매_인원수의_값이_유지된다`() {
        onView(withId(R.id.btn_plus)).perform(click())

        val activityScenario = activityRule.scenario
        activityScenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(withId(R.id.tv_reservation_count)).check(matches(withText("2")))
    }
}
