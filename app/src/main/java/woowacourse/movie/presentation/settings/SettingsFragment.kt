package woowacourse.movie.presentation.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.model.data.local.SettingPreference
import woowacourse.movie.model.data.storage.SettingStorage

class SettingsFragment(
    private val getSettingStorage: (Context) -> SettingStorage = { SettingPreference(it) }
) : Fragment(), SettingsContract.View {

    override lateinit var presenter: SettingsContract.Presenter
    private val notificationSwitch by lazy { requireActivity().findViewById<SwitchCompat>(R.id.switchPushPermission) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        initNotificationSwitch()
    }

    private fun initPresenter() {
        presenter = SettingsPresenter(this, getSettingStorage(requireContext()))
    }

    private fun initNotificationSwitch() {
        notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.setNotificationSettings(isChecked)
        }
    }

    override fun setSwitchSelectedState(state: Boolean) {
        notificationSwitch.isChecked = state
    }
}
