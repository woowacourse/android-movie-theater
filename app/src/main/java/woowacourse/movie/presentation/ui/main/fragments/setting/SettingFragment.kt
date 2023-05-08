package woowacourse.movie.presentation.ui.main.fragments.setting

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.woowacourse.data.datasource.cache.local.LocalCacheDataSource
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.presentation.base.BaseFragment
import woowacourse.movie.presentation.extensions.checkPermissionTiramisu
import woowacourse.movie.presentation.extensions.createAlertDialog
import woowacourse.movie.presentation.extensions.message
import woowacourse.movie.presentation.extensions.negativeButton
import woowacourse.movie.presentation.extensions.positiveButton
import woowacourse.movie.presentation.extensions.title
import woowacourse.movie.presentation.ui.main.fragments.setting.contract.SettingContract
import woowacourse.movie.presentation.ui.main.fragments.setting.contract.presenter.SettingPresenter

class SettingFragment : BaseFragment<FragmentSettingBinding>(), SettingContract.View {
    override val layoutResId: Int = R.layout.fragment_setting
    override val presenter: SettingContract.Presenter by lazy {
        SettingPresenter(
            view = this,
            cacheDataSource = LocalCacheDataSource.getInstance(requireContext())
        )
    }

    private val settingScreenLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val isPushPermissionGranted = requireContext()
                .checkPermissionTiramisu(Manifest.permission.POST_NOTIFICATIONS)
            presenter.updatePushAllow(isPushPermissionGranted)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.presenter = presenter
    }

    override fun changePushSwitchState(newState: Boolean) {
        binding.notificationPushSwitch.isChecked = newState
    }

    override fun checkPushPermission(): Boolean =
        requireContext().checkPermissionTiramisu(Manifest.permission.POST_NOTIFICATIONS)

    override fun showPushPermissionDialog() {
        requireContext().createAlertDialog {
            title(getString(R.string.permission_request_dialog_title))
            message(getString(R.string.permission_request_dialog_desc))
            positiveButton { launchSettingScreen() }
            negativeButton { presenter.updatePushAllow(false) }
        }.show()
    }

    private fun launchSettingScreen() {
        val appDetailsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:${requireContext().packageName}")
        ).addCategory(Intent.CATEGORY_DEFAULT)
        settingScreenLauncher.launch(appDetailsIntent)
    }

    companion object {
        internal const val TAG = "SettingFragment"
    }
}
