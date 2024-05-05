package woowacourse.movie.list.contract

import androidx.fragment.app.Fragment

interface HomeContract {
    interface View {
        fun showFragment(fragment: Fragment)
    }

    interface Presenter {
        fun setBottomNavFragment(itemId: Int)
    }
}
