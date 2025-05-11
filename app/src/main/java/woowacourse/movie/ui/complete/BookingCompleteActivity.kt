package woowacourse.movie.ui.complete

import android.Manifest
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import java.time.LocalDateTime
import java.time.ZoneId
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingCompleteBinding
import woowacourse.movie.domain.model.BookedTicket
import woowacourse.movie.domain.model.Headcount
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.notification.MovieReminderReceiver
import woowacourse.movie.ui.main.MovieBookingActivity
import woowacourse.movie.utils.AlarmManagerCompat
import woowacourse.movie.utils.Destination
import woowacourse.movie.utils.StringFormatter
import woowacourse.movie.utils.intentSerializable

class BookingCompleteActivity :
    AppCompatActivity(),
    BookingCompleteContract.View {
    private val bookingCompletePresenter = BookingCompletePresenter(this)
    private lateinit var binding: ActivityBookingCompleteBinding
    private val sharedPrefs: SharedPreferences by lazy {
        this.getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE)
    }
    private val isFirstNotificationRequest: Boolean
        get() =
            sharedPrefs.getBoolean(
                getString(R.string.preference_is_first_notification_request),
                true,
            )
    private val isEnablePostNotification: Boolean
        get() = sharedPrefs.getBoolean(getString(R.string.preference_post_notification), true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding =
            DataBindingUtil.setContentView(
                this@BookingCompleteActivity,
                R.layout.activity_booking_complete,
            )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        applyWindowInsets()
        setOnBackPressedCallback()

        bookingCompletePresenter.loadBookedTicket(restoreBookedTicket(), restoreDestination())
    }

    override fun showMovieTitle(movieTitle: String) {
        binding.movieTitle = movieTitle
    }

    override fun showScreeningDateTime(dateTime: LocalDateTime) {
        binding.dateTime = dateTime
        binding.stringFormatter = StringFormatter
    }

    override fun showDetailInfos(
        headcount: Headcount,
        seats: Seats,
        theaterName: String,
    ) {
        val count = headcount.count
        val seatsNames = seats.reservingSeats.map { seat -> seat.toText() }.sorted().joinToString()

        binding.tvDetailInfo.text =
            getString(
                R.string.text_headcount_with_seats_and_theater,
                count, seatsNames, theaterName,
            )
    }

    override fun showTotalPrice(totalPrice: Int) {
        binding.totalPrice = totalPrice
        binding.stringFormatter = StringFormatter
    }

    override fun moveTo(destination: Destination) {
        val intent =
            MovieBookingActivity.newIntent(this, destination).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        startActivity(intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                bookingCompletePresenter.navigateTo()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    override fun handlePermission(bookedTicket: BookedTicket) {
        requestPostNotificationPermission()

        if (hasPostNotification()) {
            scheduleNotification(bookedTicket)
        }
    }

    private fun requestPostNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isGranted =
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED

            if (!isGranted) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    showRecommendPushNotification()
                } else { // 최초 요청 || 다시 묻지 않음
                    if (isFirstNotificationRequest) {
                        showNotificationDialog(Manifest.permission.POST_NOTIFICATIONS)
                        sharedPrefs.edit {
                            putBoolean(
                                getString(R.string.preference_is_first_notification_request),
                                false,
                            )
                        }
                    } else {
                        showRecommendSettingDialog()
                    }
                }
            }
        }
    }

    private fun showRecommendPushNotification() {
        Toast.makeText(
            this,
            getString(R.string.recommend_push_notification),
            Toast.LENGTH_LONG,
        ).show()
    }

    private fun showNotificationDialog(permissionName: String) {
        AlertDialog
            .Builder(this)
            .setTitle(getString(R.string.dialog_notification_title))
            .setMessage(getString(R.string.dialog_notification_message))
            .setPositiveButton(getString(R.string.dialog_notification_positive_btn)) { _, _ ->
                requestPermissionLauncher.launch(permissionName)
            }
            .setNegativeButton(getString(R.string.dialog_notification_negative_btn)) { dialog, _ ->
                dialog.dismiss()
                showRecommendPushNotification()
            }.setCancelable(false)
            .show()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isChecked ->
            if (isChecked) {
                AlarmManagerCompat.requestScheduleExactPermission(this)
            }
        }

    private fun showRecommendSettingDialog() {
        AlertDialog
            .Builder(this)
            .setTitle(getString(R.string.dialog_notification_title_for_recommend))
            .setMessage(getString(R.string.dialog_notification_message_for_recommend))
            .setPositiveButton(getString(R.string.dialog_notification_positive_btn_for_recommend)) { _, _ ->
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", packageName, null)
                    }
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.dialog_notification_negative_btn_for_recommend)) { dialog, _ ->
                dialog.dismiss()
                showRecommendPushNotification()
            }.setCancelable(false)
            .show()
    }

    private fun hasPostNotification(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            isEnablePostNotification
        }
    }

    private fun scheduleNotification(bookedTicket: BookedTicket) {
        val triggerTime = bookedTicket.movieSchedule.screeningDateTime.minusMinutes(30L)
        val triggerAtMillis =
            triggerTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val requestCode = bookedTicket.hashCode()
        val intent = MovieReminderReceiver.newIntent(this, bookedTicket)
        val pendingIntent =
            PendingIntent.getBroadcast(
                this,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        AlarmManagerCompat.setExact(this, triggerAtMillis, pendingIntent)
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun restoreBookedTicket(): BookedTicket = intent.intentSerializable(EXTRA_BOOKED_TICKET, BookedTicket::class.java)!!

    private fun restoreDestination(): Destination = intent.intentSerializable(EXTRA_DESTINATION, Destination::class.java)!!

    private fun setOnBackPressedCallback() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    bookingCompletePresenter.navigateTo()
                }
            },
        )
    }

    private fun Seat.toText(): String = Char(row + ASCII_A.code) + (col + 1).toString()

    companion object {
        private const val EXTRA_BOOKED_TICKET = "EXTRA_BOOKED_TICKET"
        private const val EXTRA_DESTINATION = "EXTRA_DESTINATION"
        private const val ASCII_A = 'A'

        fun newIntent(
            context: Context,
            bookedTicket: BookedTicket,
            destination: Destination,
        ): Intent =
            Intent(context, BookingCompleteActivity::class.java).apply {
                putExtra(EXTRA_BOOKED_TICKET, bookedTicket)
                putExtra(EXTRA_DESTINATION, destination)
            }
    }
}
