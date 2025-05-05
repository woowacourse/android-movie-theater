package woowacourse.movie.util

import android.content.Intent
import android.os.Build
import android.os.Bundle
import java.io.Serializable

fun <T : Serializable> Bundle.getSerializableCompat(
    key: String,
    clazz: Class<T>,
): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializable(key, clazz)
    } else {
        getSerializable(key) as? T
    }
}

fun <T : Serializable> Intent.getSerializableExtraCompat(
    key: String,
    clazz: Class<T>,
): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(key, clazz)
    } else {
        getSerializableExtra(key) as? T
    }
}
