package woowacourse.movie.ui.adv

import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.AdvState

class AdvDetailPresenterTest {
    private lateinit var view: AdvDetailContract.View
    private lateinit var advState: AdvState

    @Before
    fun setUp() {
        // given
        view = mockk(relaxed = true)
        advState = mockk(relaxed = true)
    }

    @Test
    fun `광고 상세 화면을 초기화한다`() {
        // when
        AdvDetailPresenter(view, advState)

        // then
        verify { view.showAdv(advState) }
    }
}
