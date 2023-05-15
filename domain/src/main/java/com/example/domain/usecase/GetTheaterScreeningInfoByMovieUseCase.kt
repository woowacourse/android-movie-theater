package com.example.domain.usecase

import com.example.domain.model.Movie
import com.example.domain.model.TheaterScreeningInfo
import com.example.domain.repository.TheaterRepository

class GetTheaterScreeningInfoByMovieUseCase(private val theaterRepository: TheaterRepository) {
    operator fun invoke(
        movie: Movie,
        onSuccess: (List<TheaterScreeningInfo>) -> Unit,
        onFailure: () -> Unit
    ) {
        kotlin.runCatching { theaterRepository.getAllTheaters() }
            .onSuccess {
                val theaterInfos =
                    it.filter { theater -> movie in theater.screeningInfos.map { it.movie } }
                        .map {
                            it.copy(screeningInfos = it.screeningInfos.filter { movie == it.movie })
                        }
                onSuccess(theaterInfos)
            }
            .onFailure {
                onFailure()
            }
    }
}
