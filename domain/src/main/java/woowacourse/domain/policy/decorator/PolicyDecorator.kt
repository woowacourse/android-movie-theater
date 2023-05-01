package woowacourse.domain.policy.decorator

import woowacourse.domain.policy.Policy

abstract class PolicyDecorator(policy: Policy) : Policy(policy.ticket) {
    override val payment: Int = policy.cost()
}
