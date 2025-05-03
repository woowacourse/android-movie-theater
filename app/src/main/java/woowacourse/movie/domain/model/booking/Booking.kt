package woowacourse.movie.domain.model.booking

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

    val theaterName: String,
    val movieTitle: String,
    val bookingDate: LocalDate,
    val bookingTime: LocalTime,
    val count: PeopleCount,
) : Serializable
