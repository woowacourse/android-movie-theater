package woowacourse.movie.view.setting

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment :
    Fragment(R.layout.fragment_setting),
    SettingContract.View,
    SettingEventHandler {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: SettingContract.Presenter
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (!isGranted) showNotificationPermissionToast()
                presenter.setNotificationSetting(isGranted)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SettingPresenter(this, SettingStorageManagerImpl(requireContext()))
        presenter.loadSettings()
        binding.handler = this
    }

    override fun isNotificationPermitted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionStatus =
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS,
                )
            permissionStatus == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    override fun showNotificationSetting(enabled: Boolean) {
        binding.notificationEnabled = enabled
    }

    override fun onNotificationSettingChangeAttempted() {
        presenter.toggleNotificationSetting()
    }

    override fun attemptNotificationSettingChange(enabled: Boolean) {
        if (!enabled) {
            presenter.setNotificationSetting(false)
        } else {
            attemptNotificationPermissionRequest()
        }
    }

    private fun attemptNotificationPermissionRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                showNotificationPermissionDialog(
                    onPositive = { requestNotificationPermission() },
                    onNegative = { presenter.setNotificationSetting(false) },
                )
            } else {
                requestNotificationPermission()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    private fun showNotificationPermissionToast() {
        Toast.makeText(
            requireContext(),
            getString(R.string.text_notification_permission_required),
            Toast.LENGTH_LONG,
        ).show()
    }

    private fun showNotificationPermissionDialog(
        onPositive: () -> Unit,
        onNegative: () -> Unit,
    ) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.text_notification_permission_title)
            setMessage(R.string.text_notification_permission_required)
            setCancelable(false)
            setPositiveButton(R.string.text_permission_dialog_positive_button) { _, _ ->
                onPositive()
            }
            setNegativeButton(R.string.text_permission_dialog_negative_button) { dialog, _ ->
                dialog.dismiss()
                onNegative()
            }
        }.show()
    }
}
