package woowacourse.movie.view.setting

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.view.core.ext.checkNotificationPermission

class SettingFragment : Fragment(), SettingContract.View {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: SettingContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        presenter = SettingPresenter.initialize(this@SettingFragment, requireContext())
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun showNotificationPermission(isGranted: Boolean) {
        toggleNotificationPermissionSwitch(isGranted)
    }

    private val switchClickListener =
        CompoundButton.OnCheckedChangeListener { _, isChecked ->
            val isGranted = requireContext().checkNotificationPermission()

            if ((isChecked && !isGranted) || (!isChecked && isGranted)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (isChecked) {
                        // 권한이 거부 되었을 때
                        showNotificationPermissionGuideDialog(R.string.permission_notification_guide_title)
                    } else {
                        // 권한이 허용 되었을 때
                        showNotificationPermissionGuideDialog(R.string.permission_cannot_change_permission)
                    }
                } else {
                    showNotificationPermissionGuideDialog(R.string.permission_notification_guide_title)
                }

                // 실제 권한 상태로 Switch 되돌리기
                toggleNotificationPermissionSwitch(isGranted)
            } else {
                presenter.setPreferences(isGranted)
            }
        }

    private fun initView() {
        binding.switchNotification.setOnCheckedChangeListener(switchClickListener)
    }

    private fun showNotificationPermissionGuideDialog(
        @StringRes title: Int,
    ) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(R.string.permission_notification_guide_message)
            .setPositiveButton(R.string.permission_notification_move_to_setting) { _, _ ->
                val intent =
                    Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                        putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
                    }
                startActivity(intent)
            }
            .setNegativeButton(R.string.permission_notification_cancel_setting, null)
            .show()
    }

    private fun toggleNotificationPermissionSwitch(isGranted: Boolean) {
        with(binding) {
            switchNotification.setOnCheckedChangeListener(null)
            switchNotification.isChecked = isGranted
            switchNotification.setOnCheckedChangeListener(switchClickListener)
        }
    }

    override fun onResume() {
        super.onResume()
        val isGranted = requireContext().checkNotificationPermission()
        toggleNotificationPermissionSwitch(isGranted)
        presenter.synchronizePermission(isGranted)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    )
}
