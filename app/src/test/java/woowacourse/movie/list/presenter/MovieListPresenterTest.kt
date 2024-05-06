package woowacourse.movie.list.presenter

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.list.contract.MovieListContract
import woowacourse.movie.list.model.Movie

class MovieListPresenterTest {
    private lateinit var view: MovieListContract.View
    private lateinit var presenter: MovieListContract.Presenter

    @BeforeEach
    fun setUp() {
        view = mockk<MovieListContract.View>()
        presenter = MovieListPresenter(view)
    }

    @Test
    fun `영화 정보를 목록에 띄워야 한다`() {
        every { view.showMoviesInfo(any(), any()) } just runs
        // when
        presenter.setMoviesInfo()
        // then
        verify { view.showMoviesInfo(any(), any()) }
    }

    @Test
    fun `영화 개수는 9개여야 한다(광고 2개 포함)`() {
        // given
        val movieListSlot = slot<List<Movie>>()
        every { view.showMoviesInfo(capture(movieListSlot), any()) } just runs
        // when
        presenter.setMoviesInfo()
        // then
        assertThat(movieListSlot.captured.size).isEqualTo(9)
    }

    enum class CarType {
        A, B, C, D, E
    }

    enum class RecordingOutcome { RECORDED }

    class Car(val type: CarType) {
        fun go(goType: CarType) {
            println("${goType}으로간다")
        }
    }

    @Test
    fun name() {
        val car = mockk<Car>()
        val typeSlot = slot<CarType>()
        val list = mutableListOf<CarType>()

        every {
            car.go(
                goType = capture(list)
            )
        } just runs
        car.go(CarType.C)
        car.go(CarType.A)
//        println("알송ㅋㅋ ${typeSlot}")
        val argument = list
//        assertThat(argument).isEqualTo(3)
        println("아아알송 $argument")
    }
}
