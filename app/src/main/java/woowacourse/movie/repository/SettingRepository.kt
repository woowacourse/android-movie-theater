package woowacourse.movie.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import woowacourse.movie.domain.model.SettingData

class SettingRepository(
    private val sharedPreferences: SharedPreferences,
) : Repository<SettingData> {
    override fun findAll(): Result<List<SettingData>> {
        val result = sharedPreferences.getBoolean(SettingData.NOTIFICATION_KEY, false)
        return Result.success(listOf(SettingData(SettingData.NOTIFICATION_KEY, result)))
    }

    override fun save(value: SettingData): Result<Unit> {
        sharedPreferences.edit { putBoolean(value.key, value.value) }
        return Result.success(Unit)
    }
}
