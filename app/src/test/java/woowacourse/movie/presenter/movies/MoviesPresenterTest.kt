package woowacourse.movie.presenter.movies

import io.kotest.core.spec.style.AnnotationSpec.After
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MoviesPresenterTest {
    private lateinit var presenter: MoviesPresenter
    private lateinit var view: MoviesContracts.View

    @BeforeEach
    fun setup() {
        view = mockk()
        presenter = MoviesPresenter(view)
    }

    @Test
    fun `뷰를 초기화하면 영화 목록이 보인다`() {
        // given:
        every { view.showMovies(any()) } just Runs

        // when:
        presenter.initView()

        // then:
        verify { view.showMovies(any()) }
    }

    @Test
    fun `광고를 클릭하면 광고가 보인다`() {
        // given:
        every { view.showAdvertisement(any()) } just Runs

        // when:
        presenter.onAdvertisementRequested("https://navar.com")

        // then:
        verify { view.showAdvertisement(any()) }
    }

    @After
    fun finish() {
        clearAllMocks()
    }
}
