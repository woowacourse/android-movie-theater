package woowacourse.movie.presentation.main

import androidx.fragment.app.Fragment

interface MainContract {
    interface View {
        fun showFragment(fragment: Fragment)
    }

    interface Presenter {
        fun onBottomNavItemSelected(itemId: Int)
    }
}
