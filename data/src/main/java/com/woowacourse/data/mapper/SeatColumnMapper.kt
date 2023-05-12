package com.woowacourse.data.mapper

import com.woowacourse.data.model.DataSeatCol
import woowacourse.movie.domain.model.seat.DomainSeatCol

fun DataSeatCol.toDomain(): DomainSeatCol =
    DomainSeatCol(value = value)

fun DomainSeatCol.toData(): DataSeatCol =
    DataSeatCol(value = value)
