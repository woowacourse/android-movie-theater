package com.example.domain.usecase

import com.example.domain.model.Tickets
import com.example.domain.repository.TicketsRepository

class GetAllReservationTicketsUseCase(private val ticketsRepository: TicketsRepository) {
    operator fun invoke(onSuccess: (List<Tickets>) -> Unit, onFailure: () -> Unit) {
        kotlin.runCatching { ticketsRepository.allTickets() }
            .onSuccess { onSuccess(it) }
            .onFailure { onFailure() }
    }
}
