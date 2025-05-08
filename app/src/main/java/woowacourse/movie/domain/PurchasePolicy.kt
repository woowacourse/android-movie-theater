package woowacourse.movie.domain

import java.time.LocalTime

enum class PurchasePolicy(val time: LocalTime) {
    NormalPolicy(LocalTime.of(0, 15)),
}
