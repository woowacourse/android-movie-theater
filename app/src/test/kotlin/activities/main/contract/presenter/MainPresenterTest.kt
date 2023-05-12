package activities.main.contract.presenter

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.presentation.ui.main.contract.MainContract
import woowacourse.movie.presentation.ui.main.contract.presenter.MainPresenter
import woowacourse.movie.presentation.model.mainstate.MainScreenState
import woowacourse.movie.presentation.model.mainstate.MainState

internal class MainPresenterTest {
    private lateinit var presenter: MainContract.Presenter
    private lateinit var view: MainContract.View

    @Before
    internal fun setUp() {
        view = mockk(relaxUnitFun = true)

        presenter = MainPresenter()
        presenter.attach(view)
    }

    @Test
    internal fun 마지막_화면_상태가_히스토리일_때_예매_내역_화면_을_보여준다() {
        // given
        val state = mockk<MainState>(relaxed = true)
        every { state.latestScreen } returns MainScreenState.History

        // when
        presenter.setState(state)

        // then
        verify(exactly = 1) { view.showHistoryScreen() }
    }

    @Test
    internal fun 마지막_화면_상태가_홈일_때_홈_화면을_보여준다() {
        // given
        val state = mockk<MainState>(relaxed = true)
        every { state.latestScreen } returns MainScreenState.Home

        // when
        presenter.setState(state)

        // then
        verify(exactly = 1) { view.showHomeScreen() }
    }

    @Test
    internal fun 마지막_화면_상태가_설정일_때_설정_화면_을_보여준다() {
        // given
        val state = mockk<MainState>(relaxed = true)
        every { state.latestScreen } returns MainScreenState.Setting

        // when
        presenter.setState(state)

        // then
        verify(exactly = 1) { view.showSettingScreen() }
    }

    @Test
    internal fun 예매_내역_화면을_보면_상태를_히스토리로_바꾸고_예매_내역_화면_조회_여부가_참이다() {
        // given
        val expected = MainState(isShownHistory = true, latestScreen = MainScreenState.History)

        // when
        presenter.changeHistoryState()

        // then
        verify(exactly = 1) { view.showHistoryScreen() }
        val actual = presenter.getState()
        assertEquals(expected, actual)
    }

    @Test
    internal fun 홈_화면을_보면_상태를_홈으로_바꾼다() {
        // given
        val expected = MainState(latestScreen = MainScreenState.Home)

        // when
        presenter.changeHomeState()

        // then
        verify(exactly = 1) { view.showHomeScreen() }
        val actual = presenter.getState()
        assertEquals(expected, actual)
    }

    @Test
    internal fun 설정_화면을_보면_상태를_설정으로_바꾼다() {
        // given
        val expected = MainState(latestScreen = MainScreenState.Setting)

        // when
        presenter.changeSettingState()

        // then
        verify(exactly = 1) { view.showSettingScreen() }
        val actual = presenter.getState()
        assertEquals(expected, actual)
    }

    @Test
    internal fun 영화_화면을_조회했었다면_참을_반환한다() {
        // given
        val expected = true

        // when
        presenter.changeHistoryState()

        // then
        val actual = presenter.wasShownHistory()
        assertEquals(expected, actual)
    }
}
