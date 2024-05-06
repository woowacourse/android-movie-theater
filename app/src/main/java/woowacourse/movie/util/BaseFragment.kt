package woowacourse.movie.util

import androidx.fragment.app.Fragment

abstract class BaseFragment<P : BasePresenter> : Fragment() {
    val presenter: P by lazy { initializePresenter() }

    abstract fun initializePresenter(): P
}
