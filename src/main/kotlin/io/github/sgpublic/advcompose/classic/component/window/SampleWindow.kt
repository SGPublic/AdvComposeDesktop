package io.github.sgpublic.advcompose.classic.component.window

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import io.github.sgpublic.advcompose.classic.component.prop.SampleWindowProp
import io.github.sgpublic.advcompose.core.window.ComposeWindow

class SampleWindow: ComposeWindow<SampleWindowProp>() {
    @Composable
    override fun compose() {
        Text("Hello World.")
    }
}