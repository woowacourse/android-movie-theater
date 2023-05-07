package woowacourse.movie.main

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.FragmentType
import woowacourse.movie.view.main.MainContract
import woowacourse.movie.view.main.MainPresenter

class MainPresenterTest {
    private lateinit var presenter: MainContract.Present
    private lateinit var view: MainContract.View

    @Before
    fun setUp() {
        view = mockk()

        presenter = MainPresenter(view)
    }

    @Test
    fun `홈 프래그먼트 타입으로 홈 프래그먼트를 생성한다`() {
        val type = FragmentType.HOME

        val actual = presenter.createFragment(type)
        val expected = "MovieListFragment"
        assertEquals(expected, actual::class.simpleName)
    }

    @Test
    fun `예매 목록 프래그먼트 타입으로 예매 목록 프래그먼트를 생성한다`() {
        val type = FragmentType.RESERVATION_LIST

        val actual = presenter.createFragment(type)
        val expected = "ReservationListFragment"
        assertEquals(expected, actual::class.simpleName)
    }

    @Test
    fun `설정 프래그먼트 타입으로 설정 프래그먼트를 생성한다`() {
        val type = FragmentType.SETTING

        val actual = presenter.createFragment(type)
        val expected = "SettingsFragment"
        assertEquals(expected, actual::class.simpleName)
    }

    @Test
    fun `홈 버튼을 누르면 영화 목록 프래그먼트가 보여진다`() {
        // given
        val type = FragmentType.HOME
        val slot = slot<FragmentType>()
        justRun { view.showFragment(capture(slot)) }

        // when
        presenter.setFragment(type)

        // then
        verify { view.showFragment(slot.captured) }
    }

    @Test
    fun `예매 목록 버튼을 누르면 예매 목록 프래그먼트가 보여진다`() {
        // given
        val type = FragmentType.RESERVATION_LIST
        val slot = slot<FragmentType>()
        justRun { view.showFragment(capture(slot)) }

        // when
        presenter.setFragment(type)

        // then
        verify { view.showFragment(slot.captured) }
    }

    @Test
    fun `설정 버튼을 누르면 설정 프래그먼트가 보여진다`() {
        // given
        val type = FragmentType.SETTING
        val slot = slot<FragmentType>()
        justRun { view.showFragment(capture(slot)) }

        // when
        presenter.setFragment(type)

        // then
        verify { view.showFragment(slot.captured) }
    }
}
