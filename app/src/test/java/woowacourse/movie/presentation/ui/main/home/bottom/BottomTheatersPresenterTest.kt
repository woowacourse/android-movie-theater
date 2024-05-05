package woowacourse.movie.presentation.ui.main.home.bottom

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.presentation.ui.utils.DummyData.dummyTheaterCount

@ExtendWith(MockKExtension::class)
class BottomTheatersPresenterTest {
    @MockK
    private lateinit var view: BottomTheaterContract.View

    @MockK
    private lateinit var repository: ScreenRepository

    private val presenter = BottomTheaterPresenter(view, repository)

    @Test
    fun `선택된 영화의 id로 해당 영화를 상영 중인 상영관 정보들을 찾아 뷰에 전달한다`() {
        // given
        val movieId = 1

        every { repository.findTheaterCount(movieId) } returns
            Result.success(dummyTheaterCount)
        every { view.showBottomTheater(dummyTheaterCount, movieId) } just runs

        // when
        presenter.fetchTheaterCounts(movieId)

        // then
        verify { view.showBottomTheater(dummyTheaterCount, movieId) }
    }
}
