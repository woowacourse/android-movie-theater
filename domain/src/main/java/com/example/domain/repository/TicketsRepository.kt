package com.example.domain.repository

import com.example.domain.model.Tickets

interface TicketsRepository {

    fun getAllTickets(): List<Tickets>

    fun addTicket(ticket: Tickets)
}
