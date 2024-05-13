package woowacourse.movie.presentation.homefragments.setting

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment(), SettingContract.View {
    private lateinit var binding: FragmentSettingBinding
    private val presenter: SettingContract.Presenter = SettingPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSettingBinding.inflate(inflater)
        presenter.loadSetting(getPushSetting())
        initView()
        return binding.root
    }

    private fun initView() {
        binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
            presenter.settingAlarm(isChecked)
        }
    }

    override fun saveSetting(pushSetting: Boolean) {
        val sharedPreference = context?.getSharedPreferences(PUSH_SETTING, MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        editor?.putBoolean(PUSH_SETTING, pushSetting)?.apply()
    }

    override fun showSavedSetting(pushSetting: Boolean) {
        binding.switchButton.isChecked = pushSetting
    }

    private fun getPushSetting(): Boolean {
        val sharedPreference = context?.getSharedPreferences(PUSH_SETTING, MODE_PRIVATE)
        return sharedPreference?.getBoolean(PUSH_SETTING, false) ?: false
    }

    companion object {
        const val PUSH_SETTING = "pushSetting"
    }
}
