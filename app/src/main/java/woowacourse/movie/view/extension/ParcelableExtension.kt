package woowacourse.movie.view.extension

import android.content.Intent
import android.os.Build
import android.os.Bundle

inline fun <reified T> Intent.getParcelableCompatList(key: String): T =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getParcelableExtra(key, T::class.java) ?: throw IllegalArgumentException(ERR_INVALID_ARGUMENT + T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelableExtra(key) as? T ?: throw IllegalArgumentException(ERR_INVALID_ARGUMENT + T::class.java)
    }

inline fun <reified T> Bundle.getParcelableCompatList(key: String): List<T> =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableArray(key, T::class.java)?.toList() ?: throw IllegalArgumentException(ERR_INVALID_ARGUMENT + T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelableArray(key)?.toList() as? List<T> ?: throw IllegalArgumentException(ERR_INVALID_ARGUMENT + T::class.java)
    }

inline fun <reified T> Bundle.getParcelableCompat(key: String): T =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getParcelable(key, T::class.java) ?: throw IllegalArgumentException(ERR_INVALID_ARGUMENT + T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelable(key) as? T ?: throw IllegalArgumentException(ERR_INVALID_ARGUMENT + T::class.java)
    }

const val ERR_INVALID_ARGUMENT = "객체를 가져올 수 없습니다 "
