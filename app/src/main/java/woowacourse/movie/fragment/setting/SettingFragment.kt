package woowacourse.movie.fragment.setting

import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.switchmaterial.SwitchMaterial
import woowacourse.movie.BundleKeys.IS_CAN_PUSH_CHECKED
import woowacourse.movie.BundleKeys.REQUEST_NOTIFICATION_PERMISSION
import woowacourse.movie.PermissionManagerImpl
import woowacourse.movie.R
import woowacourse.movie.SharedPreferenceUtil

class SettingFragment(
    activityResultLauncher: ActivityResultLauncher<String>
) : Fragment(), SettingContract.View {

    override lateinit var presenter: SettingContract.Presenter
    private val canPushSwitchView: SwitchMaterial by lazy {
        requireView().findViewById(R.id.sw_setting_can_push)
    }
    private val permissionManager by lazy {
        PermissionManagerImpl(
            requireActivity(),
            activityResultLauncher
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = SettingPresenter(this, SharedPreferenceUtil(requireContext()))
        presenter.loadCanPushAlarmSwitchData(default = false)
        setCanPushSwitchOnClickListener()
    }

    override fun reRequestPermission(isChecked: Boolean) {
        setFragmentResult(
            REQUEST_NOTIFICATION_PERMISSION,
            bundleOf(IS_CAN_PUSH_CHECKED to isChecked)
        )
    }

    override fun synchronizeCanPushSwitch(isChecked: Boolean) {
        canPushSwitchView.isChecked = isChecked
    }

    override fun disableCanPushSwitch() {
        canPushSwitchView.isEnabled = false
    }

    private fun setCanPushSwitchOnClickListener() {
        canPushSwitchView.setOnCheckedChangeListener { _, isChecked ->
            presenter.onClickCanPushSwitch(
                isPermissionDenied = permissionManager.isPermissionDenied(POST_NOTIFICATIONS),
                isForeverDeniedPermission = permissionManager.isPermissionDeniedForever(
                    POST_NOTIFICATIONS
                ),
                isChecked = isChecked,
            )
        }
    }
}
