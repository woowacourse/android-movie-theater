package woowacourse.movie.data.cinema

import woowacourse.movie.domain.model.tools.cinema.Cinema

interface CinemaData {
    fun getCinemas(): List<Cinema>
}
