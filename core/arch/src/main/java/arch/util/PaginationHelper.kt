package arch.util

class PaginationHelper(
    private val dataSetSize: Int = DEFAULT_DATA_SET_SIZE,
    private val loadMoreTriggerDataSetSize: Int = DEFAULT_DATA_SET_SIZE / 2,
    private var currentOffset: Int = 0,
    private var loadingInProgress: Boolean = false,
    private var noDataLeft: Boolean = false
) {

    fun resetPagination() {
        currentOffset = 0
        noDataLeft = false
    }

    fun isNextDataSetNeeded(lastVisibleItemPosition: Int): Boolean =
        lastVisibleItemPosition > currentOffset - loadMoreTriggerDataSetSize && !noDataLeft && !loadingInProgress

    fun getOffsetForNewDataSet(): Int {
        loadingInProgress = true
        return currentOffset
    }

    fun getPageForNewDataSet(): Int {
        loadingInProgress = true
        return currentOffset / dataSetSize + 1
    }

    fun onDataSetLoaded(newDataPortionSize: Int) {
        loadingInProgress = false
        if (newDataPortionSize < dataSetSize) {
            noDataLeft = true
        } else {
            currentOffset += newDataPortionSize
        }
    }

    fun isCurrentDataSetEmpty(): Boolean = currentOffset == 0

    fun hasMoreData(): Boolean = !noDataLeft

    companion object {
        private const val DEFAULT_DATA_SET_SIZE = 20
    }
}
