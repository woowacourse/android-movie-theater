package com.example.domain.repository

import com.example.domain.model.Cinema
import com.example.domain.model.Movie

interface CinemaRepository {
    fun allCinema(): List<Cinema>

    fun findCinema(movie: Movie): List<Cinema>
}
