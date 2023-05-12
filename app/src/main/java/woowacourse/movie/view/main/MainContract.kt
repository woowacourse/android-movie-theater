package woowacourse.movie.view.main

import woowacourse.movie.model.FragmentType

interface MainContract {
    interface View {
        var presenter: Present

        fun showFragment(type: FragmentType)
        fun addMovieListFragment(type: FragmentType)
        fun addReservationListFragment(type: FragmentType)
        fun addSettingFragment(type: FragmentType)
    }

    interface Present {
        fun createFragment(type: FragmentType)
        fun setFragment(type: FragmentType)
    }
}
