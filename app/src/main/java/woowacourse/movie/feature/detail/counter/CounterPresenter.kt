package woowacourse.movie.feature.detail.counter

import woowacourse.movie.model.CountState

class CounterPresenter(
    val view: CounterContract.View
) : CounterContract.Presenter {

    private var _countNumber: CountState = CountState.of(1)
        set(value) {
            field = value
            view.setCountNumber(value)
        }
    override val countNumber: CountState
        get() = CountState.of(_countNumber.value)

    override fun minus() {
        if (_countNumber.value == 1) {
            view.showLimitMin()
            return
        }
        _countNumber -= 1
    }

    override fun plus() {
        if (_countNumber.value == 20) {
            view.showLimitMax()
            return
        }
        _countNumber += 1
    }

    override fun setCountState(countState: CountState) {
        _countNumber = countState
    }
}
