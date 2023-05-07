package woowacourse.movie.presenter

import io.mockk.*
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.contract.MainContract

class MainPresenterTest {
    private lateinit var presenter: MainContract.Presenter
    private lateinit var view: MainContract.View

    @Before
    fun setUp() {
        view = mockk()
        presenter = MainPresenter(view)
    }

    @Test
    fun 바텀네비게이션_매뉴를_클릭했을때_해당ItemID의_Fragment를_보여준다() {
        // given
        val itemId = 2
        val slot = slot<Int>()
        every { view.changeFragmentByItemID(capture(slot)) } just runs
        // when
        presenter.onClickBottomNavigationItem(itemId)
        // then
        assertEquals(itemId, slot.captured)
        verify { view.changeFragmentByItemID(itemId) }
    }

    @Test
    fun 바텀네비게이션의_상태를_업데이트_시킨다() {
        // given
        val slot = slot<Int>()
        every { view.getSavedNavigationItemId() } returns 1
        every { view.setSelectedFragmentView(capture(slot)) } just runs
        // when
        presenter.updateFragmentView()
        // then
        val actual = 1
        assertEquals(actual, slot.captured)
        verify { view.setSelectedFragmentView(slot.captured) }
    }
}
