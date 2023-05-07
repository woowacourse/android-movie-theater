package woowacourse.movie.presentation.settings

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.settings.SettingsData

class SettingsPresenterTest {
    private lateinit var view: SettingsContract.View
    private lateinit var presenter: SettingsContract.Presenter

    private object FakeSettingsData : SettingsData {
        override var isAvailable: Boolean = false
    }

    @Before
    fun `setUp`() {
        view = mockk()
        presenter = SettingsPresenter(view, FakeSettingsData)
    }

    @Test
    fun `알림 가능한지 읽어와서 false 이면 스위치 초기값을 false 로 세팅한다`() {
        // given & when
        FakeSettingsData.isAvailable = false
        val isNotifiableSlot = slot<Boolean>()
        every { view.initNotificationSwitch(capture(isNotifiableSlot)) } just runs

        // when
        presenter.initNotifiable()

        // then
        val expected = false
        val actual = isNotifiableSlot.captured
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `알림 가능한지 읽어와서 true 이면 스위치 초기값을 true 로 세팅한다`() {
        // given
        FakeSettingsData.isAvailable = true
        val isNotifiableSlot = slot<Boolean>()
        every { view.initNotificationSwitch(capture(isNotifiableSlot)) } just runs

        // when
        presenter.initNotifiable()

        // then
        val expected = true
        val actual = isNotifiableSlot.captured
        Assert.assertEquals(expected, actual)
    }
}
