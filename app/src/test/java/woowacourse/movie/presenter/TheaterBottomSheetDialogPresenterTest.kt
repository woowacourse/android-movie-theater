package woowacourse.movie.presenter

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.dummyMovie
import woowacourse.movie.view.theater.TheaterContract
import woowacourse.movie.view.theater.TheaterPresenter

class TheaterBottomSheetDialogPresenterTest {
    private lateinit var presenter: TheaterContract.Presenter
    private lateinit var view: TheaterContract.View

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = TheaterPresenter(view)
    }

    @Test
    fun `해당 영화에 대한 영화관 상영 정보를 불러온다`() {
        // Given
        every { view.showTheaters(any()) } just Runs

        // When
        presenter.fetchTheaters(dummyMovie)

        // Then
        verify { view.showTheaters(any()) }
    }
}
