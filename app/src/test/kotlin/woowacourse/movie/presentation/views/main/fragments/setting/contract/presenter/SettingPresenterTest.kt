package woowacourse.movie.presentation.views.main.fragments.setting.contract.presenter

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.presentation.views.main.fragments.setting.contract.SettingContract

class SettingPresenterTest {
    private lateinit var presenter: SettingContract.Presenter
    private lateinit var view: SettingContract.View

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = SettingPresenter(view, mockk {
            every { getBoolean(any(), any()) } returns false
            every { setBoolean(any(), any()) } returns Unit
        })
    }

    @Test
    fun 푸시_스위치_상태를_가져와서_뷰에_반영한다() {
        // given
        val slot = slot<Boolean>()
        presenter = SettingPresenter(view, mockk {
            every { getBoolean(any(), any()) } returns false
        })
        every { view.changePushSwitchState(capture(slot)) } answers { nothing }

        // when
        presenter.getPushSwitchState()

        // then
        assertEquals(false, slot.captured)
    }

    @Test
    fun 스위치_상태를_변경하고_뷰에_반영한다() {
        // given
        val slot = slot<Boolean>()
        val newState = true
        presenter = SettingPresenter(view, mockk {
            every { setBoolean(any(), newState) } returns Unit
        })
        every { view.changePushSwitchState(capture(slot)) } answers { nothing }

        // when
        presenter.updatePushAllow(newState)

        // then
        assertEquals(newState, slot.captured)
    }

    @Test
    fun 권한이_허용되어_있으면_스위치_상태를_변경하여_뷰에_반영한다() {
        // given
        every { view.checkPushPermission() } returns true

        // when
        presenter.onPushSwitchClicked(true)

        // then
        verify(atLeast = 1) { view.changePushSwitchState(any()) }
    }

    @Test
    fun 권한이_허용되어_있지_않다면_권한_요청_다이얼로그를_띄운다() {
        // given
        every { view.checkPushPermission() } returns false

        // when
        presenter.onPushSwitchClicked(true)

        // then
        verify(atLeast = 1) { view.showPushPermissionDialog() }
    }
}
