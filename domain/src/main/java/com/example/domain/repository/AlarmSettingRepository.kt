package com.example.domain.repository

interface AlarmSettingRepository {
    fun getEnablePushNotification(): Boolean
    fun setEnablePushNotification(value: Boolean)
}
