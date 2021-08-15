package io.github.sgpublic.advcompose.core.component

import io.github.sgpublic.advcompose.core.PropBase

abstract class ComponentProp: PropBase() {
    companion object {
        class SampleComponentProp: ComponentProp()
    }
}