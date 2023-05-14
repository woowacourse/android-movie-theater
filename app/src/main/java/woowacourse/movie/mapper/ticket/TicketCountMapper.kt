package woowacourse.movie.mapper.ticket

import domain.TicketCount
import woowacourse.movie.ticket.dto.TicketCountDto

fun TicketCountDto.mapToTicketCount(): TicketCount {
    return TicketCount(this.numberOfPeople)
}

fun TicketCount.mapToTicketCountDto(): TicketCountDto {
    return TicketCountDto(this.numberOfPeople)
}
