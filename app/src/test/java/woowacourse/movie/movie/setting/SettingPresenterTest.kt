package woowacourse.movie.movie.setting

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.movie.BasePreference
import woowacourse.movie.movie.MainActivity.Companion.SETTING_PREFERENCE_KEY

internal class SettingPresenterTest {

    private lateinit var presenter: SettingPresenter
    private lateinit var view: SettingContract.View
    private lateinit var mockSettingPreference: BasePreference

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        mockSettingPreference = mockk(relaxed = true)
        presenter = SettingPresenter(view, MockSettingPreference())
    }

    @Test
    fun 알림_허용이_커져있다면_스위치가_켜져있다 () {
        // Given
        val switchState = true
        every { mockSettingPreference.getBoolean(SETTING_PREFERENCE_KEY) } returns switchState
        val slot = slot<Boolean>()
        every { view.initSwitch(capture(slot)) } returns Unit

        // When
        presenter.initFragment()

        // Then
        assertEquals(true, slot.captured)
    }

    @Test
    fun 알림_허용이_꺼져있다면_스위치가_꺼져있다 () {
        // Given
        val switchState = false
        every { mockSettingPreference.getBoolean(SETTING_PREFERENCE_KEY) } returns switchState
        val slot = slot<Boolean>()
        every { view.initSwitch(capture(slot)) } returns Unit

        // When
        presenter.initFragment()

        // Then
        assertEquals(false, slot.captured)
    }

    @Test
    fun SharedPreference에_저장된_값에따라_스위치의_상태가_잘_나타난다 () {
        // Given
        val switchState = true
        every { mockSettingPreference.getBoolean(any()) } returns switchState

        // When
        val result = presenter.getSwitchState()

        // Then
        assertEquals(switchState, result)
        verify { mockSettingPreference.getBoolean(SETTING_PREFERENCE_KEY) }
    }

    @Test
    fun 스위치가_체크되면_SharedPreference에_해당_값이_저장된다() {
        // Given
        val isChecked = true

        // When
        presenter.setSwitchState(isChecked)

        // Then
        verify { mockSettingPreference.setBoolean(SETTING_PREFERENCE_KEY, isChecked) }
    }


    // private lateinit var presenter: SettingPresenter
    // private lateinit var view: SettingContract.View
    // private lateinit var mockSettingPreference: MockSettingPreference
    //
    // @Before
    // fun setUp() {
    //     view = mockk()
    //     mockSettingPreference = MockSettingPreference()
    //     presenter = SettingPresenter(view, MockSettingPreference())
    // }
    //
    // @Test
    // fun `알람이 허용되었다면 SharePreference에 true가 저장되어 있다`() {
    //     // given
    //     mockSettingPreference = MockSettingPreference()
    //     mockSettingPreference.setBoolean("test", true)
    //     presenter = SettingPresenter(view, mockSettingPreference)
    //
    //     val slot = slot<Boolean>()
    //     every { view.initSwitch(capture(slot)) } returns Unit
    //
    //     //when
    //     presenter.initFragment()
    //
    //     //then
    //     val actual = slot.captured
    //     assertEquals(true, actual)
    //     verify { view.initSwitch(actual) }
    // }
}

class MockSettingPreference : BasePreference {
    private val mockSettingPreference = mutableMapOf<String, Boolean>()

    override fun setBoolean(key: String, value: Boolean) {
        mockSettingPreference[key] = value
        return
    }

    override fun getBoolean(key: String): Boolean {
        var value = false
        mockSettingPreference[key]?.let { value = it }
        return value
    }
}