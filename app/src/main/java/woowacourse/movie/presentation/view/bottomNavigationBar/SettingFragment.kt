package woowacourse.movie.presentation.view.bottomNavigationBar

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.preference.SwitchPreferenceCompat
import woowacourse.movie.R

class SettingFragment : PreferenceFragmentCompat() {
    private var notificationPreference: SwitchPreferenceCompat? = null

    override fun onCreatePreferences(
        savedInstanceState: Bundle?,
        rootKey: String?,
    ) {
        val sharedPreferences = activity?.getSharedPreferences("movie", MODE_PRIVATE)
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val isNotificationEnabled = sharedPreferences?.getBoolean("notifications", false)
        if (rootKey != null) {
            notificationPreference = findPreference("notifications")
        }

        notificationPreference?.apply {
            if (isNotificationEnabled != null) {
                isChecked = isNotificationEnabled
            }
            setOnPreferenceChangeListener { _, newValue ->
                newValue as Boolean

                with(sharedPreferences?.edit()) {
                    this?.putBoolean("notifications", newValue)
                    this?.apply()
                }

                Log.d("Preferences", "Notifications enabled: $newValue")
                true
            }
        }
    }
}
