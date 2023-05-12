package com.example.domain.repositoryImpl

import com.example.domain.model.Tickets
import com.example.domain.repository.TicketsRepository

object TicketsSampleRepository : TicketsRepository {
    private val tickets: MutableList<Tickets> = mutableListOf()

    override fun allTickets(): List<Tickets> = tickets.toList()

    override fun addTicket(ticket: Tickets) { tickets.add(ticket) }
}
