package woowacourse.movie.list.view

import androidx.fragment.app.Fragment

interface HomeActivityContract {
    interface View {
        fun showFragment(fragment: Fragment)
    }
    
    interface Presenter {
        fun onBottomNavItemSelected(itemId: Int)
    }
}
