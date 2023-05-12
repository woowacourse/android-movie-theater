package com.woowacourse.data.repository.movie

import com.woowacourse.data.datasource.movie.MovieDataSource
import com.woowacourse.data.mapper.toDomain
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.domain.model.repository.MovieRepository

class LocalMovieRepository(
    private val localDataSource: MovieDataSource.Local,
) : MovieRepository {
    override fun getMovies(size: Int): List<Movie> =
        localDataSource.getMovies(size).map { it.toDomain() }
}
