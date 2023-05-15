package woowacourse.movie.feature.setting

import com.example.domain.usecase.LoadAlarmSettingInfoUseCase
import com.example.domain.usecase.SetAlarmSettingUseCase

class SettingPresenter(
    val view: SettingContract.View,
    private val loadAlarmSettingInfoUseCase: LoadAlarmSettingInfoUseCase,
    private val setAlarmSettingUseCase: SetAlarmSettingUseCase
) : SettingContract.Presenter {
    override fun loadAlarmSettingInfo() {
        loadAlarmSettingInfoUseCase(
            onSuccess = { view.setInitAlarmSettingInfo(it) },
            onFailure = { view.errorLoadAlarmInfo() }
        )
    }

    override fun changeSwitchCheckedState(isPermissionApprove: Boolean, isSwitchChecked: Boolean) {
        if (!isPermissionApprove) view.alarmPermissionIsNotApprove()
        setAlarmSettingUseCase(
            isSwitchChecked,
            onSuccess = { },
            onFailure = { view.errorSetAlarmInfo() }
        )
    }
}
