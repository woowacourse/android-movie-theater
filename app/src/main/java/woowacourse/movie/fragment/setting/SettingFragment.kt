package woowacourse.movie.fragment.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.domain.setting.SettingRepository
import woowacourse.movie.service.PermissionManager
import woowacourse.movie.service.PermissionManager.checkNotificationSelfPermission
import woowacourse.movie.service.PermissionManager.requestNotificationPermission
import woowacourse.movie.view.setting.SharedSettingRepository

class SettingFragment : Fragment(), SettingContract.View {
    override lateinit var presenter: SettingContract.Presenter
    private val permissionStatus
        get() = requireContext().checkNotificationSelfPermission()

    private val requestPermissionLauncher =
        PermissionManager.getRequestPermissionLauncher(this, ::onPermissionGranted)

    private val switch: SwitchCompat by lazy {
        requireView().findViewById(R.id.setting_push_switch)
    }

    private val settingRepository: SettingRepository by lazy {
        SharedSettingRepository(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = SettingPresenter(this, settingRepository)
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwitch()
    }

    override fun setSwitchStatus(setting: Boolean) {
        switch.isChecked = setting
    }

    private fun initSwitch() {
        initSwitchCheckStatus()
        switch.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChange(isChecked)
        }
    }

    private fun initSwitchCheckStatus() {
        presenter.initSwitchStatus(permissionStatus)
    }

    private fun onCheckedChange(isChecked: Boolean) {
        presenter.updateSwitchStatus(
            permissionStatus,
            { requestNotificationPermission(this, requestPermissionLauncher, ::requestPermission) },
            isChecked
        )
    }

    private fun onPermissionGranted() {
        switch.isChecked = false
    }

    private fun requestPermission() {
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
