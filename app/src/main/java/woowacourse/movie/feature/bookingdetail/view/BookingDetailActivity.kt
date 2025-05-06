package woowacourse.movie.feature.bookingdetail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingDetailBinding
import woowacourse.movie.feature.bookingdetail.contract.BookingDetailContract
import woowacourse.movie.feature.bookingdetail.presenter.BookingDetailPresenter
import woowacourse.movie.feature.bookingdetail.view.adapter.DateAdapter
import woowacourse.movie.feature.bookingdetail.view.adapter.TimeAdapter
import woowacourse.movie.feature.bookingseat.view.BookingSeatActivity
import woowacourse.movie.feature.model.BookingInfoUiModel
import woowacourse.movie.feature.model.MovieDateUiModel
import woowacourse.movie.feature.model.MovieTimeUiModel
import woowacourse.movie.feature.model.ScreeningUiModel
import woowacourse.movie.util.getExtra

class BookingDetailActivity :
    AppCompatActivity(),
    BookingDetailContract.View {
    private val binding: ActivityBookingDetailBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_booking_detail,
        )
    }
    private val presenter: BookingDetailPresenter by lazy { BookingDetailPresenter(this) }
    private lateinit var dateAdapter: DateAdapter
    private lateinit var timeAdapter: TimeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.presenter = presenter

        setupDateSpinnerItemClickListener()
        setupTimeSpinnerItemClickListener()
        presenter.prepareBookingInfo(
            intent.getExtra(SCREENING_KEY) ?: ScreeningUiModel(),
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) presenter.onBackButtonClicked()
        return super.onOptionsItemSelected(item)
    }

    override fun setupDateView(dates: List<MovieDateUiModel>) {
        dateAdapter = DateAdapter(this, dates)
        binding.spBookingDetailDate.adapter = dateAdapter
    }

    override fun setupTimeView(times: List<String>) {
        timeAdapter = TimeAdapter(this, times)
        binding.spBookingDetailTime.adapter = timeAdapter
    }

    override fun updateBookingInfo(bookingInfo: BookingInfoUiModel) {
        binding.bookingInfo = bookingInfo
    }

    override fun navigateToBookingSeat(bookingInfo: BookingInfoUiModel) {
        val intent = BookingSeatActivity.newIntent(this, bookingInfo)
        startActivity(intent)
    }

    override fun navigateToBack() {
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(BOOKING_INFO_KEY, presenter.saveBookingInfo())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val bookingInfo: BookingInfoUiModel =
            savedInstanceState.getExtra(BOOKING_INFO_KEY) ?: BookingInfoUiModel()
        presenter.loadBookingInfo(bookingInfo)
    }

    private fun setupDateSpinnerItemClickListener() {
        binding.spBookingDetailDate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedDate =
                        MovieDateUiModel.from(parent?.getItemAtPosition(position) as String)
                    presenter.selectDate(selectedDate.toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
    }

    private fun setupTimeSpinnerItemClickListener() {
        binding.spBookingDetailTime.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedTime =
                        MovieTimeUiModel.from(parent?.getItemAtPosition(position) as String)
                    presenter.selectTime(selectedTime.toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
    }

    companion object {
        private const val SCREENING_KEY = "SCREENING"
        private const val BOOKING_INFO_KEY = "BOOKING_INFO"

        fun newIntent(
            context: Context,
            screening: ScreeningUiModel,
        ): Intent =
            Intent(context, BookingDetailActivity::class.java).apply {
                putExtra(SCREENING_KEY, screening)
            }
    }
}
