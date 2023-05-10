package woowacourse.movie.view.main.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.database.SettingPreferencesManager
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment(), SettingContract.View {

    private lateinit var binding: FragmentSettingBinding
    private val presenter: SettingContract.Presenter by lazy {
        SettingPresenter(this, SettingPreferencesManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.initAlarmState()
        binding.settingPushAlarmSwitch.setOnClickListener {
            presenter.changeAlarmState()
        }
    }

    override fun setSwitchState(isChecked: Boolean) {
        binding.settingPushAlarmSwitch.isChecked = isChecked
    }
}
