package com.woowacourse.data.mapper

import com.woowacourse.data.model.DataPickedSeats
import woowacourse.movie.domain.model.seat.DomainPickedSeats

fun DataPickedSeats.toDomain(): DomainPickedSeats =
    DomainPickedSeats(seats = seats.map { it.toDomain() })

fun DomainPickedSeats.toData(): DataPickedSeats =
    DataPickedSeats(seats = seats.map { it.toData() })
