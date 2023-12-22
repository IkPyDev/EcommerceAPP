package com.ikpydev.ecommerceapp.presention.checkout//package com.ikpydev.ecommerceapp.presention.checkout
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ArrayAdapter
//import android.widget.TextView
//
//class CountAdapter(
//    private val items: Array<String>,
//    private val onItemSelected: (String) -> Unit
//) : ArrayAdapter<String>(context, resource, items) {
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
//
//        val item = getItem(position)
//        val textView = view.findViewById<TextView>(android.R.id.text1)
//        textView.text = item
//
//        return view
//    }
//
//    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
//
//        val item = getItem(position)
//        val textView = view.findViewById<TextView>(android.R.id.text1)
//        textView.text = item
//
//        return view
//    }
//
//    // Yangi funksiya
//    fun getSelectedText(position: Int): String {
//        return getItem(position) ?: ""
//    }
//
//    // AutoCompleteTextView tanlagandagi elementni qaytaruvchi funksiya
//
//    fun onItemSelected(position: Int) {
//        val selectedText = getSelectedText(position)
//        onItemSelected.invoke(selectedText)
//    }
//
//}
