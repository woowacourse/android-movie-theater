package woowacourse.movie.presenter

import io.mockk.*
import org.junit.Before
import org.junit.Test
import woowacourse.movie.view.reservationresult.ReservationResultContract
import woowacourse.movie.model.TicketsUiModel
import woowacourse.movie.view.reservationresult.ReservationResultPresenter

class ReservationResultPresenterTest {
    private lateinit var view: ReservationResultContract.View
    private lateinit var presenter: ReservationResultContract.Presenter
    private lateinit var ticketsUiModel: TicketsUiModel

    @Before
    fun setUp() {
        view = mockk()
        ticketsUiModel = TicketsUiModel(listOf())
        presenter = ReservationResultPresenter(view, ticketsUiModel)
    }

    @Test
    fun 예매_가격을_표시한다() {
        // given
        val slot = slot<Int>()
        every { view.setPriceTextView(capture(slot)) } just runs
        // when
        presenter.updatePrice()
        // then
        verify { view.setPriceTextView(slot.captured) }
    }
}
