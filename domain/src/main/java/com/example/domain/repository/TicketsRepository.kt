package com.example.domain.repository

import com.example.domain.model.Tickets

object TicketsRepository {
    private val tickets: MutableList<Tickets> = mutableListOf()

    fun allTickets(): List<Tickets> = tickets.toList()

    fun addTicket(ticket: Tickets) = tickets.add(ticket)
}
