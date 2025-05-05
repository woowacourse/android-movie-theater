package woowacourse.movie.view.extension

import android.content.Intent
import android.os.Build
import android.os.Bundle

inline fun <reified T> Intent.getParcelableCompat(key: String): T =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getParcelableExtra(key, T::class.java) ?: throw IllegalArgumentException(ERR_INVALID_ARGUMENT)
    } else {
        @Suppress("DEPRECATION")
        getParcelableExtra(key) as? T ?: throw IllegalArgumentException(ERR_INVALID_ARGUMENT)
    }

inline fun <reified T> Bundle.getParcelableCompat(key: String): List<T> =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableArray(key, T::class.java)?.toList() ?: throw IllegalArgumentException(ERR_INVALID_ARGUMENT)
    } else {
        @Suppress("DEPRECATION")
        getParcelableArray(key)?.toList() as? List<T> ?: throw IllegalArgumentException(ERR_INVALID_ARGUMENT)
    }

const val ERR_INVALID_ARGUMENT = "객체를 가져올 수 없습니다"
