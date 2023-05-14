package woowacourse.movie.mapper.ticket

import domain.TicketPrice
import woowacourse.movie.ticket.dto.TicketPriceDto

fun TicketPriceDto.mapToTicketPrice(): TicketPrice {
    return TicketPrice(this.price)
}

fun TicketPrice.mapToTicketPriceDto(): TicketPriceDto {
    return TicketPriceDto(this.price)
}
