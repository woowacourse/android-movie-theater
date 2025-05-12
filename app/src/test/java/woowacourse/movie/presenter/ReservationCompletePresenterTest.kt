package woowacourse.movie.presenter

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.dummyReservationInfoUiModel
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.view.reservation.complete.ReservationCompleteContract
import woowacourse.movie.presentation.view.reservation.complete.ReservationCompletePresenter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ReservationCompletePresenterTest {
    private lateinit var presenter: ReservationCompleteContract.Presenter
    private lateinit var view: ReservationCompleteContract.View

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = ReservationCompletePresenter(view)
    }

    @Test
    fun `데이터를 가져오면 예매 정보를 화면에 표시한다`() {
        val reservationInfoUiModelSlot = slot<ReservationInfoUiModel>()

        // given
        every { view.showReservationInfo(capture(reservationInfoUiModelSlot)) } just Runs

        // when
        presenter.fetchData(dummyReservationInfoUiModel)

        // then
        verify { view.showReservationInfo(any()) }

        assertThat(reservationInfoUiModelSlot.captured.title).isEqualTo("라라랜드")
        assertThat(reservationInfoUiModelSlot.captured.dateTime).isEqualTo(
            LocalDateTime.of(
                LocalDate.of(2025, 4, 1),
                LocalTime.of(14, 0),
            ),
        )
        assertThat(reservationInfoUiModelSlot.captured.seats.totalPrice).isEqualTo(25_000)
    }
}
