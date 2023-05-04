package woowacourse.movie.domain.theater

import woowacourse.movie.domain.policy.DiscountPolicy

data class Theater(
    val name: String,
    val seatInfo: SeatInfo,
    val discountPolicies: List<DiscountPolicy>,
)
