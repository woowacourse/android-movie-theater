package woowacourse.movie.view.main

import androidx.fragment.app.Fragment

interface MainContract {
    interface View {
        val presenter: Presenter
        fun changeFragmentByItemID(itemId: Int)
        fun replaceFragment(fragment: Fragment)
        fun setSelectedFragmentView(itemId: Int)
        fun getSavedNavigationItemId(): Int
    }

    interface Presenter {
        fun onClickBottomNavigationItem(itemId: Int): Boolean
        fun updateFragmentView()
    }
}
