package woowacourse.movie.seatselection.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import woowacourse.movie.R
import woowacourse.movie.data.db.ReservationHistoryDatabase
import woowacourse.movie.databinding.ActivityMovieSeatSelectionBinding
import woowacourse.movie.model.MovieGrade
import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats
import woowacourse.movie.result.view.MovieResultActivity
import woowacourse.movie.seatselection.presenter.MovieSeatSelectionPresenter
import woowacourse.movie.seatselection.presenter.contract.MovieSeatSelectionContract
import woowacourse.movie.seatselection.view.listener.MovieSeatSelectionClickListener
import woowacourse.movie.util.Formatter.formatColumn
import woowacourse.movie.util.Formatter.formatRow
import woowacourse.movie.util.MovieIntent.MOVIE_DATE
import woowacourse.movie.util.MovieIntent.MOVIE_ID
import woowacourse.movie.util.MovieIntent.MOVIE_TIME
import woowacourse.movie.util.MovieIntent.RESERVATION_COUNT
import woowacourse.movie.util.MovieIntent.SELECTED_SEAT_POSITIONS
import woowacourse.movie.util.MovieIntent.SELECTED_THEATER_POSITION

class MovieSeatSelectionActivity :
    AppCompatActivity(),
    MovieSeatSelectionContract.View,
    MovieSeatSelectionClickListener {
    private lateinit var binding: ActivityMovieSeatSelectionBinding
    private lateinit var seatSelectionPresenter: MovieSeatSelectionPresenter

    private val tableSeats: List<TextView> by lazy {
        findViewById<TableLayout>(R.id.table_layout_seat).children.filterIsInstance<TableRow>()
            .flatMap { tableRow ->
                tableRow.children.filterIsInstance<TextView>().toList()
            }.toList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityMovieSeatSelectionBinding.inflate(layoutInflater)
        binding.seatClickListener = this
        setContentView(binding.root)

        seatSelectionPresenter =
            MovieSeatSelectionPresenter(
                this,
                ReservationHistoryDatabase.getInstance(applicationContext),
            )
        seatSelectionPresenter.loadDetailMovie(
            intent.getLongExtra(
                MOVIE_ID.key,
                MOVIE_ID.invalidValue as Long,
            ),
        )
        seatSelectionPresenter.loadTableSeats(
            intent.getIntExtra(
                RESERVATION_COUNT.key,
                RESERVATION_COUNT.invalidValue as Int,
            ),
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val count = seatSelectionPresenter.movieSelectedSeats.count
        outState.putInt(RESERVATION_COUNT.key, count)

        val selectedPositions = seatSelectionPresenter.movieSelectedSeats.getSelectedPositions()
        outState.putIntArray(SELECTED_SEAT_POSITIONS.key, selectedPositions)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val savedCount =
            savedInstanceState.getInt(
                RESERVATION_COUNT.key,
                RESERVATION_COUNT.invalidValue as Int,
            )
        seatSelectionPresenter.updateSelectedSeats(savedCount)

        val selectedPositions = savedInstanceState.getIntArray(SELECTED_SEAT_POSITIONS.key)
        setUpSelectedSeats(selectedPositions)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun displayMovieTitle(movieTitle: String) {
        binding.movieTitle = movieTitle
    }

    override fun setUpTableSeats(baseSeats: List<MovieSeat>) {
        tableSeats.forEachIndexed { index, view ->
            val seat = baseSeats[index]
            view.text = getString(R.string.seat, seat.row.formatRow(), seat.column.formatColumn())
            view.setTextColor(ContextCompat.getColor(this, seat.grade.getSeatColor()))
            view.setOnClickListener {
                seatSelectionPresenter.clickTableSeat(index)
            }
        }
    }

    override fun updateSeatBackgroundColor(
        index: Int,
        isSelected: Boolean,
    ) {
        val backGroundColor = if (isSelected) R.color.white else R.color.selected
        tableSeats[index].setBackgroundColor(ContextCompat.getColor(this, backGroundColor))
    }

    override fun updateSelectedSeats(movieSelectedSeats: MovieSelectedSeats) {
        binding.movieSelectedSeats = movieSelectedSeats
    }

    override fun navigateToResultView(ticketId: Long) {
        val intent =
            MovieResultActivity.createIntent(
                baseContext,
                ticketId,
            )
        startActivity(intent)
    }

    private fun setUpSelectedSeats(selectedPositions: IntArray?) {
        selectedPositions?.forEach { position ->
            seatSelectionPresenter.clickTableSeat(position)
        }
    }

    private fun MovieGrade.getSeatColor(): Int {
        return when (this) {
            MovieGrade.B_GRADE -> R.color.b_grade
            MovieGrade.S_GRADE -> R.color.s_grade
            MovieGrade.A_GRADE -> R.color.a_grade
        }
    }

    override fun onCompleteButtonClick() {
        AlertDialog.Builder(this).setTitle("예매 확인").setMessage("정말 예매하시겠습니까?")
            .setPositiveButton("예매 완료") { _, _ ->
                seatSelectionPresenter.clickPositiveButton(
                    intent?.getLongExtra(MOVIE_ID.key, MOVIE_ID.invalidValue as Long)
                        ?: MOVIE_ID.invalidValue as Long,
                    intent?.getStringExtra(MOVIE_DATE.key) ?: MOVIE_DATE.invalidValue as String,
                    intent?.getStringExtra(MOVIE_TIME.key) ?: MOVIE_TIME.invalidValue as String,
                    intent?.getIntExtra(
                        SELECTED_THEATER_POSITION.key,
                        SELECTED_THEATER_POSITION.invalidValue as Int,
                    ) ?: SELECTED_THEATER_POSITION.invalidValue as Int,
                )
            }
            .setNegativeButton("취소") { dialog, _ -> dialog.dismiss() }.setCancelable(false).show()
    }

    companion object {
        fun createIntent(
            context: Context,
            movieId: Long,
            date: String,
            time: String,
            count: Int,
            theaterPosition: Int,
        ): Intent {
            return Intent(context, MovieSeatSelectionActivity::class.java).apply {
                putExtra(MOVIE_ID.key, movieId)
                putExtra(MOVIE_DATE.key, date)
                putExtra(MOVIE_TIME.key, time)
                putExtra(RESERVATION_COUNT.key, count)
                putExtra(SELECTED_THEATER_POSITION.key, theaterPosition)
            }
        }
    }
}
