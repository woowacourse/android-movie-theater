package woowacourse.movie.view.core.ext

import android.content.Intent
import android.os.Build
import java.io.Serializable

inline fun <reified T : Serializable> Intent.requireSerializable(key: String): T {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(key, T::class.java)
            ?: throw IllegalArgumentException("Serializable '$key' is missing in the Intent.")
    } else {
        @Suppress("DEPRECATION")
        (getSerializableExtra(key) as? T)
            ?: throw IllegalArgumentException("Serializable '$key' is missing in the Intent.")
    }
}
