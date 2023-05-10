package woowacourse.movie.presenter

import io.mockk.* // ktlint-disable no-wildcard-imports
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.TicketsUiModel
import woowacourse.movie.view.reservationresult.ReservationResultContract
import woowacourse.movie.view.reservationresult.ReservationResultPresenter

class ReservationResultPresenterTest {
    private lateinit var view: ReservationResultContract.View
    private lateinit var presenter: ReservationResultContract.Presenter
    private lateinit var ticketsUiModel: TicketsUiModel

    @Before
    fun setUp() {
        view = mockk()
        ticketsUiModel = TicketsUiModel(listOf())
        presenter = ReservationResultPresenter(view)
    }

    @Test
    fun 예매_가격을_표시한다() {
        // given
        val slot = slot<Int>()
        every { view.setPriceText(capture(slot)) } just runs
        // when
        presenter.calculateTicketsPrice(ticketsUiModel)
        // then
        verify { view.setPriceText(slot.captured) }
    }
}
