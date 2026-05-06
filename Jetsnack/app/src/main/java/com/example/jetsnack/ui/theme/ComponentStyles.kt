/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jetsnack.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.style.Style
import androidx.compose.foundation.style.background
import androidx.compose.foundation.style.clip
import androidx.compose.foundation.style.contentColor
import androidx.compose.foundation.style.contentPadding
import androidx.compose.foundation.style.disabled
import androidx.compose.foundation.style.minHeight
import androidx.compose.foundation.style.minWidth
import androidx.compose.foundation.style.pressed
import androidx.compose.foundation.style.shape
import androidx.compose.foundation.style.textStyle
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.jetsnack.ui.theme.JetsnackTheme.colors
import com.example.jetsnack.ui.theme.JetsnackTheme.typography

@Immutable
data class AppStyles(
    val buttonStyle: Style = buttonStyle,
)

private val buttonStyle = Style {
    minWidth(58.dp)
    minHeight(40.dp)
    contentPadding(8.dp, 24.dp)
    background(Brush.horizontalGradient(colors.interactivePrimary))
    shape(RoundedCornerShape(50))
    clip()
    textStyle(typography.labelLarge)
    contentColor(colors.textInteractive)
    disabled {
        background(Brush.horizontalGradient(colors.interactiveSecondary))
        contentColor(colors.textHelp)
    }
    pressed {
        background(Brush.horizontalGradient(colors.interactivePrimary))
    }
}
