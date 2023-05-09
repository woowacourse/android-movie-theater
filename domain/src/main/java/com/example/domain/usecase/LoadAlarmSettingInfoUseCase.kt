package com.example.domain.usecase

import com.example.domain.repository.AlarmSettingRepository

class LoadAlarmSettingInfoUseCase(
    private val settingRepository: AlarmSettingRepository
) {
    operator fun invoke(onSuccess: (Boolean) -> Unit, onFailure: () -> Unit) {
        kotlin.runCatching { settingRepository.getEnablePushNotification() }
            .onSuccess { onSuccess(it) }
            .onFailure { onFailure() }
    }
}
