package com.woowacourse.data.datasource.cache

interface CacheDataSource {
    fun getBoolean(key: String, defValue: Boolean): Boolean
    fun setBoolean(key: String, value: Boolean)
}
