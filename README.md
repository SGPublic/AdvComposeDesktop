# AdvComposeDesktop


[![Latest release](https://img.shields.io/github/v/release/sgpublic/AdvComposeDesktop?color=brightgreen&label=latest%20release)](https://github.com/sgpublic/AdvComposeDesktop/releases/latest)
[![Latest build](https://img.shields.io/github/v/release/sgpublic/AdvComposeDesktop?color=orange&include_prereleases&label=latest%20build)](https://github.com/sgpublic/AdvComposeDesktop/releases)

一个基于 Jetpack Compose for Desktop 的二次封装，旨在进一步简化 Compose 开发，并加入更多功能。

## 快速入门

导入依赖

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.sgpublic:AdvComposeDesktop:latest")
}
```

简单的一句代码即可完成 AdvComposeDesktop 启动：

```kotlin
fun main(args: Array<String>) {
    AdvComposeApplication.start(args)
}
```

启动参数传入`--debug`可开启调试模式。

### 添加 Window

新建类使其继承自 SampleComposeWindow

```kotlin
class HelloWorldWindow: SampleComposeWindow() {
    @Composable
    override fun compose(){
        Text("Hello World!")
    }
}
```

或者指定自定义 Prop

```kotlin
class HelloWorldWindow: ComposeWindow<HelloWorldWindowProp>() {
    @Composable
    override fun compose(){
        Button(onClick = {
            prop.text = "Hello AdvComposeDesktop!"
        }) {
            Text(prop.text)
        }
    }
}

class HelloWorldWindowProp: WindowProp() {
    var text: String by mutableStateOf("Hello World!")
}
```

指定 MainWindow，即启动时打开的 Window，仅需要在 class 上添加 `@MainWindow` 注解即可。

_*注意：您的程序必须有一个 @MainWindow！*_

```kotlin
@MainWindow
class HelloWorldWindow: SampleComposeWindow() {
    @Composable
    override fun compose(){
        Text("Hello World!")
    }
}
```

### 使用 Component

新建类使其继承自 SampleComposeComponent

```kotlin
class MyComponent: SampleComposeComponent() {
    @Composable
    override fun compose(){
        Text("Hello World!")
    }
}
```

或者指定自定义 Prop

```kotlin
class MyComponent: ComposeComponent<MyComponentProp>() {
    @Composable
    override fun compose(){
        Button(onClick = {
            prop.text = "Hello ComposeComponent!"
        }) {
            Text(prop.text)
        }
    }
}

class MyComponentProp: ComponentProp() {
    var text: String by mutableStateOf("Hello World!")
}
```

最后在 Window 中使用

```kotlin
class HelloWorldWindow: SampleComposeWindow() {
    @Composable
    override fun compose(){
        includeComponent(MyComponent::class)
    }
}
```

