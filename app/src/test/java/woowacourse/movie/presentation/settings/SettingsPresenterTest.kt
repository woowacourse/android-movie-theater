package woowacourse.movie.presentation.settings

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.data.storage.SettingStorage

class SettingsPresenterTest {
    private lateinit var presenter: SettingsPresenter
    private lateinit var view: SettingsContract.View
    private lateinit var settingStorage: SettingStorage

    @Before
    fun initSettingsPresenter() {
        view = mockk(relaxed = true)
        settingStorage = mockk(relaxed = true)
        presenter = SettingsPresenter(view, settingStorage)
    }

    @Test
    fun `notification setting 값을 true로 설정 해주면 switch의 isChecked 속성이 true로 변경된다`() {
        // given
        val slot = slot<Boolean>()
        every { view.setSwitchSelectedState(capture(slot)) } just Runs
        every { settingStorage.setNotificationSettings(any()) } returns true

        // when
        presenter.setNotificationSettings(true)

        // then
        val actual = slot.captured
        assertEquals(true, actual)
        verify(exactly = 2) { view.setSwitchSelectedState(any()) }
    }

    @Test
    fun `notification setting 값을 false로 설정 해주면 switch의 isChecked 속성이 false로 변경된다`() {
        // given
        val slot = slot<Boolean>()
        every { view.setSwitchSelectedState(capture(slot)) } just Runs
        every { settingStorage.setNotificationSettings(any()) } returns true

        // when
        presenter.setNotificationSettings(false)

        // then
        val actual = slot.captured
        assertEquals(false, actual)
        verify(exactly = 2) { view.setSwitchSelectedState(any()) }
    }

    @Test
    fun `notification setting 값을 저장소에서 불러올때 switch의 상태를 변화시키는 함수를 호출한다`() {
        // given
        every { view.setSwitchSelectedState(any()) } just Runs
        every { settingStorage.getNotificationSettings() } returns true

        // when
        presenter.getNotificationSettings()

        // then
        verify(exactly = 2) { view.setSwitchSelectedState(any()) }
    }
}
