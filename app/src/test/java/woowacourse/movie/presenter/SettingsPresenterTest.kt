package woowacourse.movie.presenter

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.ui.settings.contract.SettingsContract
import woowacourse.movie.ui.settings.presenter.SettingsPresenter

class SettingsPresenterTest {
    private lateinit var presenter: SettingsPresenter
    private lateinit var view: SettingsContract.View

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = SettingsPresenter(view)
    }

    @Test
    fun `refreshChecked는 view의 setSwitchChecked를 호출한다`() {
        presenter.refreshChecked()

        verify { view.setSwitchChecked() }
    }
}
