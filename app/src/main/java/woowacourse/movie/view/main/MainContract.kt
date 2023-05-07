package woowacourse.movie.view.main

import androidx.fragment.app.Fragment
import woowacourse.movie.model.FragmentType

interface MainContract {
    interface View {
        var presenter: Present

        fun showFragment(type: FragmentType)
    }

    interface Present {
        fun createFragment(type: FragmentType): Fragment
        fun setFragment(type: FragmentType)
    }
}
