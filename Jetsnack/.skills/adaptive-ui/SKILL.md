---
name: adaptive-ui
description: Instructions to make or update an app's UI so that it adapts to different Android devices including phones, tablets, foldables, laptops, desktop, TV, Auto and XR. It includes how to handle different window sizes, pointing devices (such as mouse) and text entry devices (such as keyboard) using the Compose MediaQuery API. It also covers multi-pane layouts using Navigation3 Scenes, adaptive UI components (such as buttons) where touch target sizes can change and adaptive layouts (including common navigation areas - nav rails and nav bars) using the Compose Grid and FlexBox APIs.
---

# A guide to making your app adaptive

## Prerequisites
The app should be using Jetpack Navigation 3. If it's not, use the Navigation 3 skill to migrate the app. 

## Workflow

- Step 1: Verify current UI
- Step 2: Make the navigation bar adaptive
- Step 3: Add multi-pane layouts
- Step 4: Make vertical lists adaptive by changing the number of columns 
- Step 5: TODO: FlexBox
- TODO: Add UI check? https://developer.android.com/develop/ui/compose/tooling/debug#compose_ui_check
- TODO: Handle "fix landscape" prompt from Jose's eval

## Step 1. Verify current UI
Ensure that screenshot tests exist to verify the current UI on different form factors. If they don't exist, add them. You can use the following annotation to create previews for all the major form factors. For example:

```kotlin
@Preview(name = "Phone", device = Devices.PHONE, showBackground = true)
@Preview(name = "Foldable", device = Devices.FOLDABLE, showBackground = true)
@Preview(name = "Tablet", device = Devices.TABLET, showBackground = true)
@Preview(name = "Desktop", device = Devices.DESKTOP, showBackground = true)
annotation class FormFactorPreviews

@PreviewTest
@FormFactorPreviews
@Composable
fun FeedScreenPreview() {
    MyApplicationTheme {
        FeedScreen(
            items = dummyPics,
            onItemClick = {},
        )
    }
}
```

## Step 2. Make the navigation bar adaptive
A navigation bar is a common area in the app UI that sits at the bottom of the screen and allows the user to navigate between the top level destinations in the app. It is optimized for touch input when the user is holding a phone in portrait mode - they can use their thumb to access the main parts of the app.

On larger screen hand-held devices, like tablets and unfolded foldables, the user usually holds the device from the side, so the navigation area should be easily accessible from the edge of the screen. An optimal layout is to place the navigation items (the icons and text that the user taps or clicks on) in a vertical layout on the left hand edge of the screen. This is known as a navigation rail.

The navigation area is sometimes hidden to provide more screen real estate to the content. Common examples of this include: 

- Hiding the navigation bar when the user scrolls down and showing it again when the user scrolls up. The assumption is that when the user is scrolling down, they are consuming content but when scrolling up they are trying to navigate away from that content. 
- Hiding the navigation area when its content is distracting. For example, in camera previews or when the content is best displayed in full screen (such as a single photo screen). 

When the detail screen is displayed full-screen on mobile, full-screen mode must be deactivated on larger screens.

Steps to migrate:

- Locate the existing navigation bar.
- Convert each item to a `NavigationSuiteItem`.
- Identify whether the navigation bar's visibility changes. For example, if it is wrapped with an `AnimatedContent` or `AnimatedVisibility` composable. If so, follow the guidance in the "Control navigation area visibility". 
- Replace the container that held the navigation bar (often a `Scaffold`) with `NavigationSuiteScaffold` from the Material 3 adaptive layouts library.
- Supply the navigation items using the `navigationItems` parameter of `NavigationSuiteScaffold`.

### Step 2.1. Control navigation area visibility
If the navigation bar's visibility changes - it is hidden under certain scenarios or on certain screens - this behavior must be maintained with the adaptive navigation area. This is done using `NavigationSuiteScaffold`'s `state` parameter. 

Steps to migrate: 

- Identify the scenarios under which the navigation bar is hidden. This is usually done with a boolean variable for the visibility. It could be named something like `isNavBarVisible` or `shouldShowNavBar`. 
- Create an instance of `NavigationSuiteScaffoldState` using `rememberNavigationSuiteScaffoldState()` and pass it to `NavigationSuiteScaffold`.
- When the navigation area visibility changes, use a `LaunchedEffect` to call `show` or `hide` on the `NavigationSuiteScaffoldState`.

For example: 

```
val isNavBarVisible : Boolean = ...
val scaffoldVisibilityState = rememberNavigationSuiteScaffoldState()

NavigationSuiteScaffold(
    navigationSuiteItems = ...,
    state = scaffoldVisibilityState
) { ... }

LaunchedEffect(isNavBarVisible){
    if (isNavBarVisible) {
        scaffoldVisibilityState.show()
    } else {
        scaffoldVisibilityState.hide()
    }
}
```

