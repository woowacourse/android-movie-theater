package woowacourse.movie.setting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.MovieMainActivity.Companion.sharedPrefs
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.setting.presenter.SettingPresenter
import woowacourse.movie.setting.presenter.contract.SettingContract
import woowacourse.movie.util.MovieIntent.IS_GRANTED

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
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        settingPresenter.loadAlarmSwitch()
    }

    override fun setUpAlarmSwitch(savedAlarmSetting: Boolean) {
        val isGranted = arguments?.getBoolean(IS_GRANTED.key) ?: IS_GRANTED.invalidValue as Boolean
        val isChecked = judgeChecked(isGranted, savedAlarmSetting)
        binding.isGranted = isGranted
        binding.isChecked = isChecked
        binding.switchSettingAlarm.setOnCheckedChangeListener { _, onSwitch ->
            sharedPrefs.saveAlarmSetting(onSwitch)
        }
    }

    private fun judgeChecked(
        isGranted: Boolean,
        savedAlarmSetting: Boolean,
    ): Boolean {
        if (isGranted) return savedAlarmSetting
        sharedPrefs.saveAlarmSetting(false)
        return false
    }

    companion object {
        fun newInstance(
            settingFragment: SettingFragment,
            isGranted: Boolean,
        ): SettingFragment {
            return settingFragment.apply {
                arguments =
                    Bundle().apply {
                        putBoolean(IS_GRANTED.key, isGranted)
                    }
            }
        }
    }
}
