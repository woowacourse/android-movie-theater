package com.woowacourse.data.mapper

import com.woowacourse.data.model.DataTicketPrice
import woowacourse.movie.domain.model.movie.DomainTicketPrice

fun DataTicketPrice.toDomain(): DomainTicketPrice =
    DomainTicketPrice(amount = amount)

fun DomainTicketPrice.toData(): DataTicketPrice =
    DataTicketPrice(amount = amount)
