package woowacourse.movie.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Switch
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.SettingPreferencesManager
import woowacourse.movie.contract.SettingContract
import woowacourse.movie.presenter.SettingPresenter

class SettingFragment : Fragment(R.layout.fragment_setting), SettingContract.View {

    override val presenter: SettingContract.Presenter =
        SettingPresenter(this, SettingPreferencesManager)
    private lateinit var pushAlarmSwitch: Switch

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pushAlarmSwitch = view.findViewById(R.id.setting_push_alarm_switch)
        presenter.updateSwitchState()
        pushAlarmSwitch.setOnClickListener {
            presenter.onClickSwitch()
        }
    }

    override fun setSwitchState(isChecked: Boolean) {
        pushAlarmSwitch.isChecked = isChecked
    }

}
