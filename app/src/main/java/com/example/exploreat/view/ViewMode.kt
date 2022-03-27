package com.example.exploreat.view


sealed class ViewMode<T> (val data: T? = null) {

    class ExplorationMode<T>() : ViewMode<T>(null)
    class NavigationMode<T>(data: T?) : ViewMode<T>(data)

}
