package woowacourse.movie.presentation.view.setting

import android.content.Context

class PushPreferenceHelper(
    context: Context,
) {
    private val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun isPushEnabled(): Boolean = prefs.getBoolean(KEY_PUSH_ENABLED, true)

    companion object {
        internal const val PREF_NAME = "push_pref"
        private const val KEY_PUSH_ENABLED = "push_enabled"
    }
}
