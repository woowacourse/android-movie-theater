package woowacourse.movie.presenter

import io.mockk.* // ktlint-disable no-wildcard-imports
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.view.main.MainContract
import woowacourse.movie.view.main.MainPresenter
import woowacourse.movie.view.main.Screen

class MainPresenterTest {
    private lateinit var presenter: MainContract.Presenter
    private lateinit var view: MainContract.View

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = MainPresenter(view)
    }

    @Test
    fun 화면을_전환한다() {
        // given
        val slot = slot<Screen>()
        every { view.setScreen(capture(slot)) } just runs
        // when
        presenter.changeScreen(Screen.Setting)
        // then
        assertEquals(slot.captured, Screen.Setting)
        verify { view.setScreen(slot.captured) }
    }
}
