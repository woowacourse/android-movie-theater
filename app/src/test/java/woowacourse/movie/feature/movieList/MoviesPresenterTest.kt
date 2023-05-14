package woowacourse.movie.feature.movieList

import com.example.domain.model.Movie
import com.example.domain.repository.AdvRepository
import com.example.domain.repository.MovieRepository
import com.example.domain.repository.dataSource.advDataSources
import com.example.domain.repository.dataSource.movieDataSources
import com.example.domain.repository.dataSource.theaterDataSources
import com.example.domain.usecase.GetMovieAndAdvItemsUseCase
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.feature.movieList.itemModel.AdvItemModel
import woowacourse.movie.feature.movieList.itemModel.CommonItemModel
import woowacourse.movie.feature.movieList.itemModel.MovieItemModel
import woowacourse.movie.model.SelectTheaterAndMovieState
import woowacourse.movie.model.mapper.asPresentation
import java.time.LocalTime

internal class MoviesPresenterTest {
    private lateinit var view: MovieListContract.View
    private lateinit var presenter: MovieListContract.Presenter
    private lateinit var movieRepository: MovieRepository
    private lateinit var advRepository: AdvRepository

    @Before
    fun init() {
        view = mockk()
        movieRepository = mockk()
        advRepository = mockk()
        presenter =
            MoviesPresenter(view, GetMovieAndAdvItemsUseCase(movieRepository, advRepository))
    }

    @Test
    fun 영화와_광고_아이템_목록을_불러들이고_영화3개당_광고1개가_나오도록_뷰에_설정한다() {
        every { movieRepository.allMovies() } returns mockMovies // 레포지토리가 목 영화목록을 반환하도록 정의
        every { advRepository.allAdv() } returns mockAdvs // 목 광고목록을 반환하도록 정의

        val itemsSlot = slot<List<CommonItemModel>>()
        every { view.updateItems(capture(itemsSlot)) } just Runs

        // 실행
        presenter.loadMovieAndAdvItems()

        // 뷰에 설정되는 메소드가 호출됐는지 검사
        verify { view.updateItems(any()) }
        // 캡쳐한 내용 검사
        val actual = itemsSlot.captured
        val advIndexed = (3 until actual.size step 4)
        actual.forEachIndexed { index, commonItemModel ->
            if (index in advIndexed) {
                assert(commonItemModel is AdvItemModel)
            } else {
                assert(commonItemModel is MovieItemModel)
            }
        }
    }

    @Test
    fun 바텀시트에서_극장이_선택되면_영화_액티비티로_이동한다() {
        every { view.navigateMovieDetail(any()) } just Runs // 뷰의 영화_상세_액티비티 이동 메소드 정의

        presenter.receiveTheaterInfo(mockTheater) // 실행. 여기서 가짜 객체로 실행

        verify { view.navigateMovieDetail(mockTheater) } // 실행됐는지 여부 검사
    }

    private val mockMovies: List<Movie> = mutableListOf<Movie>().apply {
        repeat(2) { addAll(movieDataSources.toList()) }
    }

    private val mockAdvs = advDataSources

    private val mockTheater = SelectTheaterAndMovieState(
        theaterDataSources[0].asPresentation(),
        mockMovies[0].asPresentation(),
        listOf(LocalTime.of(10, 0), LocalTime.of(12, 0))
    )
}
