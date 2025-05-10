package woowacourse.movie.presentation.settings

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentSettingsBinding

class SettingsFragment :
    Fragment(),
    SettingsContract.View {
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding get() = _binding!!
    private lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = SettingsPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.switchNotifications.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                requestNotificationPermission()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestNotificationPermission() {
        if (!isPostNotificationPermissionGranted()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    binding.switchNotifications.isChecked = false
                    showPermissionCheckDialog()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private fun isPostNotificationPermissionGranted(): Boolean =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED

    private fun showPermissionCheckDialog() {
        AlertDialog
            .Builder(requireContext())
            .setTitle("알림 권한 필요")
            .setMessage("예매 알림을 받으려면 알림 권한이 필요합니다.\n권한을 허용해주세요.")
            .setPositiveButton("확인") { _, _ -> }
            .setCancelable(false)
            .show()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "권한을 허용했습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "권한을 거부했습니다.", Toast.LENGTH_SHORT).show()
                binding.switchNotifications.isChecked = false
            }
        }
}
