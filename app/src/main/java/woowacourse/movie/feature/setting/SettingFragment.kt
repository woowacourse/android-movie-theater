package woowacourse.movie.feature.setting

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

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
        checkPushNotification()
        showNotificationGrantedDialog()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkPushNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isGranted =
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED
            binding.switchSettingNotification.isChecked = isGranted
        }
    }

    private fun showNotificationGrantedDialog() {
        binding.switchSettingNotification.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                makeNotificationGrantedDialog(getString(R.string.setting_notification_permission_dialog_message))
            } else {
                makeNotificationGrantedDialog(getString(R.string.setting_notification_rejection_dialog_message))
            }
        }
    }

    private fun makeNotificationGrantedDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.setting_notification_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.all_confirm)) { dialog, _ ->
                dialog.dismiss()
                navigateToNotificationSetting()
            }
            .setNegativeButton(getString(R.string.seat_selection_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun navigateToNotificationSetting() {
        val intent =
            Intent()
                .setAction("android.settings.APP_NOTIFICATION_SETTINGS")
                .putExtra("android.provider.extra.APP_PACKAGE", activity?.packageName)
        startActivity(intent)
    }
}
