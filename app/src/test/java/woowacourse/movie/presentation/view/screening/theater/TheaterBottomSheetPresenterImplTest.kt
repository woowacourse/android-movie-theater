package woowacourse.movie.presentation.view.screening.theater

import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.data.repository.TheaterRepositoryImpl
import woowacourse.movie.presentation.screening.theater.TheaterBottomSheetContract
import woowacourse.movie.presentation.screening.theater.TheaterBottomSheetPresenterImpl
import woowacourse.movie.presentation.uimodel.TheaterUiModel

@ExtendWith(MockKExtension::class)
class TheaterBottomSheetPresenterImplTest {
    @MockK
    private lateinit var view: TheaterBottomSheetContract.View

    private val movieId = 1

    private val theaterRepository = TheaterRepositoryImpl

    @InjectMockKs
    private lateinit var presenter: TheaterBottomSheetPresenterImpl

    @Test
    fun `movieId가 들어오면 극장 정보를 보여준다`() {
        // given
        var theatersInfo = emptyList<TheaterUiModel>()
        every {
            theatersInfo = theaterRepository.theatersInfo(movieId)
            view.showTheaterInfo(theatersInfo)
        } just Runs

        // when
        presenter.loadTheaters()

        // then
        verify(exactly = 1) { view.showTheaterInfo(theatersInfo) }
    }
}
