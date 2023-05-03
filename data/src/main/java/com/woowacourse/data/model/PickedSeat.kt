package com.woowacourse.data.model

import woowacourse.movie.domain.model.seat.Seat

typealias DataPickedSeats = PickedSeats

class PickedSeats(val seats: List<Seat> = emptyList())
