package woowacourse.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingsBinding
import woowacourse.movie.sharedPreference.SettingSharedPreferenceManager

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val prefs: SettingSharedPreferenceManager by lazy {
        SettingSharedPreferenceManager(
            requireContext(),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.scNotificationEnabled.isChecked = prefs.isNotificationEnabled()

        binding.scNotificationEnabled.setOnCheckedChangeListener { buttonView, isChecked ->
            prefs.updateNotificationEnabled(isChecked)
        }
    }
}
