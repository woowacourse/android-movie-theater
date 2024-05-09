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
import woowacourse.movie.ui.MovieSharedPreference
import woowacourse.movie.ui.setting.notification.MovieAlarmManager

class MovieSettingFragment : Fragment(), MovieSettingContract.View {
    private lateinit var binding: FragmentMovieSettingBinding
    private val presenter by lazy { MovieSettingPresenter(this) }
    private val sharedPreference: MovieSharedPreference by lazy {
        MovieSharedPreference(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_setting, container, false)

        presenter.loadNotificationStatus()
        setOnSwitchListener()

        return binding.root
    }

    private fun setOnSwitchListener() {
        binding.swAlarmStatus.setOnCheckedChangeListener { _, isChecked ->
            sharedPreference.setAlarmChecked(isChecked)
            if (!isChecked) {
                MovieAlarmManager.cancelAlarm(requireContext(), 1)
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
                sharedPreference.setAlarmChecked(false)
            }
        }
    }

    override fun showNotificationStatus() {
        binding.swAlarmStatus.isChecked = sharedPreference.getAlarmChecked()
    }
}
