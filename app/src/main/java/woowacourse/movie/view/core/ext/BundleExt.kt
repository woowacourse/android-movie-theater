package woowacourse.movie.view.core.ext

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

inline fun <reified T : Parcelable> Bundle.requireParcelableArrayList(key: String): ArrayList<T> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableArrayList(key, T::class.java)
            ?: throw IllegalArgumentException("ParcelableArrayList '$key' is missing in the Bundle.")
    } else {
        @Suppress("DEPRECATION")
        getParcelableArrayList(key)
            ?: throw IllegalArgumentException("ParcelableArrayList '$key' is missing in the Bundle.")
    }
}

inline fun <reified T : Parcelable> Bundle.requireParcelable(key: String): T {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, T::class.java)
            ?: throw IllegalArgumentException("Parcelable '$key' is missing in the Bundle.")
    } else {
        @Suppress("DEPRECATION")
        (getParcelable(key) as? T)
            ?: throw IllegalArgumentException("Parcelable'$key' is missing in the Bundle.")
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Serializable> Bundle.getSerializableArrayList(key: String): ArrayList<T> {
    return (
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getSerializable(key, ArrayList::class.java)
        } else {
            @Suppress("DEPRECATION")
            getSerializable(key)
        }
    ) as ArrayList<T>
}
