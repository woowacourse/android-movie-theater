package woowacourse.movie.presentation.ui.detail.adapter

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import woowacourse.movie.domain.model.ScreenDate
import java.time.LocalDate

class SpinnerDateAdapter(
    context: Context,
    private val spinnerActionHandler: SpinnerActionHandler,
) : ArrayAdapter<LocalDate>(context, android.R.layout.simple_spinner_item) {
    fun initClickListener(preDate: LocalDate?): OnItemSelectedListener {
        return object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long,
            ) {
                val localDate = parent.getItemAtPosition(position) as LocalDate

                if (preDate != localDate) {
                    spinnerActionHandler.registerDate(localDate)
                    spinnerActionHandler.createTimeSpinnerAdapter(ScreenDate(localDate))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    fun updateDate(newItems: List<LocalDate>) {
        clear()
        addAll(newItems)
        notifyDataSetChanged()
    }
}
