package woowacourse.movie.presenter

import io.mockk.*
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.contract.MovieReservationContract
import java.time.LocalDate
import java.time.LocalTime

class MovieReservationPresenterTest {
    private lateinit var presenter: MovieReservationContract.Presenter
    private lateinit var view: MovieReservationContract.View

    @Before
    fun setUp() {
        view = mockk()
        presenter = MovieReservationPresenter(view, 1)
    }

    @Test
    fun 날짜를_선택했을때_해당되는_시간들을_생성하고_Spinner에_배치한다() {
        // given
        val slot = slot<List<LocalTime>>()
        val date = LocalDate.of(2023, 5, 6)
        every { view.setTimeSpinner(capture(slot)) } just runs
        // when
        presenter.onSelectDate(date)
        // then
        assertEquals(slot.captured.first(), LocalTime.of(9, 0))
        assertEquals(slot.captured.last(), LocalTime.of(23, 0))
        verify { view.setTimeSpinner(slot.captured) }
    }

    @Test
    fun 더하기_버튼을_눌렀을때_Counter_텍스트가_1_증가한다() {
        // given
        val slot = slot<Int>()
        every { view.setCounterText(capture(slot)) } just runs
        // when
        presenter.onPlusTicketCount()
        // then
        val actual = 2
        assertEquals(slot.captured, actual)
        verify { view.setCounterText(actual) }
    }

    @Test
    fun 빼기_버튼을_눌렀을떼_Counter_텍스트가_1_감소한다() {
        // given
        val slot = slot<Int>()
        every { view.setCounterText(capture(slot)) } just runs
        // when
        presenter.onMinusTicketCount()
        // then
        val actual = 1
        assertEquals(slot.captured, actual)
        verify { view.setCounterText(actual) }
    }

    @Test
    fun 예매완료_버튼을_눌렀을때_좌석선택_화면으로_넘어간다() {
        // given
        val slot = slot<Int>()
        every { view.startSeatSelectActivity(capture(slot)) } just runs
        // when
        presenter.onReservationButtonClick()
        // then
        val actual = 1
        assertEquals(slot.captured, actual)
        verify { view.startSeatSelectActivity(actual) }
    }
}
