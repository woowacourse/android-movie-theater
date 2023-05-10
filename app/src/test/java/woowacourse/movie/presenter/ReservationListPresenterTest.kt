package woowacourse.movie.presenter

import domain.Reservation
import io.mockk.* // ktlint-disable no-wildcard-imports
import org.junit.Before
import org.junit.Test
import woowacourse.movie.database.ReservationDbHelperInterface
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.model.mapper.ReservationMapper.toUi
import woowacourse.movie.view.main.reservationlist.ReservationListContract
import woowacourse.movie.view.main.reservationlist.ReservationListPresenter

class ReservationListPresenterTest {
    private lateinit var presenter: ReservationListPresenter
    private lateinit var view: ReservationListContract.View
    private lateinit var reservationUiModel: ReservationUiModel
    private lateinit var reservationDb: ReservationDbHelperInterface

    @Before
    fun setUp() {
        view = mockk()
        reservationUiModel = mockk()
        reservationDb = object : ReservationDbHelperInterface {
            override fun saveReservation(reservation: Reservation) = Unit
            override fun getReservations(): List<Reservation> {
                return listOf()
            }
        }
        presenter = ReservationListPresenter(view, reservationDb)
    }

    @Test
    fun 예매목록을_클릭하면_예매결과_화면으로_넘어간다() {
        // given
        every {
            view.showReservationResult(
                reservationUiModel.movie,
                reservationUiModel.tickets,
            )
        } just runs
        // when
        presenter.showReservationResult(reservationUiModel)
        // then
        verify {
            view.showReservationResult(
                reservationUiModel.movie,
                reservationUiModel.tickets,
            )
        }
    }

    @Test
    fun 저장되있는_예매목록을_화면에_띄운다() {
        // given
        val reservations = reservationDb.getReservations()
        val reservationUiModels = reservations.map { it.toUi() }
        every { view.setReservationList(reservationUiModels) } just runs
        // when
        presenter.updateReservationList()
        // then
        verify { view.setReservationList(reservationUiModels) }
    }
}
