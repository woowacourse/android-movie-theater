package woowacourse.movie.presentation.view.bottomNavigationBar

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import woowacourse.movie.R
import woowacourse.movie.application.MovieApp
import woowacourse.movie.sharedpreference.SharedPreferences

class SettingFragment : PreferenceFragmentCompat() {
    private val sharedPref = MovieApp.prefs
    private var notificationPreference: SwitchPreferenceCompat? = null

    override fun onCreatePreferences(
        savedInstanceState: Bundle?,
        rootKey: String?,
    ) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        if (rootKey != null) {
            notificationPreference = findPreference(SharedPreferences.KEY_NOTIFICATION)
        }

        val isNotificationEnabled = sharedPref.getNotificationPreference()
        setUpNotificationPref(isNotificationEnabled)
    }

    private fun setUpNotificationPref(isNotificationEnabled: Boolean) {
        notificationPreference?.apply {
            isChecked = isNotificationEnabled
            setOnPreferenceChangeListener { _, newValue ->
                newValue as Boolean

                sharedPref.setNotificationPreference(newValue)
                true
            }
        }
    }
}
