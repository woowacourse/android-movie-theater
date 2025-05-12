package woowacourse.movie.view.reservationComplete

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.MainActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationCompleteBinding
import woowacourse.movie.model.ticket.MovieTicket
import woowacourse.movie.presenter.reservationComplete.ReservationCompleteContracts
import woowacourse.movie.presenter.reservationComplete.ReservationCompletePresenter
import woowacourse.movie.view.alarm.AlarmFactory
import woowacourse.movie.view.extension.dialogMessage
import woowacourse.movie.view.extension.getSerializableExtraData

class ReservationCompleteActivity :
    androidx.appcompat.app.AppCompatActivity(),
    ReservationCompleteContracts.View {
    private lateinit var presenter: ReservationCompleteContracts.Presenter
    private lateinit var binding: ActivityReservationCompleteBinding

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_complete)
        val prefs = this.getSharedPreferences(ALARM_DATA_KEY, Context.MODE_PRIVATE)
        presenter = ReservationCompletePresenter(this, prefs)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.reservation_complete)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val intentMovieTicketData: MovieTicket? =
            intent.getSerializableExtraData<MovieTicket>(TICKET_DATA_KEY)
        if (intentMovieTicketData == null) {
            presenter.requestErrorDialogMessage()
            return
        }
        presenter.updateTicketData(intentMovieTicketData)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupBackPressedDispatcher()
        presenter.requestAlarm(intentMovieTicketData)
    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(MainActivity.getIntent(this))
        finish()
        return super.onSupportNavigateUp()
    }

    override fun showMovieTicket(movieTicket: MovieTicket) {
        binding.movieTicket = movieTicket
    }

    override fun showErrorDialogMessage() {
        dialogMessage(this, R.string.not_found_data_error_message)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun showAlarmPermissionScreen() {
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
        startActivity(intent)
    }

    override fun showAlarmBeforeMovieStart(movieTicket: MovieTicket) {
        val alarmFactory = AlarmFactory(this) { presenter.requestAlarmPermissionScreen() }
        alarmFactory.scheduleNotification(movieTicket)
    }

    private fun setupBackPressedDispatcher() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    startActivity(MainActivity.getIntent(this@ReservationCompleteActivity))
                    finish()
                }
            },
        )
    }

    companion object {
        const val TICKET_DATA_KEY = "movieTicket"
        private const val ALARM_DATA_KEY = "alarmPrefs"

        fun getIntent(
            context: Context,
            movieTicket: MovieTicket,
        ): Intent =
            Intent(context, ReservationCompleteActivity::class.java).apply {
                putExtra(TICKET_DATA_KEY, movieTicket)
            }
    }
}
