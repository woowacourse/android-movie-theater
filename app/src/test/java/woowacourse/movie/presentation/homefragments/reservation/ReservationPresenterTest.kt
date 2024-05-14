package woowacourse.movie.presentation.homefragments.reservation

import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.db.ReservationDatabase
import woowacourse.movie.db.ReservationEntity
import woowacourse.movie.db.toReservation
import woowacourse.movie.model.Seat

@ExtendWith(MockKExtension::class)
class ReservationPresenterTest {
    private lateinit var presenter: ReservationPresenter

    @RelaxedMockK
    private lateinit var view: ReservationContract.View

    @RelaxedMockK
    private lateinit var db: ReservationDatabase

    @BeforeEach
    fun setup() {
        presenter = ReservationPresenter(view, db)
    }

    @Test
    fun `loadReservations을 호출하면_예매_내역을_보여준다`() {
        val data = listOf(ReservationEntity("영화 제목", "2024.05.09", "10:00", listOf(Seat(0, 0)), "선릉"))
        every { db.reservationDao().getAllReservation() } returns data

        presenter.loadReservations()

        val convertedData = data.map { it.toReservation() }
        verify { view.displayReservations(convertedData) }
    }
}
