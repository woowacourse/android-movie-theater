package woowacourse.movie.presentation.activities.main.fragments.setting

class SettingPresenter(
    private val view: SettingContract.View,
    private val alarmRepository: AlarmRepository,
) : SettingContract.Presenter {
    override fun setPushAllow(value: Boolean) {
        alarmRepository.setBoolean(value)
    }

    override fun getPushAllow(defaultValue: Boolean): Boolean {
        return alarmRepository.getBoolean(defaultValue)
    }

    override fun onSwitchChanged(isPermittedPushPermission: Boolean, isAllowed: Boolean) {
        if (isAllowed && !isPermittedPushPermission) {
            view.showPushPermissionDialog()
        } else {
            setPushAllow(isAllowed)
        }
    }
}
