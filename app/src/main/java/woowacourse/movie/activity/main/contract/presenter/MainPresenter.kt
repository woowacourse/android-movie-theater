package woowacourse.movie.activity.main.contract.presenter

import woowacourse.movie.activity.main.contract.MainContract
import woowacourse.movie.util.preference.DataPreference

class MainPresenter(
    val view: MainContract.View,
    private val dataPreference: DataPreference,
) : MainContract.Presenter {
    override fun saveSettingData(data: Boolean) {
        dataPreference.saveData(data)
    }
}
