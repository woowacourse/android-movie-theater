package woowacourse.movie.presentation.choiceSeat

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.domain.model.tools.Movie
import woowacourse.movie.domain.model.tools.seat.Seat
import woowacourse.movie.domain.model.tools.seat.Seats
import woowacourse.movie.model.data.remote.DummyMovieStorage
import woowacourse.movie.model.data.storage.BookedTicketStorage
import woowacourse.movie.model.data.storage.MovieStorage
import woowacourse.movie.model.data.storage.SettingStorage
import woowacourse.movie.presentation.model.ReservationModel
import java.time.LocalDate
import java.time.LocalDateTime

class ChoiceSeatPresenterTest {
    private lateinit var presenter: ChoiceSeatPresenter
    private lateinit var view: ChoiceSeatContract.View
    private lateinit var alarmManager: ChoiceSeatContract.AlarmManager
    private lateinit var settingStorage: SettingStorage
    private lateinit var movieStorage: MovieStorage
    private lateinit var reservation: ReservationModel
    private lateinit var bookedTicketStorage: BookedTicketStorage

    @Before
    fun initChoiceSeatPresenter() {
        view = mockk(relaxed = true)
        alarmManager = mockk(relaxed = true)
        // 알림 허용 상태이다.
        settingStorage = mockk()
        every { settingStorage.getNotificationSettings() } returns true
        // 현재 검색로직 밖에 없어서 movieStorage mock 객체 사용하지 않음(추후 변경 소요 발생할 수 있음)
        movieStorage = DummyMovieStorage()
        val movie = Movie(
            1,
            "해리 포터와 마법사의 돌",
            LocalDate.of(2024, 3, 1),
            LocalDate.of(2024, 3, 31),
            152,
            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다."
        )
        bookedTicketStorage = mockk(relaxed = true)
        // 영화 해리포터의 2024-03-03-09:00 티켓 3장 티켓을 구입하였다.
        reservation = ReservationModel(movie.id, LocalDateTime.of(2024, 3, 3, 9, 0), 3, "강남")
        presenter = ChoiceSeatPresenter(
            view = view,
            alarmManager = alarmManager,
            settingStorage = settingStorage,
            movieStorage = movieStorage,
            bookedTicketStorage = bookedTicketStorage,
            reservation = reservation
        )
    }

    @Test
    fun `티켓을 발급할 시 알람매니저에 알람이 추가된다`() {
        // given
        every { alarmManager.setAlarm(any()) } just Runs

        // when
        presenter.issueTicket()

        // then
        verify(exactly = 1) { alarmManager.setAlarm(any()) }
    }

    @Test
    fun `티켓의 갯수만큼 좌석을 선택했다면 True를 반환한다`() {
        view = mockk(relaxed = true)
        alarmManager = mockk(relaxed = true)
        settingStorage = mockk(relaxed = true)
        movieStorage = DummyMovieStorage()
        val movie = Movie(
            1,
            "해리 포터와 마법사의 돌",
            LocalDate.of(2024, 3, 1),
            LocalDate.of(2024, 3, 31),
            152,
            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다."
        )
        // 영화 해리포터의 2024-03-03-09:00 티켓 3장 티켓을 구입하였다.
        reservation = ReservationModel(movie.id, LocalDateTime.of(2024, 3, 3, 9, 0), 3, "강남")
        presenter = ChoiceSeatPresenter(
            view = view,
            alarmManager = alarmManager,
            seats = Seats(
                List(3) {
                    mockk<Seat>().apply {
                        every { this@apply.compareTo(any()) } returns 1
                    }
                }.toSet()
            ),
            settingStorage = settingStorage,
            movieStorage = movieStorage,
            bookedTicketStorage = bookedTicketStorage,
            reservation = reservation
        )

        // when
        val actual = presenter.checkReservationCountFull()

        // then
        assertEquals(true, actual)
    }

    @Test
    fun `티켓의 갯수만큼 좌석을 선택하지 않았다면 false를 반환한다`() {
        view = mockk(relaxed = true)
        alarmManager = mockk(relaxed = true)
        settingStorage = mockk(relaxed = true)
        movieStorage = DummyMovieStorage()
        val movie = Movie(
            1,
            "해리 포터와 마법사의 돌",
            LocalDate.of(2024, 3, 1),
            LocalDate.of(2024, 3, 31),
            152,
            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다."
        )
        // 영화 해리포터의 2024-03-03-09:00 티켓 3장 티켓을 구입하였다.
        reservation = ReservationModel(movie.id, LocalDateTime.of(2024, 3, 3, 9, 0), 3, "강남")
        presenter = ChoiceSeatPresenter(
            view = view,
            alarmManager = alarmManager,
            seats = Seats(
                List(2) {
                    mockk<Seat>().apply {
                        every { this@apply.compareTo(any()) } returns 1
                    }
                }.toSet()
            ),
            settingStorage = settingStorage,
            movieStorage = movieStorage,
            bookedTicketStorage = bookedTicketStorage,
            reservation = reservation
        )

        // when
        val actual = presenter.checkReservationCountFull()

        // then
        assertEquals(false, actual)
    }

    @Test
    fun `좌석을 더해주면 티켓총액을 다시그리고 최종 확인버튼의 상태를 업데이트한다`() {
        // given
        every { view.updateTextChoicePaymentAmount(any()) } just Runs
        every { view.updateConfirmButtonState(any()) } just Runs

        // when
        presenter.addSeat(1, 1)

        // then
        // 최초 화면 초기화용으로 한번 불리기 때문에 exactly = 2이다
        verify(exactly = 2) { view.updateTextChoicePaymentAmount(any()) }
        verify(exactly = 2) { view.updateTextChoicePaymentAmount(any()) }
    }

    @Test
    fun `좌석을 빼주면 티켓총액을 다시그리고 최종 확인버튼의 상태를 업데이트한다`() {
        // given
        view = mockk(relaxed = true)
        alarmManager = mockk(relaxed = true)
        settingStorage = mockk(relaxed = true)
        movieStorage = DummyMovieStorage()
        val movie = Movie(
            1,
            "해리 포터와 마법사의 돌",
            LocalDate.of(2024, 3, 1),
            LocalDate.of(2024, 3, 31),
            152,
            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다."
        )
        // 영화 해리포터의 2024-03-03-09:00 티켓 3장 티켓을 구입하였다.
        reservation = ReservationModel(movie.id, LocalDateTime.of(2024, 3, 3, 9, 0), 3, "강남")
        presenter = ChoiceSeatPresenter(
            view = view,
            alarmManager = alarmManager,
            seats = mockk<Seats>(relaxed = true),
            settingStorage = settingStorage,
            movieStorage = movieStorage,
            bookedTicketStorage = bookedTicketStorage,
            reservation = reservation
        )
        every { view.updateTextChoicePaymentAmount(any()) } just Runs
        every { view.updateConfirmButtonState(any()) } just Runs

        // when
        presenter.removeSeat(1, 1)

        // then
        // 최초 화면 초기화용으로 한번 불리기 때문에 exactly = 2이다
        verify(exactly = 2) { view.updateTextChoicePaymentAmount(any()) }
        verify(exactly = 2) { view.updateTextChoicePaymentAmount(any()) }
    }
}
