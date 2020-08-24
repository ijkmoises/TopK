package br.com.emesistemas.topk.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UiStateViewModel : ViewModel(){

    val componentsLiveData: LiveData<UiComponent> get() = _component

    private var _component: MutableLiveData<UiComponent> =
        MutableLiveData<UiComponent>().also {
            it.value = hasComponent
        }

    var hasComponent: UiComponent = UiComponent()
        set(value) {
            field = value
            _component.value = value
        }
}

class UiComponent(
    val appBar:Boolean = true,
    val homeAsUpButton :Boolean = false,
    val titleToolbar : Boolean = false
)