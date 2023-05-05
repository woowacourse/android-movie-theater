package woowacourse.movie.fragment.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.service.PermissionManager
import woowacourse.movie.service.PermissionManager.checkNotificationSelfPermission
import woowacourse.movie.view.setting.SharedSettingRepository

class SettingFragment : Fragment(), SettingContract.View {
    override lateinit var presenter: SettingContract.Presenter
    private val permissionStatus
        get() = requireContext().checkNotificationSelfPermission()

    private val requestPermissionLauncher =
        PermissionManager.getRequestPermissionLauncher(this, ::onPermissionDenied)

    private val switch: SwitchCompat by lazy {
        requireView().findViewById(R.id.setting_push_switch)
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
        presenter = SettingPresenter(this, SharedSettingRepository(requireContext()))
        initSwitch()
    }

    private fun initSwitch() {
        presenter.initSwitchStatus(permissionStatus)
        switch.setOnCheckedChangeListener { _, isChecked ->
            presenter.updateSwitchStatus(
                permissionStatus, isChecked
            )
        }
    }

    override fun setSwitchStatus(setting: Boolean) {
        switch.isChecked = setting
    }

    override fun requestPermission() {
        PermissionManager.requestNotificationPermission(
            this, requestPermissionLauncher, ::notifyForGetPermission
        )
    }

    private fun onPermissionDenied() {
        switch.isChecked = false
    }

    private fun notifyForGetPermission() {
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.permission_instruction_ment),
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val SETTING_NOTIFICATION = "notification"
    }
}
