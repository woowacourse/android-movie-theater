package woowacourse.movie.movie.mapper.ticket

import domain.TicketPrice
import woowacourse.movie.movie.dto.ticket.TicketPriceDto

fun TicketPriceDto.mapToTicketPrice(): TicketPrice {
    return TicketPrice(this.price)
}

fun TicketPrice.mapToTicketPriceDto(): TicketPriceDto {
    return TicketPriceDto(this.price)
}
