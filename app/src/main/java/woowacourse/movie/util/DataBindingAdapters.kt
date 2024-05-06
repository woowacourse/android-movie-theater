package woowacourse.movie.util

import android.R
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DataBindingAdapters {
    @BindingAdapter("imageResource")
    @JvmStatic
    fun setImageViewResource(
        imageView: ImageView,
        resourceId: Int,
    ) {
        imageView.setImageResource(resourceId)
    }
    
    @JvmStatic
    @BindingAdapter("formattedDate")
    fun setFormattedDate(textView: TextView, date: LocalDate?) {
        textView.text = date?.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) ?: ""
    }
    
    @JvmStatic
    @BindingAdapter("entries")
    fun setSpinnerEntries(spinner: Spinner, entries: List<Any>?) {
        val adapter = ArrayAdapter(spinner.context, R.layout.simple_spinner_dropdown_item, entries ?: emptyList())
        spinner.adapter = adapter
    }
    
    @JvmStatic
    @BindingAdapter("onItemSelected")
    fun setSpinnerItemSelectedListener(spinner: Spinner, itemSelectedListener: AdapterView.OnItemSelectedListener?) {
        spinner.onItemSelectedListener = itemSelectedListener
    }
}
