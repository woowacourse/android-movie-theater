package woowacourse.movie.feature.detail.counter

import woowacourse.movie.model.CountState

interface CounterContract {
    interface View {
        fun setCountNumber(count: CountState)
        fun showLimitMin()
        fun showLimitMax()
    }

    interface Presenter {
        val countNumber: CountState
        fun minus()
        fun plus()
        fun setCountState(countState: CountState)
    }
}
