package com.ortizzakarie.dealfinder.viewModel.searchResult.searchView

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.SearchView

/**
 * Created by Zakarie Ortiz on 1/13/21.
 */

/**
 * Custom class for extending search view so I can implement code to give it a empty submit listener.
 * Problem: Base SearchView does not return a submit event if the query is empty or blank by default.
 */

class EmptySubmitSearchView : SearchView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    private lateinit var searchSourceTextView : SearchAutoComplete
    private lateinit var listener : OnQueryTextListener

    override fun setOnQueryTextListener(listener: OnQueryTextListener?) {
        super.setOnQueryTextListener(listener)

        // This is where & how to intercept a search query if it is blank or empty.
        // Find the searchSourceTextView that is submitting the query text.
        // And then activate the listener.onQueryTextSubmit so that you can handle a empty search query however you want.
        if (listener != null) {
            this.listener = listener
            searchSourceTextView = this.findViewById(androidx.appcompat.R.id.search_src_text)
            searchSourceTextView.setOnEditorActionListener { textView, i, keyEvent ->
                listener.onQueryTextSubmit(query.toString())
            }
        }
    }
}