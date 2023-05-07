package com.example.domain.repositoryImpl

import com.example.domain.model.Cinema
import com.example.domain.model.Movie
import com.example.domain.repository.CinemaRepository

object CinemaSampleRepository : CinemaRepository {
    private val dummyMovies = MovieSameRepository.allMovies()
    private val cinemas: List<Cinema> = listOf(
        Cinema("선릉aaaaaaaaaaaaaaa", dummyMovies),
        Cinema("잠실", dummyMovies),
        Cinema("강남", emptyList())
    )

    override fun allCinema(): List<Cinema> = cinemas.toList()

    override fun findCinema(movie: Movie): List<Cinema> {
        return cinemas.filter { it.movies.contains(movie) }
    }
}
