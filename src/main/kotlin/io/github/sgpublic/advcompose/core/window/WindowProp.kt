package io.github.sgpublic.advcompose.core.window

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import io.github.sgpublic.advcompose.core.PropBase

abstract class WindowProp: PropBase() {
    val window: WindowPropInternal by mutableStateOf(WindowPropInternal())

    companion object {
        class WindowPropInternal {
            var onCloseRequest: () -> Unit by mutableStateOf({ })
            var state: WindowState by mutableStateOf(object : WindowState {
                override var placement: WindowPlacement by mutableStateOf(WindowPlacement.Floating)
                override var isMinimized: Boolean by mutableStateOf(false)
                override var position: WindowPosition by mutableStateOf(WindowPosition.PlatformDefault)
                var width: Dp by mutableStateOf(800.dp)
                var height: Dp by mutableStateOf(600.dp)
                override var size: WindowSize by mutableStateOf(WindowSize(width, height))
            })
            var visible: Boolean by mutableStateOf(true)
            var title: String by mutableStateOf("Untitled")
            var icon: Painter? by mutableStateOf(null)
            var undecorated: Boolean by mutableStateOf(false)
            var resizable: Boolean by mutableStateOf(true)
            var enabled: Boolean by mutableStateOf(true)
            var focusable: Boolean by mutableStateOf(true)
            var alwaysOnTop: Boolean by mutableStateOf(false)
            var onPreviewKeyEvent: (KeyEvent) -> Boolean by mutableStateOf({ false })
            var onKeyEvent: (KeyEvent) -> Boolean by mutableStateOf({ false })

            val theme by mutableStateOf(MaterialThemeProp())
            class MaterialThemeProp {
                var colors: Colors by mutableStateOf(lightColors())
                var typography: Typography by mutableStateOf(Typography())
                var shapes: Shapes by mutableStateOf(Shapes())
            }
        }
    }
}
