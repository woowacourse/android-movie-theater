package woowacourse.movie.ui.setting

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieSettingBinding
import woowacourse.movie.model.MoviePreferencesUtil

class MovieSettingFragment : Fragment(), MovieSettingContract.View {
    private var _binding: FragmentMovieSettingBinding? = null
    private val binding: FragmentMovieSettingBinding
        get() = _binding!!
    private val moviePreferencesUtil by lazy { MoviePreferencesUtil(requireContext()) }
    private val movieSettingPresenter: MovieSettingPresenter by lazy {
        MovieSettingPresenter(this, moviePreferencesUtil)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_setting, container, false)
        movieSettingPresenter.loadInitialSetting()
        return binding.root
    }

    private fun hasNotificationPermission() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun setInitialSetting(isEnabled: Boolean) {
        binding.apply {
            presenter = movieSettingPresenter
            tvSettingTitle.text = "푸시 알림 수신"
            tvSettingDescription.text = "해제하면 푸시 알림을 수신할 수 없습니다."
            isNotificationActive = isEnabled
            executePendingBindings()
        }
    }

    override fun updateSwitchStatus(isEnabled: Boolean) {
        Log.i("isEnabled", "updateSwitchStatus: ${hasNotificationPermission()}")
        binding.isNotificationActive = isEnabled
        binding.executePendingBindings()
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(requireContext(), R.string.toast_invalid_key, Toast.LENGTH_SHORT).show()
    }
}