## Step 3. Add multi-pane layouts
Analyze the codebase looking for screens that have a parent-child relationship. There are two common types of parent-child relationship: list-detail and supporting pane. 

### Step 3.1. List-detail

#### Identify the list and detail screens
List-detail can be identified by a screen that displays a list of items (this is the list screen) and clicking on an item opens a new screen (the detail screen). 

Typical usage includes productivity apps like email, notes, and messaging. 

Unless requested explicitely, avoid this pattern when the detail content requires substantial screen space (e.g., large images or media that benefits from a full-screen presentation).

#### Migrate the screens to a Nav3 list-detail
Migrate the screens to use a Material `ListDetailScene` recipe using the Navigation 3 skill making sure to add a placeholder to the list screen. 

When a detail screen is displayed full-screen on mobile, full-screen mode must be deactivated if it's part of a list-detail layout.

Detail screens should not show a back arrow when on a list-detail layout.

### Step 3.2. Supporting pane
Supporting pane screens can be identified by a screen showing a single item that opens another screen that shows more information about the item (this is the supporting screen). This screen _supports_ the main screen and should be displayed in a supporting pane. Migrate the screens to a two pane layout using the Material `SupportingPaneScene` recipe from the Navigation 3 skill. 

### Step 3.3. Run screenshot tests
If you have made changes, run the screenshot tests, some should fail. Ask the user to visually verify that the new layouts are correct.

## Step 4. Make vertical lists adaptive by changing the number of columns 
This only applies when a list screen needs to adapt to different screen sizes. If the list screen is part of a list-detail scene, skip this step entirely. 

### Step 4.1. Migrate non-lazy lists to Grid 
Look for any `Column` that contains multiple items of the same type and replace it with `Grid`. This is a new experimental API available from Compose 1.11.0-beta01 onwards. Do not replace it `LazyVerticalGrid` or any other lazy layout.  Do not place `Grid` inside the existing `Column`. Completely replace it. 

`Grid` is configured by supplying a lambda (an extension function on `GridConfigurationScope`) to its `config` parameter. Inside the lambda, `constraints` provides the minimum and maximum dimensions of the grid container and can be used to change the number of rows and columns based on the available size. For example, the following code configures `Grid` such that when the available width is: 

- less than 600dp, a 2x4 grid is used
- between 600dp and 800dp, a 3x3 grid is used
- 800dp or more, a 4x2 grid is used

```
Grid(
    config = {
        val maxWidthDp = constraints.maxWidth.toDp()
        val (cols, rows) = if (maxWidthDp.value < 600) {
            listOf(2, 4)
        } else if (maxWidthDp.value < 800) {
            listOf(3, 3)
        } else {
            listOf(4, 2)
        }

        val gapSizeDp = 8.dp
        val availableWidthForColumns = maxWidthDp.value - (cols - 1) * gapSizeDp.value
        val cellSize = availableWidthForColumns / cols

        repeat(cols) { column(cellSize.dp) }
        repeat(rows) { row(cellSize.dp) }
        gap(gapSizeDp)
    }
) { ... items ... }
```

`Grid` is an experimental API so add the  `@OptIn(ExperimentalGridApi::class)` annotation to any function that uses it.

### Step 4.2. Make lazy lists adaptive

Look for the following vertical list composables: `LazyColumn`, `LazyVerticalGrid`, `LazyVerticalStaggeredGrid`.

Steps to migrate: 

- Choose a suitable minimum width in dp for the column. It should be large enough so that item is clearly visible to the user. 
- For `LazyColumn`: change to a `LazyVerticalGrid` and follow the instruction below
- For `LazyVerticalGrid`: change the `columns` parameter to use `GridCells.Adaptive(<width>.dp)`
- For `LazyVerticalStaggeredGrid`: change the `columns` parameter to use `StaggeredGridCells.Adaptive(<width>.dp)`

## Step 5: Collapsible App Bar

In an app with multiple top-level destinations, each screen should manage its own app bar state independently. There are two main scroll behaviors: 

- `exitUntilCollapsedScrollBehavior`: Hides on scroll down, stays hidden while you scroll up until you reach the very top (0 offset).
- `enterAlwaysScrollBehavior`: Hides on scroll down, shows immediately on scroll up.

## Final step: Build and test

Build the app and run the local tests. If the project has screenshot tests, run them but DO NOT update the reference images. Prompt the user to do this after they have viewed the screenshot diffs. 

## Additional documentation

### FlexBox

Check the [FlexBox documentation](references/flexbox/).


# TODO
- soft keyboard handling
- 