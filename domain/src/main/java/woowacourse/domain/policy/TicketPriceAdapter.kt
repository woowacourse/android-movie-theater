package woowacourse.domain.policy

import woowacourse.domain.policy.decorator.DatePolicy
import woowacourse.domain.policy.decorator.TimePolicy
import woowacourse.domain.ticket.Ticket

class TicketPriceAdapter {
    fun getPayment(ticket: Ticket): Int {
        var policy: Policy = DefaultPolicy(ticket)
        policy = DatePolicy(policy)
        policy = TimePolicy(policy)
        return policy.cost()
    }
}
