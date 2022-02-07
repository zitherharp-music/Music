package com.zitherharp.store.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zitherharp.store.Extension.isNetworkConnected
import com.zitherharp.store.Repository
import com.zitherharp.store.model.Item
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class ItemViewModel : ViewModel() {
    var status = "Đang tải"

//    val items = MutableLiveData<List<Item>>().apply {
//        if (isNetworkConnected()) {
//            GlobalScope.launch(Dispatchers.IO) {
//                value = Repository.APPLICATIONS.values.toList()
//                withContext(Dispatchers.Main) {
//                    status = ""
//                }
//            }
//        } else {
//            //value = null
//            status = "Không có kết nối"
//        }
//    }
}