package woowacourse.movie.ui.reservation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.MovieReservationApplication
import woowacourse.movie.R
import woowacourse.movie.data.model.ReservationTicket
import woowacourse.movie.databinding.ActivityReservationCompleteBinding
import woowacourse.movie.domain.model.AlarmTimeBeforeMinute
import woowacourse.movie.domain.repository.OfflineReservationRepository
import woowacourse.movie.domain.repository.SharedPreferenceRepository
import woowacourse.movie.local.repository.MovieReservationNotificationRepository
import woowacourse.movie.local.source.ReservationTicketDatabase
import woowacourse.movie.ui.main.MainActivity
import woowacourse.movie.ui.pushnotification.PushNotificationBroadCastReceiver

class ReservationCompleteActivity : AppCompatActivity(), ReservationCompleteContract.View {
    private lateinit var presenter: ReservationCompleteContract.Presenter
    private val binding: ActivityReservationCompleteBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_reservation_complete)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            presenter.updateNotificationPreference(isGranted)
            if (!isGranted) {
                showPermissionSnackBar()
            }
        }

    private fun showPermissionSnackBar() {
        Snackbar.make(binding.root, R.string.push_notification_guide, Snackbar.LENGTH_LONG).setAction(R.string.ok) {
            startApplicationDetailSettings()
        }.show()
    }

    private fun startApplicationDetailSettings() {
        val intent =
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                data = Uri.fromParts("package", packageName, null)
            }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        initView()
        onBackPressedCallback()
    }

    override fun onStart() {
        super.onStart()
        requestPermission()
    }

    private fun requestPermission() {
        when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU -> return // old version doesn't need to request permission
            hasPermission() -> presenter.updateNotificationPreference(true)
            !hasPermission() -> showPermissionChangingGuide()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun hasPermission(): Boolean =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showPermissionChangingGuide() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            showPermissionSnackBar()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun onBackPressedCallback() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    MainActivity.backToHome(this@ReservationCompleteActivity)
                    finish()
                }
            },
        )
    }

    private fun initPresenter() {
        val reservationTicketId = intent.getIntExtra(PUT_EXTRA_KEY_RESERVATION_TICKET_ID, DEFAULT_RESERVATION_TICKET_ID)

        presenter =
            ReservationCompletePresenter(
                this,
                OfflineReservationRepository(
                    ReservationTicketDatabase.getDatabase(applicationContext).reservationDao(),
                ),
                preferenceRepository =
                    SharedPreferenceRepository(
                        notificationPreference = MovieReservationApplication.notificationPreference,
                    ),
                notificationRepository =
                    MovieReservationNotificationRepository(
                        context = applicationContext,
                        receiverClass = PushNotificationBroadCastReceiver::class.java,
                        alarmTime = AlarmTimeBeforeMinute(),
                    ),
                reservationTicketId,
            )
    }

    private fun initView() {
        presenter.loadReservation()
    }

    override fun showReservation(reservationTicket: ReservationTicket) {
        binding.reservationTicket = reservationTicket
        binding.totalPrice = reservationTicket.totalPrice()
    }

    override fun showReservationFail(throwable: Throwable) {
        showToastMessage(throwable)
    }

    private fun showToastMessage(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val PUT_EXTRA_KEY_RESERVATION_TICKET_ID = "reservationTicketId"
        const val DEFAULT_RESERVATION_TICKET_ID = -1

        fun startActivity(
            context: Context,
            reservationTicketId: Int,
        ) {
            val intent = Intent(context, ReservationCompleteActivity::class.java)
            intent.putExtra(PUT_EXTRA_KEY_RESERVATION_TICKET_ID, reservationTicketId)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            context.startActivity(intent)
        }
    }
}
