package woowacourse.movie.fragment.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import com.woowacourse.domain.SettingRepository
import woowacourse.movie.R
import woowacourse.movie.fragment.setting.SettingContract
import woowacourse.movie.fragment.setting.SettingPresenter
import woowacourse.movie.service.NotificationPermission
import woowacourse.movie.service.NotificationPermission.checkNotificationPermission
import woowacourse.movie.service.SharedPreferenceUtil

class SettingFragment : Fragment(), SettingContract.View {
    override lateinit var presenter: SettingContract.Presenter
    private val switch: SwitchMaterial by lazy { requireView().findViewById(R.id.switch_setting_can_push) }
    private val settingRepository: SettingRepository by lazy { SharedPreferenceUtil(requireContext()) }
    private val notificationPermission by lazy { requireContext().checkNotificationPermission() }
    private val permissionLauncher =
        NotificationPermission.getPermissionLauncher(this) { switch.isChecked = false }

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

        presenter.initSwitchStatus(notificationPermission)
        onSwitchChanged()
    }

    private fun onSwitchChanged() {
        switch.setOnCheckedChangeListener { _, isChecked ->
            presenter.updateSwitchStatus(notificationPermission, isChecked) {
                NotificationPermission.requestNotificationPermission(
                    permissionLauncher
                )
            }
        }
    }

    override fun setSwitchStatus(setting: Boolean) {
        switch.isChecked = setting
    }

    companion object {
        const val SETTING_PUSH_ALARM_SWITCH_KEY = "settingPushAlarmSwitchKey"
    }
}
