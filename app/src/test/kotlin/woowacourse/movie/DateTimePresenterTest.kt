package woowacourse.movie

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.ui.booking.DateTimeContract
import woowacourse.movie.ui.booking.DateTimePresenter
import java.time.LocalDate
import java.time.LocalTime

class DateTimePresenterTest {

    private lateinit var dateTimePresenter: DateTimeContract.Presenter
    private lateinit var view: DateTimeContract.View

    @Before
    fun setUp() {
        view = mockk()
        ignoreListener(view)
    }

    @Test
    fun `상영 기간이 4월 26일부터 4월 30일인 경우 date 정보를 나타내는 spinner에 그 사이 기간들을 넣는다`() {
        //given
        val slot = slot<List<LocalDate>>()

        every { view.setDates(capture(slot)) } answers { println(slot.captured) }
        ignoreTimes(view)

        dateTimePresenter = DateTimePresenter(
            startDate = LocalDate.of(2023, 4, 26),
            endDate = LocalDate.of(2023, 4, 30),
            view = view,
        )

        //when
        dateTimePresenter.initDateTimes()

        //then
        val actual = slot.captured
        val expected = listOf(
            LocalDate.of(2023, 4, 26),
            LocalDate.of(2023, 4, 27),
            LocalDate.of(2023, 4, 28),
            LocalDate.of(2023, 4, 29),
            LocalDate.of(2023, 4, 30)
        )

        assertEquals(actual, expected)
        verify { view.setDates(actual) }
    }

    @Test
    fun `상영 기간의 시작 날짜가 평일이라면 time 정보를 나타내는 spinner는 평일 기준으로 시간이 초기화된다`() {
        //given
        val slot = slot<List<LocalTime>>()

        every { view.setTimes(capture(slot)) } answers { println(slot.captured) }
        ignoreDates(view)

        dateTimePresenter = DateTimePresenter(
            startDate = LocalDate.of(2023, 4, 26),
            endDate = LocalDate.of(2023, 4, 30),
            view = view,
        )

        //when
        dateTimePresenter.initDateTimes()

        //then
        val actual = slot.captured
        val expected = listOf(
            LocalTime.of(10, 0),
            LocalTime.of(12, 0),
            LocalTime.of(14, 0),
            LocalTime.of(16, 0),
            LocalTime.of(18, 0),
            LocalTime.of(20, 0),
            LocalTime.of(22, 0),
        )

        assertEquals(actual, expected)
        verify { view.setTimes(actual) }
    }

    @Test
    fun `상영 기간의 시작 날짜가 주말이라면 time 정보를 나타내는 spinner는 주말말 기준로 시간이 초기화된다`() {
        //given
        val slot = slot<List<LocalTime>>()

        every { view.setTimes(capture(slot)) } answers { println(slot.captured) }
        ignoreDates(view)

        dateTimePresenter = DateTimePresenter(
            startDate = LocalDate.of(2023, 4, 23),
            endDate = LocalDate.of(2023, 4, 30),
            view = view,
        )

        //when
        dateTimePresenter.initDateTimes()

        //then
        val actual = slot.captured
        val expected = listOf(
            LocalTime.of(9, 0),
            LocalTime.of(11, 0),
            LocalTime.of(13, 0),
            LocalTime.of(15, 0),
            LocalTime.of(17, 0),
            LocalTime.of(19, 0),
            LocalTime.of(21, 0),
            LocalTime.of(23, 0),
        )

        assertEquals(actual, expected)
        verify { view.setTimes(actual) }
    }
}