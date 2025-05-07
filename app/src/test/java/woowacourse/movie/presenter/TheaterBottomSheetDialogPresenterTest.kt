package woowacourse.movie.presenter

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.ui.movielist.contract.TheaterBottomSheetDialogContract
import woowacourse.movie.ui.movielist.presenter.TheaterBottomSheetDialogPresenter

class TheaterBottomSheetDialogPresenterTest {
    private lateinit var presenter: TheaterBottomSheetDialogPresenter
    private lateinit var view: TheaterBottomSheetDialogContract.View

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = TheaterBottomSheetDialogPresenter(view)
    }

    @Test
    fun `프레젠터가 영화를 불러오면 뷰는 영화관들을 보여준다`() {
        presenter.loadAvailableTheaters(1L)
        verify { view.showTheaters(any()) }
    }
}
