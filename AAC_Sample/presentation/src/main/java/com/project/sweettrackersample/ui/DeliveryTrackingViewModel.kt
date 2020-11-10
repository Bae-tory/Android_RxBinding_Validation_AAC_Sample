package com.project.sweettrackersample.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.project.data.component.ErrorType1Exception
import com.project.data.component.ErrorType2Exception
import com.project.data.component.ErrorType3Exception
import com.project.data.component.Result
import com.project.data.repository.TrackingRepository
import com.project.sweettrackersample.R
import com.project.sweettrackersample.base.BaseViewModel
import com.project.sweettrackersample.component.ItemClickListener
import com.project.sweettrackersample.component.ResourceProvider
import com.project.sweettrackersample.ext.createDefault
import com.project.sweettrackersample.model.HistoryItem
import com.project.sweettrackersample.model.TrackingInfoPersentation
import com.project.sweettrackersample.model.fromData
import kotlinx.coroutines.launch
import java.util.*

class DeliveryTrackingViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val repository: TrackingRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel(), ItemClickListener {

    private val _trackingInfoPageData = MutableLiveData<TrackingInfoPersentation>()
    val trackingInfoPageData: LiveData<TrackingInfoPersentation> get() = _trackingInfoPageData

    private val _historyListData = MutableLiveData<List<HistoryItem>>()
    val historyListData: LiveData<List<HistoryItem>> get() = _historyListData

    private val _isLoadingProgressVisible = MutableLiveData<Boolean>().createDefault(true)
    val isLoadingProgressVisible: LiveData<Boolean> get() = _isLoadingProgressVisible

    init {
        reqeustTrackingInfo()
    }

    private fun reqeustTrackingInfo() {
        viewModelScope.launch(exceptionDispatchers) {
            when (val result = repository.requestRemoteTrackingInfo()) {
                is Result.OnSuccess -> {
                    val resultExceptList = result.data.fromData().first
                    _trackingInfoPageData.value = resultExceptList

                    val historyList =
                        result.data.fromData().second.trackingInfoPresentationList.toMutableList()
                    val standard = getStandardTimeInMillis(historyList[0])

                    for (idx in historyList.indices.reversed()) {
                        historyList[idx] =
                            historyList[idx].copy(dDay = countDday(standard, historyList[idx]))
                    }

                    _historyListData.value = historyList
                    _isLoadingProgressVisible.value = false
                }
                is Result.OnError -> {
                    _errorMsg.value =
                        when (result.exception) {
                            is ErrorType1Exception -> resourceProvider.getString(R.string.error_1)
                            is ErrorType2Exception -> resourceProvider.getString(R.string.error_2)
                            is ErrorType3Exception -> resourceProvider.getString(R.string.error_3)
                            else -> resourceProvider.getString(R.string.error_else)
                        }
                }
            }
        }
    }

    private fun getStandardTimeInMillis(standardItem: HistoryItem): Long {
        val standardCal = Calendar.getInstance()
        val standardSplit = standardItem.time.substring(0, 10).split("-").map { it.toInt() }
        standardCal.set(standardSplit[0], standardSplit[1] - 1, standardSplit[2])
        return standardCal.timeInMillis
    }

    override fun onClick(item: Any?, index: Int) {
        _msg.value = "item$item..index$index"
        //TODO do Something onClick
    }

    private fun countDday(standard: Long, target: HistoryItem): String? =
        try {
            val targetCal = Calendar.getInstance()
            val targetSplit = target.time.substring(0, 10).split("-").map { it.toInt() }

            targetCal.set(targetSplit[0], targetSplit[1] - 1, targetSplit[2])
            ((targetCal.timeInMillis / DAY_TO_MILLISECOND) - (standard / DAY_TO_MILLISECOND)).toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    companion object {
        const val DAY_TO_MILLISECOND = 86400000
    }
}
