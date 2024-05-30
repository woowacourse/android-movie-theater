package woowacourse.movie.data.repository

import woowacourse.movie.data.model.MovieData
import woowacourse.movie.data.model.MoviePosterData
import woowacourse.movie.data.source.MovieDataSource
import woowacourse.movie.domain.model.Image

class FakeMovieDataSource : MovieDataSource {
    private val movies = listOf(fakeMovie1Data, fakeMovie2Data, fakeMovie3Data)

    private val movieImages =
        listOf(
            MoviePosterData(1, Image.StringImage("1")),
            MoviePosterData(2, Image.StringImage("2")),
            MoviePosterData(3, Image.StringImage("3")),
        )

    override fun findById(id: Int): MovieData = movies.find { it.id == id } ?: throw NoSuchElementException()

    override fun imageSrc(id: Int): Image<Any> = movieImages.find { it.movieId == id }?.poster ?: throw NoSuchElementException()

    override fun imageSrc2(id: Int): MoviePosterData = movieImages.find { it.movieId == id } ?: throw NoSuchElementException()

    companion object {
        val fakeMovie1Data = MovieData(1, "title1", 1, "description1")
        val fakeMovie2Data = MovieData(2, "title2", 2, "description2")
        val fakeMovie3Data = MovieData(3, "title3", 3, "description3")
    }
}
