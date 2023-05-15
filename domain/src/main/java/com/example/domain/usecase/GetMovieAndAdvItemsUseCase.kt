package com.example.domain.usecase

import com.example.domain.model.Adv
import com.example.domain.model.Movie
import com.example.domain.repository.AdvRepository
import com.example.domain.repository.MovieRepository

class GetMovieAndAdvItemsUseCase(
    private val movieRepository: MovieRepository,
    private val advRepository: AdvRepository
) {
    operator fun invoke(
        onSuccess: (List<Movie>, List<Adv>) -> Unit,
        onFailure: () -> Unit = {}
    ) {
        var movie: List<Movie> = listOf()
        kotlin.runCatching { movieRepository.allMovies() }
            .onSuccess { movie = it }
            .onFailure {
                onFailure()
                return@invoke
            }

        var adv: List<Adv> = listOf()
        kotlin.runCatching { advRepository.allAdv() }
            .onSuccess { adv = it }
            .onFailure {
                onFailure()
                return@invoke
            }

        onSuccess(movie, adv)
    }
}
