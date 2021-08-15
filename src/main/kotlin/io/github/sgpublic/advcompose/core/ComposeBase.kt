package io.github.sgpublic.advcompose.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.lang.reflect.ParameterizedType

abstract class ComposeBase<Prop: PropBase> {
    private val propState: MutableState<Prop>
    protected val prop: Prop get() = propState.value

    constructor(){
        @Suppress("UNCHECKED_CAST")
        val propInterface = ((javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<Prop>)
            .getDeclaredConstructor().newInstance()
        this.propState = mutableStateOf(propInterface)
    }

    constructor(prop: Prop){
        this.propState = mutableStateOf(prop)
    }

    @Composable
    protected abstract fun compose()

    @Composable
    protected open fun onComponentUpdate() {

    }

    @Composable
    protected open fun onComponentCreated() {

    }

    final override fun toString(): String {
        return this.javaClass.name
    }
}