package woowacourse.movie.view.setting

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.view.dialog.DialogFactory
import woowacourse.movie.view.dialog.DialogInfo

class SettingFragment : Fragment(), SettingContract.View {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val presenter: SettingContract.Presenter by lazy {
        SettingPresenter.provideFactory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    override fun setCheckNotification(isCheck: Boolean) {
        binding.swNotification.isChecked = isCheck
    }

    override fun onResume() {
        super.onResume()
        initBinding()
    }

    private fun initBinding() {
        binding.swNotification.setOnCheckedChangeListener(null)
        presenter.initBinding(hasDeviceAlarmPermission())
        binding.swNotification.setOnCheckedChangeListener { _, isChecked ->
            presenter.notification(
                isChecked,
                hasDeviceAlarmPermission(),
                isNotificationPermissionGranted(),
            )
        }
    }

    override fun showPermissionNotificationDialog() {
        DialogFactory().show(
            DialogInfo(
                requireContext(),
                getString(R.string.need_permission),
                getString(R.string.ask_for_need_notification_permission),
                getString(R.string.agree),
                getString(R.string.cancel),
            ),
        ) {
            val intent =
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = "package:${requireContext().packageName}".toUri()
                }
            startActivity(intent)
        }
    }

    override fun showPermissionAlarmDialog() {
        DialogFactory().show(
            DialogInfo(
                requireContext(),
                this.getString(R.string.need_permission),
                this.getString(R.string.ask_for_need_alarm_permission),
                this.getString(R.string.agree),
                this.getString(R.string.cancel),
            ),
        ) {
            requestDeviceAlarmPermission(requireContext())
        }
    }

    private fun hasDeviceAlarmPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val alarmManager = requireContext().getSystemService(AlarmManager::class.java)
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

    private fun isNotificationPermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun requestDeviceAlarmPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            intent.data = "package:${context.packageName}".toUri()

            context.startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
