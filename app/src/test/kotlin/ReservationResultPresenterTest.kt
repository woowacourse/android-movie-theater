import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import woowacourse.movie.model.MovieReservation
import woowacourse.movie.model.MovieTheater
import woowacourse.movie.repository.MovieRepository
import woowacourse.movie.reservationresult.ReservationResultContract
import woowacourse.movie.reservationresult.ReservationResultPresenter
import woowacourse.movie.reservationresult.toReservationResultUiModel

class ReservationResultPresenterTest {
    private lateinit var view: ReservationResultContract.View

    private lateinit var repository: MovieRepository

    private lateinit var presenter: ReservationResultPresenter

    @BeforeEach
    fun setUp() {
        view = mockk<ReservationResultContract.View>()
        repository = mockk<MovieRepository>()
        presenter = ReservationResultPresenter(repository, view)
    }

    @Test
    @DisplayName("예약 Id를 통해 예약 세부 정보 데이터를 불러와, 정제해서 뷰에게 전달한다.")
    fun deliver_booking_detail_When_load_reservation_data_Using_id() {
        // when
        val expectedUiModel = MovieReservation.STUB.toReservationResultUiModel(MovieTheater.STUB_A.name)
        every { view.showResult(expectedUiModel) } just Runs
        every { repository.movieReservationById(RESERVATION_ID) } returns MovieReservation.STUB
        every { repository.theaterById(0L) } returns MovieTheater.STUB_A

        // given
        presenter.loadReservationResult(RESERVATION_ID)

        // then
        verify(exactly = 1) { view.showResult(expectedUiModel) }
    }

    companion object {
        private const val RESERVATION_ID = 0L
    }
}
