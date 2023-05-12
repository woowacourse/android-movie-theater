package woowacourse.movie.movieList.bottomSheet

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.view.movieList.bottomSheet.TheaterSheetAdapter
import woowacourse.movie.view.movieList.bottomSheet.TheaterSheetContract
import woowacourse.movie.view.movieList.bottomSheet.TheaterSheetPresenter

class TheaterSheetPresenterTest {
    private lateinit var presenter: TheaterSheetContract.Presenter
    private lateinit var view: TheaterSheetContract.View

    @Before
    fun setUp() {
        view = mockk()

        presenter = TheaterSheetPresenter(view)
    }

    @Test
    fun `바텀 시트 다이얼로그의 리사이클러뷰 상영관 어댑터를 설정한다`() {
        // given
        val slot = slot<TheaterSheetAdapter>()
        justRun { view.setTheaterAdapter(capture(slot)) }

        // when
        presenter.setAdapter(listOf()) {}

        // then
        verify { view.setTheaterAdapter(slot.captured) }
    }
}
