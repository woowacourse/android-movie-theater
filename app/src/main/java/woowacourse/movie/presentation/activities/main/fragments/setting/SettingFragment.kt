package woowacourse.movie.presentation.activities.main.fragments.setting

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import com.woowacourse.data.datasource.cache.local.LocalCacheDataSource
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.main.fragments.setting.contract.SettingContract
import woowacourse.movie.presentation.activities.main.fragments.setting.contract.presenter.SettingPresenter
import woowacourse.movie.presentation.extensions.checkPermissionTiramisu
import woowacourse.movie.presentation.extensions.createAlertDialog
import woowacourse.movie.presentation.extensions.message
import woowacourse.movie.presentation.extensions.negativeButton
import woowacourse.movie.presentation.extensions.positiveButton
import woowacourse.movie.presentation.extensions.title

class SettingFragment : Fragment(R.layout.fragment_setting), SettingContract.View {
    override val presenter: SettingContract.Presenter by lazy {
        SettingPresenter(
            view = this,
            cacheDataSource = LocalCacheDataSource.getInstance(requireContext()),
        )
    }

    private val pushSwitch: SwitchMaterial by lazy { requireView().findViewById(R.id.notification_push_switch) }

    private val settingScreenLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val isPushPermissionGranted =
                requireContext().checkPermissionTiramisu(Manifest.permission.POST_NOTIFICATIONS)
            presenter.updatePushAllow(isPushPermissionGranted)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPushSwitch()
    }

    private fun initPushSwitch() {
        presenter.fetchPushSwitchState()
        pushSwitch.setOnCheckedChangeListener { _, newState ->
            presenter.onPushSwitchClicked(newState)
        }
    }

    override fun changePushSwitchState(newState: Boolean) {
        pushSwitch.isChecked = newState
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
