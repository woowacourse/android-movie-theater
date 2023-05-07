package woowacourse.movie.fragment.setting

import woowacourse.movie.DataRepository

class SettingPresenter(
    private val view: SettingContract.View,
    private val sharedPreferenceRepository: DataRepository,
) : SettingContract.Presenter {

    override fun saveCanPushAlarmSwitchData(isChecked: Boolean) {
        sharedPreferenceRepository.setBooleanValue(isChecked)
    }

    override fun loadCanPushAlarmSwitchData(default: Boolean) {
        view.updateCanPushSwitch(sharedPreferenceRepository.getBooleanValue(default))
    }

    override fun onClickCanPushSwitch(
        isPermissionDenied: Boolean,
        isForeverDeniedPermission: Boolean,
        isChecked: Boolean
    ) {
        saveCanPushAlarmSwitchData(isChecked)
        if(!isChecked) return
        if (isPermissionDenied) {
            if (isForeverDeniedPermission) {
                view.disableCanPushSwitch()
            }
            view.reRequestPermission(isChecked)
        }
    }
}
