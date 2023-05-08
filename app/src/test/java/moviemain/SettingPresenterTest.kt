package moviemain

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.domain.price.Price
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.SettingRepository
import woowacourse.movie.domain.reservation.Reservation
import woowacourse.movie.domain.system.Seat
import woowacourse.movie.view.moviemain.setting.SettingContract
import woowacourse.movie.view.moviemain.setting.SettingPresenter
import java.time.LocalDateTime

class SettingPresenterTest {
    private lateinit var presenter: SettingContract.Presenter
    private lateinit var view: SettingContract.View
    val settingManager = object : SettingRepository {
        private var isAlarm = false
        override fun getIsAlarmSetting(): Boolean {
            return isAlarm
        }

        override fun setIsAlarmSetting(isOn: Boolean) {
            isAlarm = isOn
        }
    }

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        every { view.requestNotificationPermission() } returns true

        val reservationRepository = object : ReservationRepository {
            override fun add(reservation: Reservation) {
            }

            override fun findAll(): List<Reservation> {
                return listOf(
                    Reservation(
                        "해리 포터와 마법사의 돌",
                        LocalDateTime.of(2024, 4, 1, 12, 0),
                        listOf(Seat(1, 1)),
                        Price(10000),
                        "선릉 극장",
                    ),
                )
            }
        }

        presenter = SettingPresenter(view, settingManager, reservationRepository)
    }

    @Test
    fun 토글_초기값을_설정할_수_있다() {
        val slot = slot<Boolean>()
        every { view.setToggle(capture(slot)) } just runs
        presenter.initState()
        assertEquals(settingManager.getIsAlarmSetting(), slot.captured)
        verify { view.setToggle(slot.captured) }
    }

    @Test
    fun 클릭하면_토글을_ON한다() {
        val slot = slot<Boolean>()
        every { view.setToggle(capture(slot)) } just runs
        presenter.changeAlarmState(true)
        assertEquals(true, slot.captured)
        verify { view.setToggle(slot.captured) }
    }

    @Test
    fun ON인_토글을_다시_클릭하면_OFF한다() {
        val slot = slot<Boolean>()
        every { view.setToggle(capture(slot)) } just runs
        presenter.changeAlarmState(false)
        assertEquals(false, slot.captured)
        verify { view.setToggle(slot.captured) }
    }
}
