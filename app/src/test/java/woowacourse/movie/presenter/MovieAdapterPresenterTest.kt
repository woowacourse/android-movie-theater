package woowacourse.movie.presenter

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.contract.MovieAdapterContract
import woowacourse.movie.data.MovieListItemsViewData
import woowacourse.movie.data.repository.AdvertisementRepository
import woowacourse.movie.data.repository.MovieRepository
import woowacourse.movie.domain.Advertisement
import woowacourse.movie.domain.DateRange
import woowacourse.movie.domain.Image
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.advertismentPolicy.AdvertisementPolicy
import woowacourse.movie.domain.advertismentPolicy.MovieAdvertisementPolicy
import woowacourse.movie.domain.mock.AdvertisementMock
import woowacourse.movie.domain.mock.AdvertisementPolicyMock
import woowacourse.movie.domain.mock.MovieMock
import woowacourse.movie.mapper.AdvertisementMapper.toView
import woowacourse.movie.mapper.MovieMapper.toView
import java.time.LocalDate

class MovieAdapterPresenterTest {
    lateinit var movieAdapterPresenter: MovieAdapterContract.Presenter
    lateinit var view: MovieAdapterContract.View
    private lateinit var movieRepository: MovieRepository
    private lateinit var advertisementRepository: AdvertisementRepository

    @Before
    fun init() {
        view = mockk()
        movieRepository = mockk()
        advertisementRepository = mockk()
        movieAdapterPresenter =
            MovieAdapterPresenter(view, movieRepository, advertisementRepository)
    }

    @Test
    fun 영화_정보를_받아와_Adapter에_설정한다() {
        // given
        val movieSlot = slot<MovieListItemsViewData>()
        every { view.setAdapterData(capture(movieSlot)) } just runs
        every { movieRepository.requestMovies() } returns listOf(
            fakeMovie()
        )
        every { advertisementRepository.requestAdvertisements() } returns listOf(
            fakeAdvertisement()
        )
        every { advertisementRepository.requestAdvertisementPolicy() } returns fakeAdvertisementPolicy()

        // when
        val movies = listOf(MovieMock.createMovie()).map { it.toView() }
        val advertisements = listOf(AdvertisementMock.createAdvertisement()).map { it.toView() }
        val advertisementPolicy = AdvertisementPolicyMock.createAdvertisementPolicy().movieCount

        val expect = MovieListItemsViewData.from(
            movies, advertisements, advertisementPolicy
        )
        movieAdapterPresenter.setMovieList()

        // then
        val actual = movieSlot.captured

        assertEquals(actual, expect)
        verify { view.setAdapterData(expect) }
    }

    private fun fakeMovie(): Movie = Movie(
        Image(0),
        "해리 포터 1",
        DateRange(
            LocalDate.of(2021, 1, 1),
            LocalDate.of(2021, 1, 31),
        ),
        111,
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
    )

    private fun fakeAdvertisement(): Advertisement = Advertisement(
        Image(4)
    )

    private fun fakeAdvertisementPolicy(): AdvertisementPolicy = MovieAdvertisementPolicy(3, 1)
}
