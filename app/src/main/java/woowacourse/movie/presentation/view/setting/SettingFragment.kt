package woowacourse.movie.presentation.view.setting

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.utils.versionTiramisuOrHigher

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        checkNotificationStatus()
        setupNotificationSwitchListener()
    }

    private fun checkNotificationStatus() {
        if (versionTiramisuOrHigher()) {
            val isGranted =
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED
            binding.switchSettingAlarm.isChecked = isGranted
        }
    }

    private fun setupNotificationSwitchListener() {
        binding.switchSettingAlarm.setOnClickListener {
            showNotificationGrantedDialog(getString(R.string.change_notification_permission))
        }
    }

    private fun showNotificationGrantedDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.notification_title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.confirmation_text)) { dialog, _ ->
                dialog.dismiss()
                navigateToNotificationSetting()
            }
            .setNegativeButton(getString(R.string.cancel_text)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun navigateToNotificationSetting() {
        val intent =
            Intent().apply {
                setAction("android.settings.APP_NOTIFICATION_SETTINGS")
                putExtra("android.provider.extra.APP_PACKAGE", activity?.packageName)
            }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
