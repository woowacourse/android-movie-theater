package woowacourse.movie.view.setting

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import woowacourse.movie.MovieTheaterApplication
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.view.base.BaseFragment
import woowacourse.movie.view.extension.alarmManager
import woowacourse.movie.view.receiver.NotificationReceiver
import java.time.LocalDateTime

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting), SettingContract.View {
    private val presenter: SettingContract.Presenter by lazy {
        (requireActivity().application as MovieTheaterApplication)
            .settingProvider.settingPresenter(this)
    }
    private val requestPermissionLauncher =
        registerForActivityResult(
            RequestPermission(),
        ) { isGranted: Boolean ->
            if (hasAllPermissions()) {
                binding.switchSettingPushAlarm.isChecked = true
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

    override fun setNotification(
        tickets: List<Ticket>,
        showTimes: List<LocalDateTime>,
    ) {
        tickets.forEachIndexed { idx, ticket ->
            NotificationReceiver.setNotification(
                requireContext(),
                NotificationReceiver.pendingIntent(requireContext(), ticket),
                showTimes[idx],
            )
        }
    }

    override fun cancelNotification(tickets: List<Ticket>) {
        val alarmManager = requireContext().alarmManager()
        tickets.forEach {
            alarmManager.cancel(NotificationReceiver.pendingIntent(requireContext(), it))
        }
    }

    override fun setPermissionSwitch(isEnabled: Boolean) {
        binding.hasAllPermission = hasAllPermissions() && isEnabled
        binding.switchSettingPushAlarm.setOnCheckedChangeListener { _, isChecked ->
            presenter.savePushAlarmSetting(isChecked && hasAllPermissions())
            if (isChecked) {
                if (!hasNotificationPermission()) {
                    requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                }
                if (!hasExactAlarmPermission()) {
                    requestExactAlarmPermission()
                }
                if (!hasAllPermissions()) {
                    binding.switchSettingPushAlarm.isChecked = false
                    return@setOnCheckedChangeListener
                }
                presenter.setNotification()
            } else {
                presenter.cancelNotification()
            }
        }
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
