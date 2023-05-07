package woowacourse.movie.view.moviemain.setting

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.AlarmPreference
import woowacourse.movie.domain.Minute
import woowacourse.movie.domain.Money
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.Seat
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.ReservationUiModel
import java.time.LocalDate
import java.time.LocalDateTime

class SettingPresenterTest {
    private lateinit var presenter: SettingPresenter
    private lateinit var view: SettingContract.View
    private lateinit var alarmPreference: AlarmPreference
    private lateinit var reservationRepository: ReservationRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        alarmPreference = mockk(relaxed = true)
        reservationRepository = mockk(relaxed = true)
        presenter = SettingPresenter(
            view, alarmPreference, reservationRepository
        )
    }

    @Test
    fun `알림이 켜져있다면 토클을 켠다`() {
        every { alarmPreference.isAlarmOn(false) } returns true
        val slot = slot<Boolean>()
        every { view.setToggleState(capture(slot)) } returns Unit

        presenter.loadAlarmSetting()

        assertEquals(true, slot.captured)
    }

    @Test
    fun `알림이 꺼져있다면 토클을 끈다`() {
        every { alarmPreference.isAlarmOn(false) } returns false
        val slot = slot<Boolean>()
        every { view.setToggleState(capture(slot)) } returns Unit

        presenter.loadAlarmSetting()

        assertEquals(false, slot.captured)
    }

    @Test
    fun `저장된 예매 내역을 가져와 알림을 다시 등록한다`() {
        every { reservationRepository.findAll() } returns listOf(fakeReservation())
        val reservationsSlot = slot<List<ReservationUiModel>>()
        every { view.resetAlarms(capture(reservationsSlot), any()) } returns Unit

        presenter.onResetAlarms()

        val expected = listOf(fakeReservation().toUiModel())
        assertEquals(expected, reservationsSlot.captured)
    }

    private fun fakeReservation(): Reservation {
        val movie = Movie(
            1,
            "아바타",
            LocalDate.of(2024, 3, 2),
            LocalDate.of(2024, 3, 31),
            Minute(120),
            1,
            "줄거리"
        )
        return Reservation(
            1,
            "선릉 극장",
            movie,
            listOf(Seat(1, 1)),
            LocalDateTime.of(2024, 3, 5, 12, 0),
            Money(10000)
        )
    }
}
