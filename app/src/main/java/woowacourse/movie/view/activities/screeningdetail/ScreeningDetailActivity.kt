package woowacourse.movie.view.activities.screeningdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import woowacourse.movie.databinding.ActivityScreeningDetailBinding
import woowacourse.movie.repository.ScreeningRepository
import woowacourse.movie.repository.TheaterRepository
import woowacourse.movie.view.activities.common.BackButtonActivity
import woowacourse.movie.view.activities.seatselection.SeatSelectionActivity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.properties.Delegates

class ScreeningDetailActivity : BackButtonActivity(), ScreeningDetailContract.View {

    private val binding: ActivityScreeningDetailBinding by lazy {
        ActivityScreeningDetailBinding.inflate(layoutInflater)
    }

    private val presenter: ScreeningDetailContract.Presenter by lazy {
        ScreeningDetailPresenter(
            this,
            intent.getLongExtra(SCREENING_ID, -1),
            intent.getLongExtra(THEATER_ID, -1),
            TheaterRepository,
            ScreeningRepository
        )
    }
    private var timeSpinnerPosition: Int = 0
    private var audienceCount: Int by Delegates.observable(1) { _, _, new ->
        binding.audienceCountTv.text = new.toString()
    }
    private var savedInstanceState: Bundle? = null
    private val screeningDetailViewHolder by lazy { ScreeningDetailViewHolder(binding) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        this.savedInstanceState = savedInstanceState

        presenter.loadScreeningData()
        initAudienceCountTextView()
        initSeatSelectionButtonOnClickListener()
    }

    private fun initAudienceCountTextView() {
        binding.audienceCountTv.text = audienceCount.toString()
    }

    private fun initSeatSelectionButtonOnClickListener() {
        binding.seatSelectionBtn.setOnClickListener { startSeatSelectionActivity() }
    }

    private fun startSeatSelectionActivity() {
        fun getSelectedScreeningDateTime(): LocalDateTime {
            val selectedDate = binding.dateSpinner.selectedItem as LocalDate
            val selectedTime = binding.timeSpinner.selectedItem as LocalTime
            return LocalDateTime.of(selectedDate, selectedTime)
        }

        SeatSelectionActivity.startActivity(
            this,
            intent.getLongExtra(SCREENING_ID, -1),
            getSelectedScreeningDateTime(),
            audienceCount,
            intent.getLongExtra(THEATER_ID, -1)
        )
    }

    override fun setScreening(screeningDetailUIState: ScreeningDetailUIState) {
        screeningDetailViewHolder.bind(screeningDetailUIState)

        initSpinners(screeningDetailUIState.screeningDateTimes)

        initAudienceCountAdjustButtons(screeningDetailUIState.maxAudienceCount)
    }

    private fun initSpinners(screeningDateTimes: Map<LocalDate, List<LocalTime>>) {
        fun initTimeSpinner(screeningTimes: List<LocalTime>) {
            binding.timeSpinner.adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                screeningTimes
            )
            binding.timeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
        binding.dateSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            screeningDates
        )
        binding.dateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                    binding.timeSpinner.setSelection(this.getInt(TIME_SPINNER_POSITION))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    private fun initAudienceCountAdjustButtons(maxAudienceCount: Int) {
        binding.minusAudienceCountBtn.setOnClickListener {
            if (audienceCount > 1) audienceCount--
        }
        binding.plusAudienceCountBtn.setOnClickListener {
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
        const val THEATER_ID = "THEATER_ID"
        private const val TIME_SPINNER_POSITION = "TIME_SPINNER_POSITION"
        private const val AUDIENCE_COUNT = "AUDIENCE_COUNT"

        fun startActivity(context: Context, screeningId: Long, theaterId: Long) {
            val intent = Intent(context, ScreeningDetailActivity::class.java).apply {
                putExtra(SCREENING_ID, screeningId)
                putExtra(THEATER_ID, theaterId)
            }
            context.startActivity(intent)
        }
    }
}
