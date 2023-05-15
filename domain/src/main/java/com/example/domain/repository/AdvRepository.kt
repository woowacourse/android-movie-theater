package com.example.domain.repository

import com.example.domain.model.Adv

interface AdvRepository {
    fun allAdv(): List<Adv>
}
