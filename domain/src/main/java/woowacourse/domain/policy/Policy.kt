package woowacourse.domain.policy

import woowacourse.domain.ticket.Ticket

abstract class Policy(val ticket: Ticket) {
    abstract val payment: Int
    abstract fun cost(): Int
}
