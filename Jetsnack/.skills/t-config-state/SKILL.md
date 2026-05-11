---
name: adaptive-ui-state-continuity
description: >
  Use retain, rememberSaveable, rememberSerializable, and ViewModel to preserve UI state and data across configuration changes.
---

# Adaptive window size classes skill

Use this document to fix issues where an app loses user data after an orientation change, configuration change, or recomposition.

**IMPORTANT**: If the app doesn't use Kotlin and Jetpack Compose, **stop now**. If the app uses the [Hilt library for dependency injection](https://developer.android.com/training/dependency-injection/hilt-android), **stop now**.

## Glossary

+ _orientation change_: the device changes from portrait to landscape, landscape to portrait, or any other 90, 180, or 270-degree rotation.
+ _configuration change_: the device folds, unfolds, or changes to book, flat, or tabletop posture.
+ _recomposition_: the process of calling your composable functions again when the UI changes, which can cause local `remember` state to be reset if the composable leaves the composition.
+ _business logic_: the real-world business rules that determine how app data must be created, stored, and changed. Read https://developer.android.com/develop/ui/compose/state-saving#business-logic.
+ _ui logic_: how to display UI state on the screen. Read https://developer.android.com/develop/ui/compose/state-saving#ui-logic.
+ _data layer_: the layer that contains application data and business logic.

## Step 1. Decide what type of data preservation strategy to use

Ask the user whether their data is:

+ UI logic
+ business logic not persisted across activities
+ business logic that is persisted across activities

After the user has answered, determine whether the data is serializable.

## Step 2. Read the data preservation strategy

Based on the app data preservation strategy, read the corresponding file and follow its instructions:

UI Logic → read [State and Jetpack Compose](https://developer.android.com/develop/ui/compose/state) and [State lifespans in Compose](https://developer.android.com/develop/ui/compose/state-lifespans). Decide whether to use `retain`, `rememberSaveable`, or `rememberSerializable`.
Business Logic, not persisted across activities → read ./reference/viewmodel.md
Business Logic, persisted across activities → read ./reference/viewmodel.md and ./reference/viewmodel-saved-state.md

## References

- [State and Jetpack Compose](https://developer.android.com/develop/ui/compose/state)
- [Save UI state in Compose](https://developer.android.com/develop/ui/compose/state-saving)
- [State lifespans in Compose](https://developer.android.com/develop/ui/compose/state-lifespans)
- [ViewModel overview](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Saved state module for `ViewModel`](https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-savedstate#savedstate-compose-state)