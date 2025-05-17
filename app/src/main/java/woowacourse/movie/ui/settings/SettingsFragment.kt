package woowacourse.movie.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingsBinding
import woowacourse.movie.providers.StorageProvider

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        binding.isEnablePostNotification = StorageProvider.hasPushNotificationPermission
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.switchPushNotification.setOnClickListener {
            switchToggleStatus()
        }
    }

    private fun switchToggleStatus() {
        val toggledStatus = !StorageProvider.hasPushNotificationPermission
        binding.isEnablePostNotification = toggledStatus
        StorageProvider.setPushNotificationPermissionState(toggledStatus)
    }
}

