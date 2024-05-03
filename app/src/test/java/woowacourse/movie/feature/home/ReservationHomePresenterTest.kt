package woowacourse.movie.feature.home

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.model.movie.Movie

@ExtendWith(MockKExtension::class)
class ReservationHomePresenterTest {
    @MockK
    private lateinit var view: ReservationHomeContract.View
    private lateinit var presenter: ReservationHomeContract.Presenter

    @BeforeEach
    fun setUp() {
        presenter = ReservationHomePresenter(view)
    }

    @Test
    fun `영화_목록의_지금_예매_버튼을_누르면_선택된_영화의_ID를_가지고_극장_선택으로_이동한다`() {
        every { view.navigateToDetail(any()) } just runs
        presenter.loadMovie(
            Movie(
                0,
                0,
                "해리포터",
                emptyList(),
                "",
                "",
            ),
        )
        verify { view.navigateToDetail(0) }
    }
}
