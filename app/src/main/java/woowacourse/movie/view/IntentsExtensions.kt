package woowacourse.movie.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable

inline fun <reified T : Parcelable> Intent.getParcelableExtraCompat(key: String): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key, T::class.java)
            ?: error(ERROR_NO_DATA.format(key))
    } else {
        @Suppress("DEPRECATION")
        getParcelableExtra(key)
            as? T ?: error(
            ERROR_NO_DATA.format(key),
        )
    }

inline fun <reified T : Parcelable> Bundle.compatParcelable(key: String): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelable(key)
    }

const val ERROR_NO_DATA = "%s 데이터를 찾을 수 없습니다."
