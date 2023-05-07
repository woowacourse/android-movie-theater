package com.example.domain.usecase

import com.example.domain.repository.AlarmSettingRepository

class LoadAlarmSettingInfoUseCase(
    private val settingRepository: AlarmSettingRepository
) {
    operator fun invoke(onSuccess: (Boolean) -> Unit, onFailure: () -> Unit) {
        var alarmSettingInfo: Boolean = false
        kotlin.runCatching { alarmSettingInfo = settingRepository.getEnablePushNotification() }
            .onSuccess { onSuccess(alarmSettingInfo) }
            .onFailure { onFailure() }
    }
}
