package com.sigran0.sendreceive.interfaces;

public interface DataListner {

    public interface DataReceiveListener<T>{
        public void success(T data);
        public void fail(String message);
    }

    public interface DataSendListener {
        public void success();
        public void fail(String message);
    }

    public interface DataInitializeListener {
        public void success();
        public void fail(String message);
    }
}
