package com.example.domain.repository

import com.example.domain.model.Cinema
import com.example.domain.model.Movie

object CinemaRepository {
    private val dummyMovies = MovieRepository.allMovies()
    private val dummyMovies2 = MovieRepository.allMovies2()
    private val cinemas: List<Cinema> = listOf(
        Cinema("선릉aaaaaaaaaaaaaaa", dummyMovies),
        Cinema("잠실", dummyMovies),
        Cinema("강남", dummyMovies2),
        Cinema("강남", emptyList())
    )

    fun allCinema(): List<Cinema> = cinemas.toList()

    fun findCinema(movie: Movie): List<Cinema> {
        return cinemas.filter { it.movies.contains(movie) }
    }
}
