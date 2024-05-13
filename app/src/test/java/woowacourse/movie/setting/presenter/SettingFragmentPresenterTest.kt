package woowacourse.movie.setting.presenter

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.MovieApplication
import woowacourse.MovieApplication.Companion.sharedPrefs
import woowacourse.movie.setting.presenter.contract.SettingContract

class SettingFragmentPresenterTest {
    private lateinit var view: SettingContract.View
    private lateinit var presenter: SettingPresenter

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = SettingPresenter(view)
        mockkObject(MovieApplication.Companion)
    }

    @Test
    fun `setUpAlarmSwitch를 호출하면 AlarmSwitch의 세팅 정보를 불러온다`() {
        every { view.setUpAlarmSwitch(any()) } just runs
        every { sharedPrefs.getSavedAlarmSetting() } returns true

        presenter.loadAlarmSwitch()

        verify { view.setUpAlarmSwitch(true) }
    }
}
