package woowacourse.movie.model.mapper

import com.example.domain.model.Theater
import woowacourse.movie.model.TheaterState

fun TheaterState.asDomain(): Theater = Theater(theaterId, theaterName)

fun Theater.asPresentation(): TheaterState = TheaterState(theaterId, theaterName)
