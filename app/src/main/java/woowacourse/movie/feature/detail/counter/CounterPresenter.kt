package woowacourse.movie.feature.detail.counter

import woowacourse.movie.model.CountState

class CounterPresenter(
    val view: CounterContract.View
) : CounterContract.Presenter {

    override var countNumber: CountState = CountState.of(1)
        set(value) {
            field = value
            view.setCountNumber(value)
        }

    override fun minus() {
        if (countNumber.value == 1) {
            view.showLimitMin()
            return
        }
        countNumber -= 1
    }

    override fun plus() {
        if (countNumber.value == 20) {
            view.showLimitMin()
            return
        }
        countNumber += 1
    }

    override fun setCountState(countState: CountState) {
        countNumber = countState
    }
}
