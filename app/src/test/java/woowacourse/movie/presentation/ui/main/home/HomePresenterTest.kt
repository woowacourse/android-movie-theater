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
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.presentation.ui.utils.DummyData.load

@ExtendWith(MockKExtension::class)
class HomePresenterTest {
    @MockK
    private lateinit var view: HomeContract.View

    private lateinit var presenter: HomeContract.Presenter

    @MockK
    private lateinit var repository: ScreenRepository

    @BeforeEach
    fun setUp() {
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
}
