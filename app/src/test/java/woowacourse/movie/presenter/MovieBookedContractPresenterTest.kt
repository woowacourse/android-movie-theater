//package woowacourse.movie.presenter
//
//import io.kotest.assertions.any
//import io.mockk.Runs
//import io.mockk.every
//import io.mockk.just
//import io.mockk.mockk
//import io.mockk.verify
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import woowacourse.movie.domain.Theater
//import woowacourse.movie.fixture.MovieFixture
//import woowacourse.movie.moviebooked.MovieBookedContract
//import woowacourse.movie.moviebooked.MovieBookedPresenter
//
//class MovieBookedContractPresenterTest {
//    private lateinit var view: MovieBookedContract.View
//    private lateinit var presenter: MovieBookedContract.Presenter
//
//    @BeforeEach
//    fun setUp() {
//        view = mockk()
//        presenter = MovieBookedPresenter(view)
//    }
//
//    @Test
//    fun loadMovie_호출시_View의_showMovieInfo가_호출된다() {
//        // given - 예약 정보가 주어짐
//        every { view.showReservation(any()) } just Runs
//
//        // when - loadBookedStatus를 호출 시
//        presenter.loadReservationInfo(1)
//
//        // then - showBookedStatus()가 호출된다
//        verify {
//            view.showReservation(any())
//        }
//    }
//}
