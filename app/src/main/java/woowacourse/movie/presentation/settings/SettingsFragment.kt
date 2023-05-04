package woowacourse.movie.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.settings.SettingsPreference

class SettingsFragment : Fragment(), SettingsContract.View {

    override lateinit var presenter: SettingsContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = SettingsPresenter(
            this,
            SettingsPreference.getInstance("notification", requireContext()),
        )
    }

    override fun initNotificationSwitch(isNotifiable: Boolean) {
        val notificationSwitch =
            requireActivity().findViewById<SwitchCompat>(R.id.switchPushPermission)

        notificationSwitch.isChecked = isNotifiable

        notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.setNotifiable(isChecked)
        }
    }
}
