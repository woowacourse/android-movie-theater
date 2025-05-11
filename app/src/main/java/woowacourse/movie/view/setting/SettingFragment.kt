package woowacourse.movie.view.setting

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.presenter.setting.SettingContracts
import woowacourse.movie.presenter.setting.SettingPresenter

class SettingFragment :
    Fragment(),
    SettingContracts.View {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var presenter: SettingContracts.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        val prefs = requireContext().getSharedPreferences(SETTING_DATA_KEY, Context.MODE_PRIVATE)
        presenter = SettingPresenter(this, prefs)
        presenter.loadAlarmSwitch()

        binding.alarmSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.changeAlarmSwitch(isChecked)
        }
        return binding.root
    }

    companion object {
        const val SETTING_DATA_KEY = "setting"
    }

    override fun updateAlarmSwitchView(isPushEnabled: Boolean) {
        binding.alarmSwitch.isChecked = isPushEnabled
    }
}
