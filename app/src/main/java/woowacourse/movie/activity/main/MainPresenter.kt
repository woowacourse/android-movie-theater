package woowacourse.movie.activity.main

import woowacourse.movie.DataRepository

class MainPresenter(
    private val view: MainContract.View,
    private val sharedPreferenceUtil: DataRepository,
) : MainContract.Presenter {

    override fun saveSwitchData(isChecked: Boolean) {
        sharedPreferenceUtil.setBooleanValue(isChecked)
        view.updatePushAlarmSwitch(isChecked)
    }
}
