package woowacourse.movie.presentation.settings

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.settings.SettingsData

class SettingsPresenterTest {
    private lateinit var view: SettingsContract.View
    private lateinit var presenter: SettingsContract.Presenter
    private lateinit var settingsData: SettingsData

    @Before
    fun `setUp`() {
        view = mockk()
        settingsData = mockk(relaxed = true)
        presenter = SettingsPresenter(view, settingsData)
    }

    @Test
    fun `알림 가능한지 읽어와서 false 이면 스위치 초기값을 false 로 세팅한다`() {
        // given & when
        every { settingsData.isAvailable } returns false
        val isNotifiableSlot = slot<Boolean>()
        every { view.initNotifiable(capture(isNotifiableSlot)) } just runs

        // when
        presenter.initNotifiable()

        // then
        val expected = false
        val actual = isNotifiableSlot.captured
        assertEquals(expected, actual)
    }

    @Test
    fun `알림 가능한지 읽어와서 true 이면 스위치 초기값을 true 로 세팅한다`() {
        // given
        every { settingsData.isAvailable } returns true
        val isNotifiableSlot = slot<Boolean>()
        every { view.initNotifiable(capture(isNotifiableSlot)) } just runs

        // when
        presenter.initNotifiable()

        // then
        val expected = true
        val actual = isNotifiableSlot.captured
        assertEquals(expected, actual)
    }
}
