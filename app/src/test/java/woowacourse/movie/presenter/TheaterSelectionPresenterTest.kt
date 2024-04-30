package woowacourse.movie.presenter

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.presenter.theater.TheaterSelectionContract
import woowacourse.movie.presenter.theater.TheaterSelectionPresenter

@ExtendWith(MockKExtension::class)
class TheaterSelectionPresenterTest {
    @MockK
    private lateinit var view: TheaterSelectionContract.View
    private lateinit var presenter: TheaterSelectionContract.Presenter

    @BeforeEach
    fun setUp() {
        presenter = TheaterSelectionPresenter(view)
    }

    @Test
    fun `극장_목록에서_극장을_선택하면_선택된_극장_ID와_영화_ID를_가지고_예매_화면으로_이동한다`() {
        every { view.navigateToDetail(any(), any()) } just runs
        presenter.loadTheater(
            theaterId = 0,
            movieId = 0,
        )
        verify { view.navigateToDetail(0, 0) }
    }
}
