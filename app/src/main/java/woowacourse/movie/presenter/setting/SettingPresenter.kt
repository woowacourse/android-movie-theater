package woowacourse.movie.presenter.setting

import android.util.Log

class SettingPresenter(
    private val view: SettingContract.View,
) : SettingContract.Presenter {
    override fun loadSavedSetting(isPushSetting: Boolean) {
        Log.d("isPushing",isPushSetting.toString())
        view.showSavedSetting(isPushSetting)
    }

}
