package woowacourse.movie.presenter

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.contract.MainContract
import woowacourse.movie.view.MainActivity.Companion.SCREEN_ID_HOME
import woowacourse.movie.view.MainActivity.Companion.SCREEN_ID_RESERVATION_HISTORY
import woowacourse.movie.view.MainActivity.Companion.SCREEN_ID_SETTING

class MainPresenterTest {
    private lateinit var view: MainContract.View
    private lateinit var presenter: MainContract.Presenter

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = MainPresenter(view)
    }

    @Test
    fun `하단의 네비게이션 뷰 통해 영화 예매 내역 화면으로 이동할 수 있다`() {
        // given
        every { view.updateScreen(SCREEN_ID_RESERVATION_HISTORY) } just Runs

        // when
        presenter.presentScreen(SCREEN_ID_RESERVATION_HISTORY)

        // then
        verify { view.updateScreen(SCREEN_ID_RESERVATION_HISTORY) }
    }

    @Test
    fun `하단의 네비게이션 뷰 통해 홈 화면으로 이동할 수 있다`() {
        // given
        every { view.updateScreen(SCREEN_ID_HOME) } just Runs

        // when
        presenter.presentScreen(SCREEN_ID_HOME)

        // then
        verify { view.updateScreen(SCREEN_ID_HOME) }
    }

    @Test
    fun `하단의 네비게이션 뷰 통해 설정 화면으로 이동할 수 있다`() {
        // given
        every { view.updateScreen(SCREEN_ID_SETTING) } just Runs

        // when
        presenter.presentScreen(SCREEN_ID_SETTING)

        // then
        verify { view.updateScreen(SCREEN_ID_SETTING) }
    }
}
