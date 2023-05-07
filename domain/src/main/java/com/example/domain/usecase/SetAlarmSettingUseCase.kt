package com.example.domain.usecase

import com.example.domain.repository.AlarmSettingRepository

class SetAlarmSettingUseCase(
    private val settingRepository: AlarmSettingRepository
) {
    operator fun invoke(
        alarmSettingInfo: Boolean,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ) {
        kotlin.runCatching {
            settingRepository.setEnablePushNotification(alarmSettingInfo)
        }.onSuccess {
            onSuccess()
        }.onFailure {
            onFailure()
        }
    }
}
