package com.woowacourse.data.cache

interface CacheDataStore {
    fun getBoolean(key: String, defValue: Boolean): Boolean
    fun setBoolean(key: String, value: Boolean)
}
