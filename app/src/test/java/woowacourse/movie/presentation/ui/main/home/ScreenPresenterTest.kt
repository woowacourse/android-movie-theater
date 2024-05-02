package woowacourse.movie.presentation.ui.main.home

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.domain.model.TheaterCount
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.presentation.ui.utils.DummyData.load

@ExtendWith(MockKExtension::class)
class ScreenPresenterTest {
    @MockK
    private lateinit var view: HomeContract.View

    private lateinit var presenter: HomeContract.Presenter

    @MockK
    private lateinit var repository: ScreenRepository

    @BeforeEach
    fun setUp() {
        every { repository.load() } returns load()
        presenter = HomePresenter(view, repository)
    }

    @Test
    fun `ScreenPresenter가 loadScreens()을 했을 때, view에게 screens 데이터를 전달한다`() {
        // given
        every { repository.findTheaterCount(any()) } returns
            Result.success(
                listOf(
                    TheaterCount(
                        3,
                        "선릉",
                        180,
                    ),
                ),
            )
        every { view.showBottomTheater(any(), any()) } just runs

        // when
        presenter.onScreenClick(0)

        // then
        verify { view.showBottomTheater(any(), any()) }
    }

    @Test
    fun `ScreenPresenter가 유효한 상영장 id를 통해 onScreenClick을 했을 때, view에게 사영장 id를 전달한다`() {
        // given
        every { view.navigateToDetail(any(), any()) } just runs

        // when
        presenter.onTheaterClick(1, 1)

        // then
        verify { view.navigateToDetail(any(), any()) }
    }
}
