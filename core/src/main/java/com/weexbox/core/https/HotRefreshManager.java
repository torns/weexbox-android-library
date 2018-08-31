package com.weexbox.core.https;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.taobao.weex.appfram.websocket.WebSocketModule;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.logging.Logger;

import javax.annotation.Nullable;

import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.internal.ws.WebSocketProtocol;
import okio.Buffer;
import okio.BufferedSource;

/**
 */
public class HotRefreshManager {


  //hot refresh
  public static final int HOT_REFRESH_CONNECT = 0x111;
  public static final int HOT_REFRESH_DISCONNECT = HOT_REFRESH_CONNECT + 1;
  public static final int HOT_REFRESH_REFRESH = HOT_REFRESH_DISCONNECT + 1;
  public static final int HOT_REFRESH_CONNECT_ERROR = HOT_REFRESH_REFRESH + 1;

  private static final String TAG = "HotRefreshManager";

  private static HotRefreshManager hotRefreshInstance = new HotRefreshManager();
  private WebSocket mWebSocket = null;
  private Handler mHandler = null;

  private HotRefreshManager() {
  }

  public static HotRefreshManager getInstance() {
    return hotRefreshInstance;
  }

  public void setHandler(Handler handler) {
    mHandler = handler;
  }

  public boolean disConnect() {
    if (mWebSocket != null) {
      mWebSocket.close(1000, "activity finish!");
    }
    return true;
  }

  public boolean connect(String url) {
    OkHttpClient httpClient = new OkHttpClient();
    Request request = new Request.Builder().url(url).addHeader("sec-websocket-protocol", "echo-protocol").build();

//    WebSocketCall.create(httpClient, request).enqueue(new WXWebSocketListener(url));
    httpClient.newWebSocket(request, new WXWebSocketListener(url));

    return true;
  }

  class WXWebSocketListener extends WebSocketListener {

    private String mUrl;

    WXWebSocketListener(String url) {
      mUrl = url;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
      mWebSocket = webSocket;
    }

//    @Override
//    public void onMessage(ResponseBody message) {
//
//    }

//    @Override
//    public void onMessage(BufferedSource payload, WebSocket.PayloadType type) throws IOException {
//      if (type == WebSocket.PayloadType.TEXT) {
//        String temp = payload.readUtf8();
//        Log.e(TAG, "into--[onMessage] msg:" + temp);
//        payload.close();
//
//        if (TextUtils.equals("refresh", temp) && mHandler != null) {
//          mHandler.obtainMessage(Constants.HOT_REFRESH_REFRESH, 0, 0, mUrl).sendToTarget();
//        }
//      }
//    }

//    @Override
//    public void onPong(Buffer payload) {
//
//    }


    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
      super.onClosed(webSocket, code, reason);
      mWebSocket = null;
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
      super.onFailure(webSocket, t, response);
      mWebSocket = null;
    }

//    @Override
//    public void onClose(int code, String reason) {
//      mWebSocket = null;
//    }

//    @Override
//    public void onFailure(IOException e, Response response) {
//      mWebSocket = null;
//    }
  }
}
