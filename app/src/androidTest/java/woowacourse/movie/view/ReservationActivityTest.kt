package woowacourse.movie.view

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
import woowacourse.movie.domain.movie.Minute
import woowacourse.movie.domain.movie.Movie
import woowacourse.movie.domain.movie.Schedule
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.reservation.ReservationActivity
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class ReservationActivityTest {

    private val movie = Movie(
        "스즈메의 문단속",
        LocalDate.of(2024, 3, 1),
        LocalDate.of(2024, 3, 31),
        Minute(122),
        "“이 근처에 폐허 없니? 문을 찾고 있어” 규슈의 한적한 마을에 살고 있는 소녀 ‘스즈메’는 문을 찾아 여행 중인 청년 ‘소타’를 만난다. " +
            "그의 뒤를 쫓아 산속 폐허에서 발견한 낡은 문. ‘스즈메’가 무언가에 이끌리듯 문을 열자 마을에 재난의 위기가 닥쳐오고 가문 대대로 문 너머의 재난을 봉인하는 ‘소타’를 도와 간신히 문을 닫는다. " +
            "“닫아야만 하잖아요, 여기를!” 재난을 막았다는 안도감도 잠시, 수수께끼의 고양이 ‘다이진’이 나타나 ‘소타’를 의자로 바꿔 버리고 일본 각지의 폐허에 재난을 부르는 문이 열리기 시작하자 ‘스즈메’는 의자가 된 ‘소타’와 함께 재난을 막기 위한 여정에 나선다." +
            "“꿈이 아니었어” 규슈, 시코쿠, 고베, 도쿄 재난을 막기 위해 일본 전역을 돌며 필사적으로 문을 닫아가던 중 어릴 적 고향에 닿은 ‘스즈메’는 잊고 있던 진실과 마주하게 되는데…",
        Schedule(
            mapOf(
                "정말아주아주아주아주아주아주아주긴극장이름" to listOf(LocalTime.of(13, 0), LocalTime.of(15, 0), LocalTime.of(17, 0), LocalTime.of(19, 0)),
                "선릉 극장" to listOf(LocalTime.of(13, 0), LocalTime.of(15, 0), LocalTime.of(17, 0), LocalTime.of(19, 0)),
                "잠실 극장" to listOf(LocalTime.of(9, 0)),
                "강남 극장" to listOf(LocalTime.of(12, 0), LocalTime.of(14, 0), LocalTime.of(16, 0)),
            ),
        ),
    )

    private val intent = ReservationActivity.newIntent(
        ApplicationProvider.getApplicationContext(),
        movie.toUiModel(R.drawable.harry_potter1_poster),
        "강남 극장",
    )

    @get:Rule
    val activityRule = ActivityScenarioRule<ReservationActivity>(intent)

    @Test
    fun 영화_제목을_표시한다() {
        onView(withId(R.id.movie_title)).check(matches(withText("스즈메의 문단속")))
    }

    @Test
    fun 처음_표시되는_인원수는_1이다() {
        onView(withId(R.id.people_count)).check(matches(withText("1")))
    }

    @Test
    fun 플러스_버튼을_한_번_클릭하면_인원수는_2이다() {
        onView(withId(R.id.plus_button)).perform(click())
        onView(withId(R.id.people_count)).check(matches(withText("2")))
    }

    @Test
    fun 초기_인원_1인_경우_마이너스_버튼을_눌러도_인원수는_1이다() {
        onView(withId(R.id.minus_button)).perform(click())
        onView(withId(R.id.people_count)).check(matches(withText("1")))
    }
}
