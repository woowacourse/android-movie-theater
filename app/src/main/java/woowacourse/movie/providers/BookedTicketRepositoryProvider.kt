package woowacourse.movie.providers

import woowacourse.movie.domain.model.BookedTicketRepository

object BookedTicketRepositoryProvider {
    private lateinit var bookedTicketRepository: BookedTicketRepository

    fun init(bookedTicketRepository: BookedTicketRepository) {
        this.bookedTicketRepository = bookedTicketRepository
    }

    fun provideBookedTicketRepository() = bookedTicketRepository
}
