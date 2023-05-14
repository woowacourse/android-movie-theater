package woowacourse.movie.setting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.SettingPreference
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.setting.view.contract.SettingContract
import woowacourse.movie.setting.view.presenter.SettingPresenter

class SettingFragment : Fragment(), SettingContract.View {
    override lateinit var presenter: SettingContract.Presenter
    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        val view = binding.root
        presenter = SettingPresenter(this, SettingPreference(binding.root.context))
        bindSwitch()
        return view
    }

    override fun bindSwitch() {
        presenter.initState()
        setSwitch()
    }

    override fun initSwitch(state: Boolean) {
        binding.pushAlarmSwitch.isChecked = state
    }

    private fun setSwitch() {
        binding.pushAlarmSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.setSwitchState(isChecked)
        }
    }
}
