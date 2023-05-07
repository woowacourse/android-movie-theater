package woowacourse.movie.ui.fragment.settings.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.storage.SettingsStorage
import woowacourse.movie.databinding.FragmentSettingsBinding
import woowacourse.movie.ui.fragment.settings.SettingsContract
import woowacourse.movie.ui.fragment.settings.presenter.SettingsPresenter

class SettingsFragment : Fragment(), SettingsContract.View {
    override lateinit var presenter: SettingsContract.Presenter
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initPresenter()
        initBinding()

        return binding.root
    }

    private fun initPresenter() {
        presenter = SettingsPresenter(SettingsStorage, this)
    }

    private fun initBinding() {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        binding.presenter = presenter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPushNotificationSwitch()
    }

    override fun setPushNotificationState(enable: Boolean) {
        binding.pushNotificationSwitch.isChecked = enable
    }

    private fun initPushNotificationSwitch() {
        presenter.checkPushNotificationState()
    }
}
