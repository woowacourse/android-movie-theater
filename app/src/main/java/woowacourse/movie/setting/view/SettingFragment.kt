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
import woowacourse.movie.util.MovieIntentConstant.KEY_IS_GRANTED

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
        setUpAlarmSwitch()
    }

    private fun setUpAlarmSwitch() {
        val isGranted = arguments?.getBoolean(KEY_IS_GRANTED) ?: false
        val isChecked = judgeChecked(isGranted)
        binding.isGranted = isGranted
        binding.isChecked = isChecked
        binding.switchSettingAlarm.setOnCheckedChangeListener { _, onSwitch ->
            sharedPreferences.saveAlarmSetting(onSwitch)
        }
    }

    private fun judgeChecked(isGranted: Boolean): Boolean {
        if (isGranted) return sharedPreferences.getSavedAlarmSetting()
        sharedPreferences.saveAlarmSetting(false)
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
                        putBoolean(KEY_IS_GRANTED, isGranted)
                    }
            }
        }
    }
}
