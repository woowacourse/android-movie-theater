package com.woowacourse.data.mapper

import com.woowacourse.data.model.DataSeat
import woowacourse.movie.domain.model.seat.DomainSeat

fun DataSeat.toDomain(): DomainSeat =
    DomainSeat(row = row.toDomain(), col = col.toDomain())

fun DomainSeat.toData(): DataSeat =
    DataSeat(row = row.toData(), col = col.toData())
