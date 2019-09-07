package dev.sunnat629.shutterstockimages.models.networks

data class NetworkState(
    val status: Status,
    val message: String? = null
) {

    companion object {
        val SUCCEED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        fun ERROR(message: String?) = NetworkState(Status.FAILED, message)
    }
}


enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}