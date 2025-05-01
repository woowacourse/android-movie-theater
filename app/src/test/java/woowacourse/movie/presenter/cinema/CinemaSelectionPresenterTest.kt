package woowacourse.movie.presenter.cinema

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.contract.cinema.CinemaSelectionContract
import woowacourse.movie.data.cinema.FakeCinemaData
import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.domain.reservation.Movie
import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ShowtimePolicy
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class CinemaSelectionPresenterTest {
    private lateinit var fakeCurrent: LocalDateTime
    private lateinit var jamsilShowtimePolicy: ShowtimePolicy
    private lateinit var gangnamShowtimePolicy: ShowtimePolicy
    private lateinit var fakeScreening: Screening
    private lateinit var jamsilCinema: Cinema
    private lateinit var gangnamCinema: Cinema
    private lateinit var view: CinemaSelectionContract.View
    private lateinit var presenter: CinemaSelectionContract.Presenter

    @BeforeEach
    fun setUp() {
        fakeCurrent = LocalDateTime.of(2025, 4, 2, 10, 0)
        jamsilShowtimePolicy =
            object : ShowtimePolicy() {
                override fun showtimes(current: LocalDateTime): List<LocalTime> = listOf(LocalTime.of(9, 0))
            }
        gangnamShowtimePolicy =
            object : ShowtimePolicy() {
                override fun showtimes(current: LocalDateTime): List<LocalTime> = emptyList()
            }
        fakeScreening =
            Screening(
                Movie(0, "해리 포터와 마법사의 돌", 152),
                LocalDate.of(2025, 4, 1),
                LocalDate.of(2025, 4, 25),
                fakeCurrent,
            )
        jamsilCinema = Cinema("잠실 극장", listOf(fakeScreening), jamsilShowtimePolicy)
        gangnamCinema = Cinema("강남 극장", listOf(fakeScreening), gangnamShowtimePolicy)
        view = mockk()
        presenter =
            CinemaSelectionPresenter(
                view,
                fakeScreening,
                FakeCinemaData(listOf(jamsilCinema, gangnamCinema)),
            )
    }

    @Test
    fun `선택 가능한 극장을 보여준다`() {
        // given
        every {
            view.setCinemas(
                listOf(
                    jamsilCinema,
                ),
            )
        } just Runs

        // when
        presenter.presentCinemas()

        // then
        verify {
            view.setCinemas(
                listOf(
                    jamsilCinema,
                ),
            )
        }
    }

    @Test
    fun `극장을 선택할 수 있다`() {
        // given
        every {
            view.navigateToReservationScreen(
                screening = fakeScreening,
                cinemaName = "잠실 극장",
                showtimePolicy = jamsilShowtimePolicy,
            )
        } just Runs

        // when
        presenter.onSelectCinema("잠실 극장", jamsilShowtimePolicy)

        // then
        verify {
            view.navigateToReservationScreen(
                screening = fakeScreening,
                cinemaName = "잠실 극장",
                showtimePolicy = jamsilShowtimePolicy,
            )
        }
    }
}
