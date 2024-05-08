package woowacourse.movie.presentation.ui.detail.adapter

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import java.time.LocalTime

class SpinnerTimeAdapter(
    context: Context,
    private val actionHandler: SpinnerTimeActionHandler,
) : ArrayAdapter<LocalTime>(context, android.R.layout.simple_spinner_item) {
    fun initClickListener(): OnItemSelectedListener {
        return object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long,
            ) {
                actionHandler.registerTime(parent.getItemAtPosition(position) as LocalTime)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    fun updateTime(newItems: List<LocalTime>) {
        clear()
        addAll(newItems)
        notifyDataSetChanged()
    }
}
