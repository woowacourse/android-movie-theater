package woowacourse.movie.view.activities.screeningdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import woowacourse.movie.R
import woowacourse.movie.view.activities.common.BackButtonActivity
import woowacourse.movie.view.activities.seatselection.SeatSelectionActivity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.properties.Delegates

class ScreeningDetailActivity : BackButtonActivity(), ScreeningDetailContract.View {

    private lateinit var presenter: ScreeningDetailContract.Presenter
    private var timeSpinnerPosition: Int = 0
    private var audienceCount: Int by Delegates.observable(1) { _, _, new ->
        findViewById<TextView>(R.id.audience_count_tv).text = new.toString()
    }
    private var savedInstanceState: Bundle? = null
    private val screeningDetailViewHolder by lazy { ScreeningDetailViewHolder(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screening_detail)
        this.savedInstanceState = savedInstanceState

        val screeningId = intent.getLongExtra(SCREENING_ID, -1)
        presenter = ScreeningDetailPresenter(this, screeningId)
        presenter.loadScreeningData()
        initAudienceCountTextView()
        initSeatSelectionButtonOnClickListener(screeningId)
    }

    private fun initAudienceCountTextView() {
        findViewById<TextView>(R.id.audience_count_tv).text = audienceCount.toString()
    }

    private fun initSeatSelectionButtonOnClickListener(screeningId: Long) {
        val seatSelectionButton = findViewById<Button>(R.id.seat_selection_btn)
        seatSelectionButton.setOnClickListener { startSeatSelectionActivity(screeningId) }
    }

    private fun startSeatSelectionActivity(screeningId: Long) {
        fun getSelectedScreeningDateTime(): LocalDateTime {
            val dateSpinner = findViewById<Spinner>(R.id.date_spinner)
            val selectedDate = dateSpinner.selectedItem as LocalDate
            val timeSpinner = findViewById<Spinner>(R.id.time_spinner)
            val selectedTime = timeSpinner.selectedItem as LocalTime
            return LocalDateTime.of(selectedDate, selectedTime)
        }

        SeatSelectionActivity.startActivity(
            this,
            screeningId,
            getSelectedScreeningDateTime(),
            audienceCount
        )
    }

    override fun setScreening(screeningDetailUIState: ScreeningDetailUIState) {
        screeningDetailViewHolder.bind(screeningDetailUIState)

        initSpinners(screeningDetailUIState.screeningDateTimes)

        initAudienceCountAdjustButtons(screeningDetailUIState.maxAudienceCount)
    }

    private fun initSpinners(screeningDateTimes: Map<LocalDate, List<LocalTime>>) {
        fun initTimeSpinner(screeningTimes: List<LocalTime>) {
            val timeSpinner = findViewById<Spinner>(R.id.time_spinner)
            timeSpinner.adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                screeningTimes
            )
            timeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    timeSpinnerPosition = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
        }

        val screeningDates = screeningDateTimes.keys.toList()
        val dateSpinner = findViewById<Spinner>(R.id.date_spinner)
        dateSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            screeningDates
        )
        dateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                initTimeSpinner(
                    screeningDateTimes[screeningDates[position]]
                        ?: throw IllegalArgumentException("${screeningDates[position]}은 상영하지 않는 날이라서 타임 스피너를 초기화할 수 없습니다.")
                )
                savedInstanceState?.run {
                    val timeSpinner = findViewById<Spinner>(R.id.time_spinner)
                    timeSpinner.setSelection(this.getInt(TIME_SPINNER_POSITION))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    private fun initAudienceCountAdjustButtons(maxAudienceCount: Int) {
        val minusButton = findViewById<Button>(R.id.minus_audience_count_btn)
        minusButton.setOnClickListener {
            if (audienceCount > 1) audienceCount--
        }
        val plusButton = findViewById<Button>(R.id.plus_audience_count_btn)
        plusButton.setOnClickListener {
            if (audienceCount < maxAudienceCount) audienceCount++
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.apply {
            putInt(TIME_SPINNER_POSITION, timeSpinnerPosition)
            putInt(AUDIENCE_COUNT, audienceCount)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        audienceCount = savedInstanceState.getInt(AUDIENCE_COUNT)
    }

    companion object {
        const val SCREENING_ID = "SCREENING_ID"
        private const val TIME_SPINNER_POSITION = "TIME_SPINNER_POSITION"
        private const val AUDIENCE_COUNT = "AUDIENCE_COUNT"

        fun startActivity(context: Context, screeningId: Long) {
            val intent = Intent(context, ScreeningDetailActivity::class.java).apply {
                putExtra(SCREENING_ID, screeningId)
            }
            context.startActivity(intent)
        }
    }
}
