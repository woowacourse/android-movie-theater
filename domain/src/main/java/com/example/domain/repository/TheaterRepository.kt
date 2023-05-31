package com.example.domain.repository

import com.example.domain.model.TheaterScreeningInfo

interface TheaterRepository {
    fun getAllTheaters(): List<TheaterScreeningInfo>
}
