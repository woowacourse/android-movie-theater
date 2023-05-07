package com.example.domain.repository

import com.example.domain.model.Movie

interface MovieRepository {
    fun allMovies(): List<Movie>
}
