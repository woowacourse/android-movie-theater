package woowacourse.movie.domain.model.booking

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

class Booking(
    val movieTitle: String,
    val theaterName: String,
    val screeningDate: LocalDate,
    val screeningTime: LocalTime,
    val count: AdmissionCount,
) : Serializable
