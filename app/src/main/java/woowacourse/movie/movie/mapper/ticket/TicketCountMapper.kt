package woowacourse.movie.movie.mapper.ticket

import domain.TicketCount
import woowacourse.movie.movie.dto.ticket.TicketCountDto

fun TicketCountDto.mapToTicketCount(): TicketCount {
    return TicketCount(this.numberOfPeople)
}

fun TicketCount.mapToTicketCountDto(): TicketCountDto {
    return TicketCountDto(this.numberOfPeople)
}
