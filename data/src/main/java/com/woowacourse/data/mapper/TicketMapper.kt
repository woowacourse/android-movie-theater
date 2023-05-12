package com.woowacourse.data.mapper

import com.woowacourse.data.model.DataTicket
import woowacourse.movie.domain.model.ticket.DomainTicket

fun DataTicket.toDomain(): DomainTicket =
    DomainTicket(count = count)

fun DomainTicket.toData(): DataTicket =
    DataTicket(count = count)
