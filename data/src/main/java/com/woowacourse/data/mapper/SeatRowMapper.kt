package com.woowacourse.data.mapper

import com.woowacourse.data.model.DataSeatRow
import woowacourse.movie.domain.model.seat.DomainSeatRow

fun DataSeatRow.toDomain(): DomainSeatRow =
    DomainSeatRow(value = value)

fun DomainSeatRow.toData(): DataSeatRow =
    DataSeatRow(value = value)
