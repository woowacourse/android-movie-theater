package woowacourse.movie.presenter

import io.mockk.*
import org.junit.Before
import org.junit.Test
import woowacourse.movie.contract.ReservationListContract
import woowacourse.movie.model.ReservationUiModel

class ReservationListPresenterTest {
    private lateinit var presenter: ReservationListPresenter
    private lateinit var view: ReservationListContract.View
    private lateinit var reservationUiModel: ReservationUiModel

    @Before
    fun setUp() {
        view = mockk()
        reservationUiModel = mockk()
        presenter = ReservationListPresenter(view)
    }

    @Test
    fun 예매목록을_클릭하면_예매결과_화면으로_넘어간다() {
        // given
        every {
            view.startReservationResultActivity(
                reservationUiModel.movie,
                reservationUiModel.tickets
            )
        } just runs
        // when
        presenter.reservationItemClick(reservationUiModel)
        // then
        verify {
            view.startReservationResultActivity(
                reservationUiModel.movie,
                reservationUiModel.tickets
            )
        }
    }

    @Test
    fun 예매목들을_만들고_예매목록을_화면에_띄운다() {
        // given
        val slot = slot<List<ReservationUiModel>>()
        every { view.setAdapter(capture(slot)) } answers { println("slot = ${slot.captured}") }
        // when
        presenter.updateReservationList()
        // then
        val actual = slot.captured
        verify { view.setAdapter(actual) }
    }
}
