package woowacourse.movie.common.util

import android.content.Intent
import android.os.Build
import android.os.Bundle
import java.io.Serializable

@Suppress("UNCHECKED_CAST", "DEPRECATION")
fun <T : Serializable> Intent.getSerializableExtraCompat(
    key: String,
    clazz: Class<T>,
): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSerializableExtra(key, clazz)
    } else {
        this.getSerializableExtra(key) as? T
    }

@Suppress("UNCHECKED_CAST", "DEPRECATION")
fun <T : Serializable> Bundle.getSerializableCompat(
    key: String,
    clazz: Class<T>,
): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSerializable(key, clazz)
    } else {
        this.getSerializable(key) as? T
    }
