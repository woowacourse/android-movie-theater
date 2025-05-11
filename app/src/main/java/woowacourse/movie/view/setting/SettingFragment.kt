package woowacourse.movie.view.setting

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.data.ApplicationSettings
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.view.ApplicationSettingProvider

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding get() = requireNotNull(_binding) { "_binding is null" }

    private lateinit var applicationSettings: ApplicationSettings
    private lateinit var onNotificationClickListener: OnClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        applicationSettings = (activity as ApplicationSettingProvider).provideApplicationSetting()
        onNotificationClickListener =
            OnClickListener { view ->
                view as SwitchCompat
                applicationSettings.notificationEnabled = view.isChecked
            }
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
        binding.applicationSettings = applicationSettings
        binding.onNotificationClickListener = onNotificationClickListener
        return binding.root
    }
}
