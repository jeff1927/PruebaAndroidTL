# Prueba Tecnica TL

## Project Overview
The project intends to follow the structure of MVVM´s architecture pattern, using some of the main developer tools of the Android Architecture Components.

This project is divided in four layers:

## Data Layer
In this layer it can be found, the API service interface, data transfer objects, data sources and the repository implementation.
this layer is the responsible of the origin of the data, here the implementation of the repository allows to retrieve the data of the different Apis

## Domain Layer
This layer contains the Models of the app for the movies and the routes, repository interfaces and the app´s use cases. 
Domain layer is the responsible of save the business logic. 

## UI Layer
Visual Components of the app are in this layer: Adapters, fragments and view models are localized here.
there are two responsibilities on this layer, first one fragments and activities are responsible of actualize the UI, on the other hand view model is responsible to persistence of the data that is being shows in the screen.

## DI Layer
Modules for dependency injection are here

### Used libraries: ###

- [Coroutines](https://developer.android.com/topic/libraries/architecture/coroutines?hl=es-419)
- [Retrofit2](https://github.com/square/retrofit)
- [Gson](https://github.com/google/gson)
- [DAGGER HILT](https://developer.android.com/training/dependency-injection/hilt-android?hl=es-419)
- [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)
- [View Model](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Live data](https://developer.android.com/topic/libraries/architecture/livedata.html)
- [Kotlin Flows](https://developer.android.com/kotlin/flow)
- [Navigation](https://developer.android.com/guide/navigation)
- [Room](https://developer.android.com/jetpack/androidx/releases/room)

## Instructions to build this project:

In order to limit the access to the google API, this project would should to put the PUBLIC_API_KEY in the local properties file, just like the following video shows, 
for easiest way to build the project, the API keys are exposed but also restricted and it will be delete after the evaluator check the project.

- [How to keep your Apy_Keys safe](https://www.youtube.com/watch?v=X8lYNW_Or2o)

### CompileSDK : 32 ###

### Android Studio Bumblebee | 2021.1.1 Patch 3
Build #AI-211.7628.21.2111.8309675, built on March 16, 2022
Runtime version: 11.0.11+9-b60-7590822 amd64
VM: OpenJDK 64-Bit Server VM by Oracle Corporation
Windows 10 10.0
GC: G1 Young Generation, G1 Old Generation
Memory: 1280M
Cores: 4
Registry: external.system.auto.import.disabled=true, ide.balloon.shadow.size=0
Non-Bundled Plugins: cn.yiiguxing.plugin.md.palette (1.4), wu.seal.tool.jsontokotlin (3.7.2), org.jetbrains.kotlin (211-1.6.20-release-275-AS7442.40), org.intellij.plugins.markdown (211.7142.37) 

# Screen Shots of User Interface

## Home View:









