package woowacourse.movie.presenter

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.presentation.repository.AdRepository
import woowacourse.movie.presentation.repository.MovieRepository
import woowacourse.movie.presentation.view.bottomNavigationBar.HomeFragment
import woowacourse.movie.presentation.view.screening.ScreeningContract
import woowacourse.movie.presentation.view.screening.ScreeningPresenterImpl

class ScreeningPresenterTest {
    private lateinit var view: ScreeningContract.View
    private lateinit var presenter: ScreeningPresenterImpl
    private lateinit var movieRepository: MovieRepository
    private lateinit var adRepository: AdRepository

    @BeforeEach
    fun setUp() {
        view = mockk<HomeFragment>(relaxed = true)
        movieRepository = mockk(relaxed = true)
        adRepository = mockk(relaxed = true)
        presenter = ScreeningPresenterImpl(movieRepository, adRepository)
    }

    @Test
    fun `View를 설정하면 presenter의 view가 초기화된다`() {
        // given
        presenter.attachView(view)

        // when
        presenter.onViewSetUp()

        // then
        verify { view.onUpdateMovies(any()) }
        verify { view.onUpdateAds(any()) }
    }

    @Test
    fun `View를 detach하면 presenter의 view가 null이 된다`() {
        // given
        presenter.detachView()

        // when
        presenter.loadMovie()
        presenter.loadAds()

        // then
        verify(exactly = 0) { view.onUpdateMovies(any()) }
        verify(exactly = 0) { view.onUpdateAds(any()) }
    }

    @Test
    fun `예약 버튼이 클릭되면 view에서 극장을 선택하도록 presenter가 호출한다`() {
        // given
        presenter.attachView(view)

        // when
        presenter.onReserveButtonClicked(0)

        // then
        verify { view.showTheaterBottomSheet(any()) }
    }
}
