package woowacourse.movie.presentation.view.main.setting

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val allowedPushNotification = sharedPref?.getBoolean(getString(R.string.push_alarm_permission), false) ?: false
        binding.switchSettingAlarm.isChecked = allowedPushNotification
        binding.switchSettingAlarm.setOnCheckedChangeListener { _, isChecked ->
            sharedPref?.edit()?.let {
                it.putBoolean(getString(R.string.push_alarm_permission), isChecked)
                it.apply()
            }
        }

        return binding.root
    }
}
