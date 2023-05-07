package woowacourse.movie.presentation.view.main.setting

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment(), SettingContract.View {
    private lateinit var binding: FragmentSettingBinding
    private val presenter: SettingPresenter by lazy {
        SettingPresenter(
            view = this,
            context = requireContext()
        )
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        presenter.setAlarmSetting(isGranted)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getAlarmSettingInfo()
    }

    override fun updatePermissionNotGrantedView() {
        binding.switchSettingAlarm.isSelected = false
    }

    override fun updateAlarmSettingView(isSet: Boolean, isGranted: Boolean) {
        setAlarmView(isSet, isGranted)
    }

    private fun setAlarmView(allowedPushNotification: Boolean, isGranted: Boolean) {
        binding.switchSettingAlarm.isChecked =
            allowedPushNotification && isGranted
        binding.switchSettingAlarm.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked && isGranted.not()) {
                requestNotificationPermission()
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
