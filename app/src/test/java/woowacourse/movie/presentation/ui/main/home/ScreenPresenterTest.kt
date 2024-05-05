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
//        every { repository.load() } returns load()
        presenter = HomePresenter(view, repository)
    }

    @Test
    fun `상영 중인 영화 정보를 불러와 뷰에 전달한다`() {
        // given
        every { repository.load() } returns load()
        every { view.showScreenList(any()) } just runs

        // when
        presenter.fetchScreens()

        // then
        verify { view.showScreenList(load()) }
    }

    @Test
    fun `영화의 id로 해당 영화를 상영 중인 상영관을 찾아 뷰에 전달한다`() {
        // given
        val movieId = 1
        val theaterCount =
            listOf(
                TheaterCount(
                    3,
                    "선릉",
                    180,
                ),
            )
        every { repository.findTheaterCount(any()) } returns
            Result.success(theaterCount)
        every { view.showBottomTheater(any(), any()) } just runs

        // when
        presenter.selectMovie(movieId)

        // then
        verify { view.showBottomTheater(theaterCount, movieId) }
    }
}
