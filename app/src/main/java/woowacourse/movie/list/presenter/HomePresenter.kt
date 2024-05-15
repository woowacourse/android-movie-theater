package woowacourse.movie.list.presenter

import woowacourse.movie.database.NotificationSharedPreference
import woowacourse.movie.list.contract.HomeContract

class HomePresenter(
    val view: HomeContract.View,
    private val sharedPreference: NotificationSharedPreference,
) : HomeContract.Presenter {
    override fun saveInSharedPreference(isGranted: Boolean) {
        sharedPreference.save(isGranted)
    }
}
