package woowacourse.movie.movie.moviedetail

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import woowacourse.movie.movie.dto.movie.MovieDto
import woowacourse.movie.movie.dto.theater.MovieTheaterDto
import woowacourse.movie.movie.dto.theater.MovieTheatersDto
import java.time.LocalDate
import java.time.LocalTime

internal class MovieDetailPresenterTest {

    private lateinit var view: MovieDetailContract.View
    private lateinit var presenter: MovieDetailContract.Presenter

    private val fakeMovieDto = MovieDto(
        title = "Joker",
        startDate = LocalDate.of(2023, 5, 15),
        endDate = LocalDate.of(2023, 5, 20),
        runningTime = 120,
        description = "입 찢어진 남자가 조증 걸려서 웃고 다니는 영화",
        moviePoster = 1,
        theaters = MovieTheatersDto(listOf(MovieTheaterDto("선릉", listOf(LocalTime.of(13, 0)))))
    )

    private val fakeTheater = MovieTheaterDto("상일", listOf(LocalTime.of(13,0)))

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = MovieDetailPresenter(view)
    }

    @Test
    fun `선택한 영화의 제목이 잘 보여진다`(){
        val movieTitleSlot = slot<String>()

        every { view.showMovieInfo(any(), capture(movieTitleSlot), any()) } answers { nothing }

        presenter.initActivity(fakeMovieDto, fakeTheater)

        val expected = "Joker"
        Assert.assertEquals(expected, movieTitleSlot.captured)
        verify { view.showMovieInfo(any(), movieTitleSlot.captured, any()) }
    }

    @Test
    fun `선택한 영화의 설명이 잘 보여진다`(){
        val movieTitleSlot = slot<String>()

        every { view.showMovieInfo(any(), any(), capture(movieTitleSlot)) } answers { nothing }

        presenter.initActivity(fakeMovieDto, fakeTheater)

        val expected = "입 찢어진 남자가 조증 걸려서 웃고 다니는 영화"
        Assert.assertEquals(expected, movieTitleSlot.captured)
        verify { view.showMovieInfo(any(), any(), movieTitleSlot.captured) }
    }

    @Test
    fun `선택한 영화의 상영날짜가 언제인지가 잘 보여진다`() {
        val movieRunningDateSlot = slot<String>()

        every { view.formatMovieRunningDate(any(), any()) } returns "상영일: 2023.5.15 ~ 2023.5.20"
        every { view.showMovieDateInfo(capture(movieRunningDateSlot), any()) } answers { nothing }

        presenter.initActivity(fakeMovieDto, fakeTheater)

        val expected = "상영일: 2023.5.15 ~ 2023.5.20"
        Assert.assertEquals(expected, movieRunningDateSlot.captured)
        verify { view.showMovieDateInfo(movieRunningDateSlot.captured, any()) }
    }

    @Test
    fun `선택한 영화의 러닝타임이 어느정도인지 잘 보여진다`() {
        val movieRunningDateSlot = slot<String>()

        every { view.formatMovieRunningTime(any()) } returns "러닝타임: 120분"
        every { view.showMovieDateInfo(any(), capture(movieRunningDateSlot)) } answers { nothing }

        presenter.initActivity(fakeMovieDto, fakeTheater)

        val expected = "러닝타임: 120분"
        Assert.assertEquals(expected, movieRunningDateSlot.captured)
        verify { view.showMovieDateInfo(any(), movieRunningDateSlot.captured) }
    }
}