package woowacourse.movie.view

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotSelected
import androidx.test.espresso.matcher.ViewMatchers.isSelected
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.domain.movie.Minute
import woowacourse.movie.domain.movie.Movie
import woowacourse.movie.domain.movie.Schedule
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.ReservationOptions
import woowacourse.movie.view.seatselection.SeatSelectionActivity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
@LargeTest
class SeatSelectionActivityTest {

    private val reservationOptions = ReservationOptions(
        "스즈메의 문단속",
        LocalDateTime.of(LocalDate.of(2024, 3, 1), LocalTime.of(14, 0)),
        2,
        "강남 극장",
    )

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

    private val intent = SeatSelectionActivity.newIntent(
        ApplicationProvider.getApplicationContext(),
        reservationOptions,
        movie.toUiModel(R.drawable.harry_potter1_poster),
    )

    @get:Rule
    val activityRule = ActivityScenarioRule<SeatSelectionActivity>(intent)

    @Test
    fun 영화_제목을_표시한다() {
        onView(withId(R.id.text_title))
            .check(matches(withText("스즈메의 문단속")))
    }

    @Test
    fun 한_번_클릭하면_좌석이_선택된다() {
        onView(withText("A1")).perform(click()).check(matches(isSelected()))
    }

    @Test
    fun 두_번_클릭하면_좌석_선택이_해제된다() {
        onView(withText("A1")).perform(click())
        onView(withText("A1")).perform(click()).check(matches(isNotSelected()))
    }

    @Test
    fun 인원수에_해당하는_좌석이_모두_선택되지_않았다면_확인_버튼은_비활성화_상태다() {
        onView(withText("A1")).perform(click())
        onView(withId(R.id.btn_next))
            .check(matches(isNotEnabled()))
    }

    @Test
    fun 인원수에_해당하는_좌석이_모두_선택되었다면_확인_버튼은_활성화_상태다() {
        onView(withText("A1")).perform(click())
        onView(withText("A2")).perform(click())
        onView(withId(R.id.btn_next))
            .check(matches(isEnabled()))
    }

    @Test
    fun 인원수에_해당하는_좌석이_모두_선택되었다면_최종_금액이_표시된다() {
        onView(withText("A1")).perform(click())
        onView(withText("A2")).perform(click())
        onView(withId(R.id.text_price)).check(matches(withText("20,000원")))
    }

    @Test
    fun 좌석을_선택할_때_가격을_갱신한다() {
        onView(withText("A1")).perform(click())
        onView(withId(R.id.text_price)).check(matches(withText("10,000원")))
        onView(withText("A2")).perform(click())
        onView(withId(R.id.text_price)).check(matches(withText("20,000원")))
    }
}
