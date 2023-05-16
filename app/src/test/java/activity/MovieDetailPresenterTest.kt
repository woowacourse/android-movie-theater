package activity

import com.woowacourse.domain.ScreeningSchedule
import com.woowacourse.domain.TheaterMovie
import com.woowacourse.domain.movie.Movie
import com.woowacourse.domain.movie.MovieBookingInfo
import com.woowacourse.domain.movie.MovieSchedule
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import woowacourse.movie.DateFormatter
import woowacourse.movie.activity.moviedetail.MovieDetailContract
import woowacourse.movie.activity.moviedetail.MovieDetailPresenter
import woowacourse.movie.model.MovieUIModel
import java.time.LocalDate
import java.time.LocalTime

class MovieDetailPresenterTest {

    private lateinit var presenter: MovieDetailPresenter
    private lateinit var view: MovieDetailContract.View

    @Before
    fun setUp() {
        view = mockk()
        presenter = MovieDetailPresenter(view)
    }

    @Test
    fun 영화상영날짜를_만든다() {
        // given
        val movieData =
            Movie(0, "title", 200, "synopsis", LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 5))
        val screeningSchedule = ScreeningSchedule(movieData, listOf(LocalTime.of(9, 0)))
        val slot = slot<MovieSchedule>()
        every { view.setScheduleDate(capture(slot)) } answers { nothing }

        // when: (request Data)
        presenter.loadScheduleDate(screeningSchedule)

        // then: 주어진 시작 날짜와 끝 날짜에 맞는 MovieSchedule 반환
        val actual = slot.captured
        val expect = MovieSchedule(screeningSchedule)
        Assert.assertEquals(expect, actual)
    }

    @Test
    fun 주어진_Movie_데이터로_화면을_초기화한다() {
        // given
        val movieData =
            Movie(0, "title", 200, "synopsis", LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 5))
        val slot = slot<MovieUIModel>()
        every { view.setUpMovieDetailView(capture(slot)) } answers { nothing }

        // when: request Data
        presenter.loadMovieData(movieData)

        // then: movieData에 맞게 view를 초기화한다
        val actual = slot.captured
        Assert.assertEquals(movieData, actual)
    }

    @Test
    fun 현재_인원수가_1인_경우_인원수를_더하면_인원수는_2가_된다() {
        // given
        val slot = slot<Int>()
        every { view.setCountText(capture(slot)) } answers { nothing }

        // when
        presenter.addPeople()

        // then
        val actual = slot.captured
        Assert.assertEquals(2, actual)
    }

    @Test
    fun 현재_인원수가_2인_경우_인원수를_뻬면_인원수가_1이_된다() {
        // given
        val slot = slot<Int>()
        every { view.setCountText(capture(slot)) } answers { nothing }
        presenter.addPeople()

        // when
        presenter.subPeople()

        // then
        val actual = slot.captured
        Assert.assertEquals(1, actual)
    }

    @Test
    fun 현재_인원수가_1인_경우_인원수를_뻬면_메세지가_전달된다() {
        // given
        val slot = slot<String>()
        every { view.showGuideMessage(capture(slot)) } answers { nothing }

        // when
        presenter.subPeople()

        // then
        val actual = slot.captured
        Assert.assertEquals("1장 이상의 표를 선택해 주세요.", actual)
    }

    @Test
    fun 만들어진_MovieBookingInfo으로_intent를_한다() {
        // given
        val movieData =
            Movie(0, "title", 200, "synopsis", LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 5))
        val selectDate = LocalDate.of(2023, 5, 2)
        val selectTime = "09:00"
        val theaterMovie =
            TheaterMovie("theater", ScreeningSchedule(movieData, listOf(LocalTime.of(9, 0))))
        val expect = MovieBookingInfo(theaterMovie, DateFormatter.format(selectDate), selectTime, 1)

        val slot = slot<MovieBookingInfo>()
        every { view.setIntent(capture(slot)) } answers { nothing }

        // when
        presenter.loadMovieBookingInfo(theaterMovie, selectDate, selectTime)

        // then
        val actual = slot.captured
        Assert.assertEquals(expect, actual)
    }
}
