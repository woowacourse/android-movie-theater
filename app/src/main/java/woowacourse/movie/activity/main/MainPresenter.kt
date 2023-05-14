package woowacourse.movie.activity.main

import woowacourse.movie.DataRepository

class MainPresenter(
    private val view: MainContract.View,
    private val dataRepository: DataRepository,
) : MainContract.Presenter {

    override fun saveSwitchData(isChecked: Boolean) {
        dataRepository.setBooleanValue(isChecked)
        view.updatePushAlarmSwitch(isChecked)
    }
}
