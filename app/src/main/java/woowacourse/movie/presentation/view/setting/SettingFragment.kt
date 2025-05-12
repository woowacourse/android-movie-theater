package woowacourse.movie.presentation.view.setting

import android.os.Bundle
import android.view.View
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.presentation.base.BaseFragment

class SettingFragment :
    BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting),
    SettingContract.View {
    private lateinit var presenter: SettingContract.Presenter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        presenter = SettingPresenter(this)
        presenter.checkPreference()

        binding.pushSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.updatePreference(isChecked)
        }
    }

    override fun updateSwitch(isEnabled: Boolean) {
        binding.pushSwitch.setOnCheckedChangeListener(null)
        binding.pushSwitch.isChecked = isEnabled
        binding.pushSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.updatePreference(isChecked)
        }
    }
}
