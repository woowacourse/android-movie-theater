package woowacourse.movie.presenter

import woowacourse.movie.contract.MainContract

class MainPresenter(val view: MainContract.View) : MainContract.Presenter {
    override fun onClickBottomNavigationItem(itemId: Int): Boolean {
        view.changeFragmentByItemID(itemId)
        return true
    }

    override fun updateFragmentView() {
        val itemId = view.getSavedNavigationItemId()
        view.setSelectedFragmentView(itemId)
    }
}
