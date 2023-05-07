package com.example.domain.usecase

import com.example.domain.model.Tickets
import com.example.domain.repository.TicketsRepository

class AddReservationTicketsUseCase(
    private val ticketsRepository: TicketsRepository,
) {
    operator fun invoke(tickets: Tickets, onSuccess: () -> Unit = {}, onFailure: () -> Unit = {}) {
        kotlin.runCatching { ticketsRepository.addTickets(tickets) }
            .onSuccess { onSuccess() }
            .onFailure { onFailure() }
    }
}
