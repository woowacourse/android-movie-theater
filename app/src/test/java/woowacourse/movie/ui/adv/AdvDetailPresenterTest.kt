package woowacourse.movie.ui.adv

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import woowacourse.movie.model.AdvState

class AdvDetailPresenterTest {
    @Test
    fun `광고 상세 화면을 초기화한다`() {
        // given
        val view = mockk<AdvDetailContract.View>()
        val advState = mockk<AdvState>()
        every { view.setAdv(any()) } returns Unit

        // when
        AdvDetailPresenter(view, advState)

        //
        verify { view.setAdv(advState) }
    }
}
