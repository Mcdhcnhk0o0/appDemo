package com.example.appdemo.network;

import android.util.Log;

import com.example.appdemo.BuildConfig;
import com.example.appdemo.network.protocol.WebSocketMessageListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketService extends WebSocketListener {

    private static final String TAG = WebSocketService.class.getSimpleName();

    private static final String LOCAL_WEBSOCKET_URL = "ws://192.168.0.102:8880/demo/websocket/123";
    private static final String REMOTE_WEBSOCKET_URL = "ws://123.249.16.84:8880/demo/websocket";

    private final OkHttpClient client = new OkHttpClient();

    private final Request request;

    {
        String url;
        if (BuildConfig.DEBUG) {
            url = LOCAL_WEBSOCKET_URL;
        } else {
            url = REMOTE_WEBSOCKET_URL;
        }
         request = new Request.Builder().url(url).build();
    }

    private WebSocket webSocket;

    private final List<WebSocketMessageListener> messageListeners = new ArrayList<>();

    private WebSocketService() {
        createWebSocket();
    }

    public static WebSocketService getInstance() {
        return InnerClass.instance;
    }

    public void createWebSocket() {
        webSocket = client.newWebSocket(request, this);
        webSocket.request();
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        Log.d(TAG, "websocket open: " + webSocket);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        for (var listener: messageListeners) {
            listener.onMessage(text);
        }
        Log.d(TAG, "receive message from server: " + text);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
        Log.d(TAG, "remote websocket " + webSocket + " closed because: " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        Log.e(TAG, "error in websocket: " + t.getMessage() + ". response: " + response);
    }

    public boolean sendMessage(String message) {
        Log.d(TAG, "send message to server: " + message);
        return webSocket.send(message);
    }

    public void addMessageListener(WebSocketMessageListener listener) {
        messageListeners.add(listener);
    }

    private static class InnerClass {
        private static final WebSocketService instance = new WebSocketService();
    }

}
