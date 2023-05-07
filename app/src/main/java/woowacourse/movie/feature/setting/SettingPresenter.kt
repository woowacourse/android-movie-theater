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
            onFailure = { }
        )
    }

    override fun changeSwitchCheckedState(isPermissionApprove: Boolean, isSwitchChecked: Boolean) {
        if (!isPermissionApprove) view.alarmPermissionIsNotApprove()
        setAlarmSettingUseCase(isSwitchChecked, {}, {
            // 문제 발생. 토스트 띄워야 함.
        })
    }
}
