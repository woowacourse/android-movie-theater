package woowacourse.movie.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.data.ApplicationSettings
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding get() = requireNotNull(_binding) { "_binding is null" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        binding.applicationSettings = ApplicationSettings
        binding.onNotificationClickListener =
            OnClickListener { view ->
                ApplicationSettings.notificationEnabled = (view as SwitchCompat).isChecked
            }
        return binding.root
    }
}
