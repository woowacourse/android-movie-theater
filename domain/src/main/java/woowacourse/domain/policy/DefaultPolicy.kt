package woowacourse.domain.policy

import woowacourse.domain.ticket.Ticket

class DefaultPolicy(ticket: Ticket) : Policy(ticket) {
    override val payment: Int = ticket.seat.rank.price

    override fun cost(): Int {
        return payment
    }
}
