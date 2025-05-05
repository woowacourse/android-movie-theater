package woowacourse.movie.view.extension

import android.content.Intent
import android.os.Build
import android.os.Bundle
import java.io.Serializable

inline fun <reified T : Serializable> Intent.getSerializableExtraData(key: String): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(key, T::class.java)
    } else {
        getSerializableExtra(key) as? T
    }

inline fun <reified T : Serializable> Bundle.getSerializableExtraData(key: String): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializable(key, T::class.java)
    } else {
        getSerializable(key) as? T
    }
