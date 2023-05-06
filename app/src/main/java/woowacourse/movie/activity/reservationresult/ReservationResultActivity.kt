package woowacourse.movie.activity.reservationresult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationResultBinding
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.model.TicketsUiModel

class ReservationResultActivity : AppCompatActivity(), ReservationResultContract.View {
    private var _binding: ActivityReservationResultBinding? = null
    private val binding
        get() = _binding!!

    override lateinit var presenter: ReservationResultContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_result)

        setUpUiModels()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setUpUiModels() {
        binding.tickets = intent.extras?.getSerializableCompat(TICKETS_KEY_VALUE) ?: throw IllegalStateException()
        binding.movie = intent.extras?.getSerializableCompat(MOVIE_KEY_VALUE) ?: throw IllegalStateException()
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    companion object {
        private const val MOVIE_KEY_VALUE = "movie"
        private const val TICKETS_KEY_VALUE = "tickets"
        fun getIntent(
            context: Context,
            movieUiModel: MovieUiModel,
            ticketsUiModel: TicketsUiModel
        ): Intent {
            val intent = Intent(context, ReservationResultActivity::class.java)
            intent.putExtra(MOVIE_KEY_VALUE, movieUiModel)
            intent.putExtra(TICKETS_KEY_VALUE, ticketsUiModel)
            return intent
        }
    }
}
