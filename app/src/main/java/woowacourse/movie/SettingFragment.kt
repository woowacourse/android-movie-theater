package woowacourse.movie

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import woowacourse.movie.helper.SettingsPreferenceHelper

class SettingFragment : Fragment() {
    private lateinit var sharedPreference: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val switch = view.findViewById<Switch>(R.id.switch_push)
        switch.isChecked = SettingsPreferenceHelper.isNotificationEnabled(requireContext())

        switch.setOnCheckedChangeListener { _, isChecked ->
            SettingsPreferenceHelper.setNotificationEnabled(requireContext(), isChecked)
        }
    }
}
