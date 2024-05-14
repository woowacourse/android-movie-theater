package woowacourse.movie.ui.setting

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieSettingBinding
import woowacourse.movie.model.db.UserTicketDatabase
import woowacourse.movie.model.db.UserTicketRepositoryImpl

class MovieSettingFragment : Fragment(), MovieSettingContract.View {
    private lateinit var binding: FragmentMovieSettingBinding
    private val presenter by lazy {
        MovieSettingPresenter(
            this,
            UserTicketRepositoryImpl.get(UserTicketDatabase.database().userTicketDao()),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_setting, container, false)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        presenter.loadNotificationStatus()
        setOnSwitchListener()
    }

    private fun setOnSwitchListener() {
        binding.swAlarmStatus.setOnCheckedChangeListener { _, isChecked ->
            presenter.setAlarmStatus(isChecked)
            if (!isChecked) {
                presenter.cancelNotification()
            } else {
                requestNotificationPermission(requireContext())
            }
        }
    }

    private fun requestNotificationPermission(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Toast.makeText(
                    context,
                    getString(R.string.requre_alarm_permission_comment),
                    Toast.LENGTH_SHORT,
                )
                    .show()
                binding.swAlarmStatus.isChecked = false
                presenter.setAlarmStatus(false)
            }
        }
    }

    override fun showNotificationStatus(status: Boolean) {
        binding.swAlarmStatus.isChecked = status
    }
}
