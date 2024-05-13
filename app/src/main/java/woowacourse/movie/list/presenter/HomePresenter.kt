package woowacourse.movie.list.presenter

import android.content.Context
import woowacourse.movie.database.NotificationSharedPreference
import woowacourse.movie.list.contract.HomeContract

class HomePresenter(
    val view: HomeContract.View
) : HomeContract.Presenter {
    override fun saveInSharedPreference(isGranted: Boolean) {
        val sharedPreference = NotificationSharedPreference(view as Context)
        sharedPreference.save(isGranted)
    }
}
