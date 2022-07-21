package preview.android.activity.util

import androidx.lifecycle.MutableLiveData

class MutableListLiveData<T>  : MutableLiveData<List<T>>(){
    private val tempList = mutableListOf<T>()

    init {
        value = tempList
    }

    fun add(item: T) {
        tempList.add(item)
        value = tempList
    }

    fun addAll(items: List<T>) {
        tempList.addAll(items)
        value = tempList
    }

    fun remove(item: T) {
        tempList.remove(item)
        value = tempList
    }

    fun clear() {
        tempList.clear()
        value = tempList
    }
}