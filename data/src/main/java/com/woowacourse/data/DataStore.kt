package com.woowacourse.data

interface DataStore {
    fun getBoolean(key: String, defValue: Boolean): Boolean
    fun setBoolean(key: String, value: Boolean)
}
