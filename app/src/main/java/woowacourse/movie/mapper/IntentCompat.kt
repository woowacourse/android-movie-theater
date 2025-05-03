package woowacourse.movie.mapper

import android.content.Intent
import android.os.Build
import android.os.Parcelable

object IntentCompat {
    private const val ERROR_NO_EXTRA_DATA = "[Key : %s] 부가 데이터를 찾을 수 없습니다"

    @Suppress("DEPRECATION")
    fun <T : Parcelable> getParcelableExtra(
        intent: Intent,
        key: String,
        clazz: Class<T>,
    ): T {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(key, clazz) ?: throw IllegalArgumentException(ERROR_NO_EXTRA_DATA.format(key))
        } else {
            intent.getParcelableExtra(key) as T? ?: throw IllegalArgumentException(ERROR_NO_EXTRA_DATA.format(key))
        }
    }
}
