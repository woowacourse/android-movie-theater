package com.example.domain

object ReservationRepository {
    private val reservations = mutableMapOf<Long, Reservation>()
    private var nextId = 1L

    fun save(reservation: Reservation): Long {
        reservation.id = nextId++
        reservations[reservation.id!!] = reservation
        return reservation.id!!
    }

    fun findAll(): List<Reservation> {
        return reservations.values.toList()
    }

    fun findById(id: Long): Reservation? {
        return reservations[id]
    }
}