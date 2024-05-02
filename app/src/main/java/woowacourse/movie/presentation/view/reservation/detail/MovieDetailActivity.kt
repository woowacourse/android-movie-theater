package woowacourse.movie.presentation.view.reservation.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.view.reservation.seat.SeatSelectionActivity
import woowacourse.movie.presentation.view.screening.ScreeningActivity.Companion.MOVIE_ID_KEY
import woowacourse.movie.presentation.view.screening.theater.TheaterBottomSheetDialogFragment.Companion.THEATER_ID_KEY

class MovieDetailActivity : BaseActivity(), MovieDetailContract.View {
    private lateinit var movieDetailPresenter: MovieDetailContract.Presenter
    private lateinit var timeSpinnerAdapter: ArrayAdapter<String>
    private lateinit var binding: ActivityMovieDetailBinding
    private val dateSpinner: Spinner by lazy {
        findViewById(R.id.dateSpinner)
    }
    private val timeSpinner: Spinner by lazy {
        findViewById(R.id.timeSpinner)
    }

    override fun getLayoutResId(): Int = R.layout.activity_movie_detail

    override fun onCreateSetup(savedInstanceState: Bundle?) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val movieId = intent.getIntExtra(MOVIE_ID_KEY, DEFAULT_MOVIE_ID)
        val theaterId = intent.getIntExtra(THEATER_ID_KEY, DEFAULT_THEATER_ID)

        movieDetailPresenter =
            MovieDetailPresenterImpl(movieId, theaterId) // todo: theaterId 넣어줘서 극장별 상영시간 받아오기
        movieDetailPresenter.attachView(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        binding.view = this
        binding.data = movieDetailPresenter as MovieDetailPresenterImpl

        savedInstanceState?.let { it ->
            val count = it.getString(SIS_COUNT_KEY)?.toIntOrNull()
            count?.let { value ->
                movieDetailPresenter.initReservationCount(value)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        movieDetailPresenter.detachView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SIS_COUNT_KEY, binding.reservationInfo.text.toString())
    }

    override fun setScreeningDatesAndTimes(
        dates: List<String>,
        times: List<String>,
        defaultDataIndex: Int,
    ) {
        attachDateSpinnerAdapter(dates, defaultDataIndex)
        attachTimeSpinnerAdapter(times, defaultDataIndex)
    }

    private fun attachDateSpinnerAdapter(
        dates: List<String>,
        defaultDataIndex: Int,
    ) {
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, dates).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        dateSpinner.adapter = adapter
        dateSpinner.setSelection(defaultDataIndex)
        onSelectDateListener()
    }

    override fun updateScreeningTimes(
        times: List<String>,
        defaultDataIndex: Int,
    ) {
        timeSpinnerAdapter.clear()
        timeSpinnerAdapter.addAll(times)
        timeSpinnerAdapter.notifyDataSetChanged()
        timeSpinner.setSelection(defaultDataIndex)
    }

    private fun attachTimeSpinnerAdapter(
        times: List<String>,
        defaultDataIndex: Int,
    ) {
        timeSpinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, times).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        timeSpinner.adapter = timeSpinnerAdapter
        timeSpinner.setSelection(defaultDataIndex)
        onSelectTimeListener()
    }

    private fun onSelectDateListener() {
        dateSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long,
                ) {
                    val selectedDate = parent.getItemAtPosition(position) as String
                    movieDetailPresenter.selectDate(selectedDate)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    dateSpinner.setSelection(DEFAULT_SPINNER_INDEX)
                }
            }
    }

    private fun onSelectTimeListener() {
        timeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long,
                ) {
                    val selectedTime = parent.getItemAtPosition(position) as String
                    movieDetailPresenter.selectTime(selectedTime)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    timeSpinner.setSelection(DEFAULT_SPINNER_INDEX)
                }
            }
    }

    fun subCount() {
        movieDetailPresenter.minusReservationCount()
        updateBinding()
    }

    fun addCount() {
        movieDetailPresenter.plusReservationCount()
        updateBinding()
    }

    private fun updateBinding() {
        binding.invalidateAll()
    }

    override fun moveToSeatSelection(
        reservationCount: Int,
        title: String,
    ) {
        val intent = Intent(this, SeatSelectionActivity::class.java)
        intent.putExtra(TITLE_KEY, title)
        intent.putExtra(RESERVATION_COUNT_KEY, reservationCount)
        startActivity(intent)
    }

    companion object {
        const val DEFAULT_MOVIE_ID = -1
        const val DEFAULT_THEATER_ID = -1
        const val SIS_COUNT_KEY = "count"
        const val DEFAULT_SPINNER_INDEX = 0
        const val TITLE_KEY = "title"
        const val RESERVATION_COUNT_KEY = "reservationCount"
    }
}
