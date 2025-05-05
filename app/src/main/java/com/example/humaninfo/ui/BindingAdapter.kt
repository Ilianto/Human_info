package com.example.humaninfo.ui

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.example.humaninfo.model.Sex

@BindingAdapter("selectedValue")
fun Spinner.setSelectedValue(value: Sex?) {
    val index = (adapter as? ArrayAdapter<Sex>)?.getPosition(value)
    if (index != null && index >= 0) {
        setSelection(index)
    }
}

@InverseBindingAdapter(attribute = "selectedValue")
fun Spinner.getSelectedValue(): Sex? {
    return selectedItem as? Sex
}

@BindingAdapter("selectedValueAttrChanged")
fun Spinner.setListeners(listener: InverseBindingListener) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            listener.onChange()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
}
