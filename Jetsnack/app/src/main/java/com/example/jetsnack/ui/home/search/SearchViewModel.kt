package com.example.jetsnack.ui.home.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.example.jetsnack.model.Filter
import com.example.jetsnack.model.SearchCategoryCollection
import com.example.jetsnack.model.SearchRepo
import com.example.jetsnack.model.SearchSuggestionGroup
import com.example.jetsnack.model.Snack
import com.example.jetsnack.model.SnackRepo
import kotlinx.coroutines.launch

@OptIn(SavedStateHandleSaveableApi::class)
class SearchViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var query by savedStateHandle.saveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var focused by savedStateHandle.saveable { mutableStateOf(false) }
    var searching by savedStateHandle.saveable { mutableStateOf(false) }
    var categories by mutableStateOf(SearchRepo.getCategories())
    var suggestions by mutableStateOf(SearchRepo.getSuggestions())
    var filters by mutableStateOf(SnackRepo.getFilters())
    var searchResults by mutableStateOf<List<Snack>>(emptyList())

    val searchDisplay: SearchDisplay
        get() = when {
            !focused && query.text.isEmpty() -> SearchDisplay.Categories
            focused && query.text.isEmpty() -> SearchDisplay.Suggestions
            searchResults.isEmpty() -> SearchDisplay.NoResults
            else -> SearchDisplay.Results
        }
    
    fun performSearch() {
        viewModelScope.launch {
            searching = true
            searchResults = SearchRepo.search(query.text)
            searching = false
        }
    }
}
