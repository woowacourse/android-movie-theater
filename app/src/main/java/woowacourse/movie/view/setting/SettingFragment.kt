package woowacourse.movie.view.setting

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import woowacourse.movie.Provider
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.view.base.BaseFragment
import woowacourse.movie.view.receiver.NotificationReceiver

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting), SettingContract.View {
    private val presenter: SettingContract.Presenter by lazy {
        Provider.settingPresenter(this)
    }
    private val requestPermissionLauncher =
        registerForActivityResult(
            RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                presenter.setNotification()
            }
        }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setPermissionSwitch()
    }

    override fun setNotification(tickets: List<Ticket>) {
        tickets.forEach {
            NotificationReceiver.setNotification(requireContext(), it)
        }
    }

    override fun setPermissionSwitch() {
        if (hasAllPermissions() && NotificationReceiver.isEnabled) {
            binding.switchSettingPushAlarm.isChecked = true
        } else {
            binding.switchSettingPushAlarm.isChecked = false
        }

        binding.switchSettingPushAlarm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (!hasNotificationPermission()) {
                    requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                }
                if (!hasExactAlarmPermission()) {
                    requestExactAlarmPermission()
                }
                presenter.setNotification()
            } else {
                NotificationReceiver.cancelNotification()
            }
        }
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
