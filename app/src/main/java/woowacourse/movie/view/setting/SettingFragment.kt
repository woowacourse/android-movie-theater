package woowacourse.movie.view.setting

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.presenter.setting.SettingContract
import woowacourse.movie.presenter.setting.SettingPresenter
import woowacourse.movie.utils.MovieUtils

class SettingFragment : Fragment(), SettingContract.View {
    private val presenter: SettingPresenter by lazy {
        SettingPresenter(
            view = this,
            sharedPreference = requireContext().getSharedPreferences(PUSH_SETTING, MODE_PRIVATE)
        )
    }
    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadSavedSetting()
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showSavedSetting(isPushSetting: Boolean) {
        binding.switchButton.isChecked = isPushSetting
    }

    override fun showPushSettingOnToast()  = MovieUtils.makeToast(requireContext(), getString(R.string.on_push_text))

    override fun showPushSettingOffToast()  = MovieUtils.makeToast(requireContext(), getString(R.string.off_push_text))

    private fun initView() {
        binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
            presenter.settingPushAlarmState(isChecked)
        }
    }


    companion object {
        const val PUSH_SETTING = "pushSetting"
    }
}
