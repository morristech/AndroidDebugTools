package io.lecon.debugtools.server;


import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.lecon.debugtools.sockethandler.ActionHandler;
import io.lecon.debugtools.utils.Utils;

/**
 * Created by spawn on 15/11/16.
 */
public class ClientServer implements Runnable {

    private static final String TAG = "ClientServer";

    private final int mPort;
    private final Context mContext;

    private boolean mIsRunning;

    private ServerSocket mServerSocket;

    private final ActionHandler mRequestHandler;

    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public ClientServer(Context context, int port) {
        mContext = context;
        mRequestHandler = new ActionHandler(context);
        mPort = port;
    }

    public void start() {
        mIsRunning = true;
        cachedThreadPool.submit(this);
    }

    public void stop() {
        try {
            mIsRunning = false;
            if (null != mServerSocket) {
                mServerSocket.close();
                mServerSocket = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error closing the server socket.", e);
        }
    }

    @Override
    public void run() {
        try {
            mServerSocket = new ServerSocket(mPort);
            Log.i(TAG, "StartServer Ip: http://" + Utils.getIP() + ":" + mPort);
            while (mIsRunning) {
                Socket socket = mServerSocket.accept();
                mRequestHandler.handle(socket);
                socket.close();
            }
        } catch (SocketException e) {
            // The server was stopped; ignore.
        } catch (IOException e) {
            Log.e(TAG, "Web server error.", e);
        } catch (Exception ignore) {

        }
    }

    public boolean isRunning() {
        return mIsRunning;
    }
}
