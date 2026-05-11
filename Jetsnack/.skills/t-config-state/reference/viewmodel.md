# ViewModel

Use this document to create the `ViewModel` for the user.

## Step 1. Read the docs

1. Read the official Android documentation about [how to create a ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel).
2. Read the documentation about [how to create a `ViewModel` factory](https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-factories)
3. Read the documentation about [how to create a repository](https://developer.android.com/topic/architecture/data-layer).

## Step 2. Add the Gradle dependencies

```groovy
def lifecycle_version = "2.10.0" // Or latest version if higher

// ViewModel
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
// ViewModel utilities for Compose
implementation "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version"
// Saved state module for ViewModel
implementation "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version"
// ViewModel integration with Navigation3
implementation "androidx.lifecycle:lifecycle-viewmodel-navigation3:2.11.0-alpha02"
```

## Step 3. Create the ViewModel file and class

Create a `ViewModel` for each logical screen in the app, as a screen-level state holder. Name the `ViewModel` after the screen that it provides the implementation details for. Put the `ViewModel` in a separate file from where it is used.

Examples:

+ `ReadingScreen.kt` has a `ReadingViewModel.kt` file that contains the `ReadingViewModel` class.
+ `PurchaseScreen.kt` has a `PurchaseViewModel.kt` file that contains the `PurchaseViewModel` class.

## Step 4. Create the UI data class

Next, create the UI state `data class` that the `ViewModel` exposes to the UI. Declare and define the UI state `data class` in the same file as the `ViewModel`. Name the `data class` so that it corresponds to its `ViewModel`. Create a backing field in the UI state `data class` for each UI component that the `ViewModel` stores state

```kotlin
data class ReadingUiState(
    val content: String = "",
    val bookInformation: String = "",
    val pageNumber: Int = 0,
    val chapterNumber: Int = 0,
    val isFinishedReading: Boolean = false,
)

class ReadingViewModel : ViewModel () {
    private val _uiState = MutableStateFlow(ReadingUiState())
    val uiState: StateFlow<ReadingUiState> get() = _uiState
    
    // Other implementation details
}
```

## Step 5. Add mutators to the `ViewModel` class

Define methods on the `ViewModel` class that updates the underlying data based upon changes in the UI.

```kotlin
class ReadingViewModel : ViewModel () {
    private val _uiState = MutableStateFlow(ReadingUiState())
    val uiState: StateFlow<ReadingUiState> get() = _uiState
    
    fun turnPage() {
        _uiState.update {
            currentState.copy(
                pageNumber = currentState.pageNumber + 1,
                content = getContentForPage(currentState.pageNumber + 1)
            )
        }
    }
}
```

## Step 6. Create a repository for the backing data

The repository connects to the data source of the app. This is part of the app's _data layer_. This repository can be used by multiple different screens in the app. Do not name the repository after the ViewModel. The data source can be as simple as a list of items or as complex as a connection to a database.


Define the data sources or connections to data sources in the repository. Create one repository for each data type in your app. The `Repository` class should be declared and defined in its own file.

```kotlin
class BookRepository(val readingSource: ReadingDataSource) {

    val books = mutableStateListOf(
        "The Republic" to "Plato's description of how politics could work",
        "The Prince" to "Niccolo Machiavelli's description of how politics really works"
    )

    // Or connect to the data source

    suspend fun fetchContentForPage(chapter: Int, page: Int): String {
        // Implementation details
    }
}
```

Update the `ViewModel` class so that it uses the repository. Create a `Factory()` method that accepts an instance of the repository as a parameter. Then store the instance of the repository in your `ViewModel`.

```kotlin
class ReadingViewModel {
    // Define ViewModel factory in a companion object
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return ReadingViewModel(
                    (application as MyApplication).readingRepository,
                    savedStateHandle
                ) as T
            }
        }
    }
}

```

Finally, update the mutators in your `ViewModel` so that it updates the repository.

```kotlin
class ReadingViewModel {
    private val repository: BookRepository
    
    fun turnPage() {
        viewmodelScope.launch {
            val content = repository.fetchContentForPage(
                uiState.chapterNumber, uiState.pageNumber)
        }
    }
}

```

## Step 7. Update the UI to use the `ViewModel`

Create an instance of the `ViewModel` from the `onCreate()` override of the `Activity` class that hosts the UI.

```kotlin
class MyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val readingViewModel: ReadingViewModel by viewModels()
    }
}
```

Get a reference to the `ViewModel` from inside the composable that uses its data. Specify the activity as the `ViewModelStoreOwner` of the `ViewModel`.

```kotlin
@Composable
fun ReadingScreen(
    context: Context = LocalContext.current,
    viewModel: ReadingViewModel = viewModel(
        viewModelStoreOwner = (context as MyActivity)
    )
)
```

**IMPORTANT**: If the `ViewModel` does not need to be preserved across the activity lifecycle, **stop now**.

## Step 8. Update the ViewModel for SavedState

Go to the [next document](viewmodel-saved-state.md) to create the `SavedStateHandle` for the `ViewModel`.
