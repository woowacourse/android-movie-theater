package woowacourse.movie.view.setting

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        val prefs = requireContext().getSharedPreferences("setting", Context.MODE_PRIVATE)
        val isPushEnabled = prefs.getBoolean("push_enabled", false)
        binding.alarmSwitch.isChecked = isPushEnabled

        binding.alarmSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit { putBoolean("push_enabled", isChecked) }
        }
        return binding.root
    }
}
