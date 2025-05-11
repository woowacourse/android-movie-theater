package woowacourse.movie.view.setting

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.ApplicationSettings
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.view.ApplicationSettingProvider

class SettingFragment : Fragment() {
    private lateinit var applicationSettings: ApplicationSettings

    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding get() = requireNotNull(_binding) { "_binding is null" }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        applicationSettings = (activity as ApplicationSettingProvider).provideApplicationSetting()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingNotificationSwitch.isChecked = applicationSettings.notificationEnabled

        binding.settingNotificationSwitch.setOnCheckedChangeListener { view, checked ->
            applicationSettings.notificationEnabled = checked
            view.isChecked = applicationSettings.notificationEnabled
        }
    }
}
