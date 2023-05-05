package woowacourse.movie.feature.detail.counter

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.CountState

internal class CounterPresenterTest {
    private lateinit var view: CounterContract.View
    private lateinit var presenter: CounterContract.Presenter

    @Before
    fun init() {
        view = mockk()
        presenter = CounterPresenter(view)
    }

    @Test
    fun 카운터값이_1보다_클때_마이너스_버튼을_클릭하면_카운터값이_하나_내려간다() {
        every { view.showLimitMin() } just Runs
        every { view.setCountNumber(any()) } just Runs
        presenter.setCountState(CountState.of(2))

        // 실행
        presenter.minus()

        verify(exactly = 0) { view.showLimitMin() } // 실행안됨.
        val expected = 1
        assert(presenter.countNumber.value == expected)
    }

    @Test
    fun 카운터값이_1일때_마이너스_버튼을_클릭하면_카운터값이_내려가지_않고_안내_문구가_뜬다() {
        every { view.showLimitMin() } just Runs
        every { view.setCountNumber(any()) } just Runs
        presenter.setCountState(CountState.of(1))

        // 실행
        presenter.minus()

        verify { view.showLimitMin() }
        val expected = 1
        assert(presenter.countNumber.value == expected)
    }

    @Test
    fun 카운터값이_20보다_작을때_플러스_버튼을_클릭하면_카운터값이_올라간다() {
        every { view.showLimitMax() } just Runs
        every { view.setCountNumber(any()) } just Runs
        presenter.setCountState(CountState.of(19))

        // 실행
        presenter.plus()

        verify(exactly = 0) { view.showLimitMax() }
        val expected = 20
        assert(presenter.countNumber.value == expected)
    }

    @Test
    fun 카운터값이_20일때_플러스_버튼을_클릭하면_카운터값이_올라가지_않고_안내_문구가_뜬다() {
        every { view.showLimitMax() } just Runs
        every { view.setCountNumber(any()) } just Runs
        presenter.setCountState(CountState.of(20))

        // 실행
        presenter.plus()

        verify { view.showLimitMax() }
        val expected = 20
        assert(presenter.countNumber.value == expected)
    }
}
