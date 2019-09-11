package dev.sunnat629.shutterstockimages.models.networks

data class NetworkState(
    val status: Status,
    val message: String? = null
) {

    companion object {
        val LOADED = NetworkState(Status.LOADED)
        val LOADING = NetworkState(Status.RUNNING)
        fun ERROR(message: String?) = NetworkState(Status.FAILED, message)
    }
}


enum class Status {
    RUNNING,
    LOADED,
    FAILED
}