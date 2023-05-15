package woowacourse.movie.presentation.view.main.home.seatpick

import io.mockk.mockk
import org.junit.Before
import org.junit.Test


internal class SeatPickerPresenterTest{
    lateinit var presenter: SeatPickerPresenter

    @Before
    fun setUp(){
        val view = mockk<SeatPickerContract.View>()
        presenter = SeatPickerPresenter(view, mockk(relaxed = true), mockk(relaxed = true))
    }
    @Test
    fun `초기화_테스트`() {
        presenter.confirmBooking()
    }

}