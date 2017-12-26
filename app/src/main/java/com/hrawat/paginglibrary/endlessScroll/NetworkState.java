package com.hrawat.paginglibrary.endlessScroll;

/**
 * Created by hrawat on 12/26/2017.
 */
public class NetworkState {

    public static final Status LOADED = NetworkState.Status.SUCCESS;
    public static final Status LOADING = NetworkState.Status.RUNNING;
    private Status status;

    public static NetworkState error(String msg) {
        return new NetworkState(Status.FAILED, msg);
    }

    private NetworkState(Status status, String message) {
        this.status = status;
    }

    enum Status {
        RUNNING,
        SUCCESS,
        FAILED
    }

    public Status getStatus() {
        return status;
    }
}
//
//enum class Status {
//    RUNNING,
//    SUCCESS,
//    FAILED
//}
//
//@Suppress("DataClassPrivateConstructor")
//data class NetworkState private constructor(
//        val status: Status,
//        val msg: String? = null) {
//    companion object {
//        val LOADED = NetworkState(Status.SUCCESS)
//        val LOADING = NetworkState(Status.RUNNING)
//        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
//    }
//}