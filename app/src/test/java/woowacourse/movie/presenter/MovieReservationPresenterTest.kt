package woowacourse.movie.presenter

import io.mockk.*
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.view.moviereservation.MovieReservationContract
import woowacourse.movie.model.TheaterUiModel
import woowacourse.movie.view.moviereservation.MovieReservationPresenter
import java.time.LocalDate
import java.time.LocalDateTime

class MovieReservationPresenterTest {
    private lateinit var presenter: MovieReservationContract.Presenter
    private lateinit var view: MovieReservationContract.View
    private lateinit var theaterUiModel: TheaterUiModel

    @Before
    fun setUp() {
        view = mockk()
        theaterUiModel = mockk()
        presenter = MovieReservationPresenter(view, 1)
    }

    @Test
    fun 날짜_Spinner를_업데이트한다() {
        // given
        every { theaterUiModel.name } returns "선릉"
        every { theaterUiModel.screenTimes } returns listOf(LocalDateTime.now(), LocalDateTime.MAX)
        every { view.setDateSpinner(any()) } just runs
        // when
        presenter.updateDateSpinner(theaterUiModel)
        // then
        verify { view.setDateSpinner(any()) }
    }

    @Test
    fun 날짜를_선택했을때_해당되는_시간들을_생성하고_Spinner에_배치한다() {
        // given
        val date = LocalDate.of(2023, 5, 6)
        every { theaterUiModel.name } returns "선릉"
        every { theaterUiModel.screenTimes } returns listOf(LocalDateTime.now(), LocalDateTime.MAX)
        every { view.setTimeSpinner(any()) } just runs
        // when
        presenter.onSelectDate(theaterUiModel, date)
        // then
        verify { view.setTimeSpinner(any()) }
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
