package woowacourse.movie.ui.main.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.repository.SettingRepositoryImpl

class SettingFragment : Fragment(), SettingContract.View {
    override lateinit var presenter: SettingContract.Presenter
    private lateinit var pushAlarmSwitch: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpPresenter()
    }

    private fun setUpPresenter() {
        SettingRepositoryImpl.init(requireContext())
        presenter = SettingPresenter(SettingRepositoryImpl)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        pushAlarmSwitch = view.findViewById(R.id.setting_push_alarm_switch)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSwitchClick()
    }

    override fun updateSwitch() {
        pushAlarmSwitch.isChecked = presenter.getNotificationState()
    }

    private fun setUpSwitchClick() {
        pushAlarmSwitch.setOnClickListener {
            presenter.setSwitchState(pushAlarmSwitch.isChecked)
        }
    }
}
