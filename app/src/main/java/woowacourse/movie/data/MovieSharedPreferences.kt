package woowacourse.movie.data

import android.content.Context
import androidx.core.content.edit
import kotlin.concurrent.Volatile

class MovieSharedPreferences private constructor(context: Context) {
    private val sharedPreferences by lazy {
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun setBoolean(
        key: String,
        value: Boolean,
    ) {
        sharedPreferences.edit {
            putBoolean(key, value)
            apply()
        }
    }

    fun getBoolean(
        key: String,
        defaultValue: Boolean,
    ): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    companion object {
        private const val PREFERENCES_NAME = "shared_preferences"

        @Volatile
        private var instance: MovieSharedPreferences? = null

        fun instance(context: Context): MovieSharedPreferences {
            return instance ?: run {
                synchronized(this) {
                    val newInstance = MovieSharedPreferences(context)
                    instance = newInstance
                    newInstance
                }
            }
        }
    }
}
