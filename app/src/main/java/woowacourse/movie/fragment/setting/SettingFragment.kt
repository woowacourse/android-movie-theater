package woowacourse.movie.fragment.setting

import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import woowacourse.movie.BundleKeys.IS_CAN_PUSH_CHECKED
import woowacourse.movie.BundleKeys.REQUEST_NOTIFICATION_PERMISSION
import woowacourse.movie.PermissionManager
import woowacourse.movie.R
import woowacourse.movie.SharedPreferenceUtil
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment(
    activityResultLauncher: ActivityResultLauncher<String>
) : Fragment(), SettingContract.View {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    override lateinit var presenter: SettingContract.Presenter
    private val permissionManager by lazy {
        PermissionManager(
            requireActivity(),
            activityResultLauncher
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
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

    override fun updateCanPushSwitch(isChecked: Boolean) {
        binding.swSettingCanPush.isChecked = isChecked
    }

    override fun disableCanPushSwitch() {
        binding.swSettingCanPush.isEnabled = false
    }

    private fun setCanPushSwitchOnClickListener() {
        binding.swSettingCanPush.setOnCheckedChangeListener { _, isChecked ->
            presenter.onClickCanPushSwitch(
                isPermissionDenied = permissionManager.isPermissionDenied(POST_NOTIFICATIONS),
                isForeverDeniedPermission = permissionManager.isPermissionDeniedForever(
                    POST_NOTIFICATIONS
                ),
                isChecked = isChecked,
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
