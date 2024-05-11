package woowacourse.movie.presentation.ui.main.setting

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import woowacourse.movie.R
import woowacourse.movie.data.repository.local.PreferenceRepositoryImpl
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.presentation.base.BaseMvpBindingFragment
import woowacourse.movie.presentation.permision.Permission
import woowacourse.movie.presentation.permision.Permission.toPermissionText
import woowacourse.movie.presentation.ui.main.setting.SettingContract.View

class SettingFragment : BaseMvpBindingFragment<FragmentSettingBinding>(), View {
    override val layoutResourceId: Int
        get() = R.layout.fragment_setting

    private val presenter by lazy { SettingPresenter(this, PreferenceRepositoryImpl()) }

    override fun initStartView() {
        binding.presenter = presenter
        presenter.loadNotificationMode()
    }

    override fun showNotificationMode(mode: Boolean) {
        requireActivity().runOnUiThread {
            binding.mode = mode
        }
    }

    override fun checkNotificationPermissions(mode: Boolean) {
        requestNotificationPermissions(mode, Permission.notificationPermissions.toList())
    }

    private fun requestNotificationPermissions(
        mode: Boolean,
        permissionList: List<String>,
    ) {
        val requestList = ArrayList<String>()
        for (permission in permissionList) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    permission,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestList.add(permission)
            }
            if (requestList.isNotEmpty()) {
                requestMultiplePermissionsLauncher.launch(requestList.toTypedArray())
                return
            }
        }
        presenter.saveNotificationMode(mode)
    }

    private val requestMultiplePermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.values.all { it }) {
                presenter.loadNotificationMode()
            } else {
                presenter.loadNotificationMode()
                val stringResId =
                    permissions.entries.find { !it.value }?.key?.toPermissionText()
                        ?: R.string.permission_default_text

                showPermissionSnackBar(stringResId)
            }
            return@registerForActivityResult
        }

    private fun showPermissionSnackBar(
        @StringRes stringResId: Int,
    ) {
        presenter.requestNotificationPermission(getString(stringResId)) {
            setAction(getString(R.string.permission_done)) {
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", requireContext().packageName, null)
                    }
                startActivity(intent)
            }
        }
    }

    companion object {
        const val TAG = "SETTING_FRAGMENT"
    }
}
