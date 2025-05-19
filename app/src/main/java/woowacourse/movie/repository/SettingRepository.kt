package woowacourse.movie.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import woowacourse.movie.domain.model.SettingData
import kotlin.concurrent.thread

class SettingRepository(
    private val sharedPreferences: SharedPreferences,
) {
    fun findAll(callback: (Result<List<SettingData>>) -> Unit) {
        thread {
            runCatching {
                sharedPreferences.getBoolean(SettingData.NOTIFICATION_KEY, false)
            }.onSuccess {
                callback(Result.success(listOf(SettingData(SettingData.NOTIFICATION_KEY, it))))
            }.onFailure {
                callback(Result.failure(it))
            }
        }
    }

    fun save(value: SettingData): Result<Unit> {
        sharedPreferences.edit { putBoolean(value.key, value.value) }
        return Result.success(Unit)
    }
}
