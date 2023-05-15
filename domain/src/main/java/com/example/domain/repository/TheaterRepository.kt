package com.example.domain.repository

import com.example.domain.model.TheaterScreeningInfo

interface TheaterRepository {
    fun getAllTheaters(): List<TheaterScreeningInfo> // 모든 극장의 상영 정보를
}
