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

class SettingFragment : Fragment(R.layout.fragment_setting), SettingContract.View {

    private lateinit var binding: FragmentSettingBinding
    override val presenter: SettingContract.Presenter by lazy {
        SettingPresenter(this, SettingPreferencesManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.updateSwitchState()
        binding.settingPushAlarmSwitch.setOnClickListener {
            presenter.onClickSwitch()
        }
    }

    override fun setSwitchState(isChecked: Boolean) {
        binding.settingPushAlarmSwitch.isChecked = isChecked
    }

}
