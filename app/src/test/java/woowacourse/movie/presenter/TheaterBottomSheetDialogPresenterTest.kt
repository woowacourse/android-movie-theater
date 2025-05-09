package woowacourse.movie.presenter

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.MOVIE_HARRY_POTTER_AND_THE_PRISONER_OF_AZKABAN
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
    fun `상영 가능한 영화관을 추려내면 뷰는 영화관들을 보여준다`() {
        presenter.loadAvailableTheaters(MOVIE_HARRY_POTTER_AND_THE_PRISONER_OF_AZKABAN)
        verify { view.showTheaters(any()) }
    }
}
