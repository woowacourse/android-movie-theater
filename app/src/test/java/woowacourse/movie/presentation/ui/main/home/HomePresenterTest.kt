package woowacourse.movie.presentation.ui.main.home

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.domain.repository.TheaterRepository
import woowacourse.movie.presentation.ui.utils.DummyData.load
import woowacourse.movie.presentation.ui.utils.DummyData.theaterCount

@ExtendWith(MockKExtension::class)
class HomePresenterTest {
    @MockK
    private lateinit var view: HomeContract.View

    @MockK
    private lateinit var screenRepository: ScreenRepository

    @MockK
    private lateinit var theaterRepository: TheaterRepository

    @InjectMockKs
    private lateinit var presenter: HomePresenter

    @BeforeEach
    fun setUp() {
        every { screenRepository.load() } returns load()
    }

    @Test
    fun `ScreenPresenter가 loadScreens()을 했을 때, view에게 screens 데이터를 전달한다`() {
        // given
        val theaterCounts = listOf(theaterCount)
        every { theaterRepository.findTheaterCount(any()) } returns
            Result.success(theaterCounts)
        every { view.showBottomTheater(any(), any()) } just runs

        // when
        presenter.onScreenClick(0)

        // then
        verify { view.showBottomTheater(theaterCounts, 0) }
    }

    @Test
    fun `ScreenPresenter가 유효한 상영장 id를 통해 onScreenClick을 했을 때, view에게 사영장 id를 전달한다`() {
        // given
        every { view.navigateToDetail(any(), any()) } just runs

        // when
        presenter.onTheaterClick(1, 1)

        // then
        verify { view.navigateToDetail(1, 1) }
    }
}
