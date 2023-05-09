package woowacourse.movie.view.main

import woowacourse.movie.model.FragmentType

class MainPresenter(private val view: MainContract.View) : MainContract.Present {
    override fun createFragment(type: FragmentType) {
        when (type) {
            FragmentType.RESERVATION_LIST -> view.addReservationListFragment(type)
            FragmentType.HOME -> view.addMovieListFragment(type)
            FragmentType.SETTING -> view.addSettingFragment(type)
        }
    }

    override fun setFragment(type: FragmentType) {
        view.showFragment(type)
    }
}
