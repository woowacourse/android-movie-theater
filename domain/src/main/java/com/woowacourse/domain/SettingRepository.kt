package com.woowacourse.domain

interface SettingRepository {
    fun getValue(key: String, default: Boolean = false): Boolean
    fun setValue(key: String, value: Boolean)
}
