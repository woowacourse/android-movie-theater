package woowacourse.movie.domain.cinema

import woowacourse.movie.domain.reservation.Screening

class Cinema(val name: String, val screenings: Set<Screening>)
