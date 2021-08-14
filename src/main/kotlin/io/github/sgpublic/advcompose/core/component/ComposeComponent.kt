package io.github.sgpublic.advcompose.core.component

import androidx.compose.runtime.Composable
import io.github.sgpublic.advcompose.core.ComposeBase
import kotlin.reflect.KClass

abstract class ComposeComponent<Prop: ComponentProp>: ComposeBase<Prop>() {
    private var created: Boolean = false
    @Composable
    private fun createComponent(){
        onComponentUpdate()
        compose()
        if (created){
            return
        }
        created = true
        onComponentCreated()
    }

    companion object {
        @JvmStatic
        @Composable
        fun createComponent(componentClass: KClass<out ComposeComponent<out ComponentProp>>) {
            componentClass.java.getDeclaredConstructor()
                .newInstance()
                .createComponent()
        }
    }
}