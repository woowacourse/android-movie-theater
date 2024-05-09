package woowacourse.movie.setting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.MovieMainActivity.Companion.sharedPreferences
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.setting.presenter.SettingPresenter
import woowacourse.movie.setting.presenter.contract.SettingContract

class SettingFragment : Fragment(), SettingContract.View {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var settingPresenter: SettingPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentSettingBinding.inflate(inflater, container, false)

        settingPresenter = SettingPresenter(this)

        setUpAlarmSwitch()

        return binding.root
    }

    private fun setUpAlarmSwitch() {
        binding.switchSettingAlarm.isChecked = sharedPreferences.getSavedAlarmSetting()
        binding.switchSettingAlarm.setOnCheckedChangeListener { _, onSwitch ->
            sharedPreferences.saveAlarmSetting(onSwitch)
        }
    }
}
