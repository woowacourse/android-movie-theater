package woowacourse.movie.domain.movieseat

import java.time.LocalTime

enum class RemainTimePolicy(val time: LocalTime) {
    NormalPolicy(LocalTime.of(0, 30)),
}
