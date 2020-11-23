package com.deificindia.indifun1.services;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.Utility.MySharePref;

import java.util.Objects;

import static com.deificindia.indifun1.Utility.Logger.loge;

import static com.deificindia.indifun1.agorlive.proxy.model.request.Request.CHANGE_ONLINE_STATUS;
import static com.deificindia.indifun1.agorlive.proxy.model.request.Request.UPDATE_BROADCASTING;

public class IndiFunThread extends Thread {

    public static final String TAG = "IndiFunThread";
    private static final int ACTION_WORKER_THREAD_QUIT = 0X1010; // quit this thread
    private static final int END_LIVE_SESSION = 100; // quit this thread



    private static final class WorkerThreadHandler extends Handler {

        private IndiFunThread indiFunThread;

        WorkerThreadHandler(IndiFunThread thread) {
            this.indiFunThread = thread;
        }

        void release() {
            indiFunThread = null;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (this.indiFunThread == null) {
                return;
            }

            String[] data;
            String peerUid;

            switch (msg.what) {
                case ACTION_WORKER_THREAD_QUIT:
                    indiFunThread.exit();
                    break;
                case CHANGE_ONLINE_STATUS:
                    data = (String[]) msg.obj;
                    indiFunThread.setEndApp2(data[0]);
                    break;
                case UPDATE_BROADCASTING:
                    data = (String[]) msg.obj;
                    indiFunThread.endSession(data[0]);
                    break;
                /*case ACTION_WORKER_CONNECT_TO_RTM_SERVICE:
                    data = (String[]) msg.obj;
                    mWorkerThread.connectToRtmService(data[0]);
                    break;
                case ACTION_WORKER_DISCONNECT_FROM_RTM_SERVICE:
                    mWorkerThread.disconnectFromRtmService();
                    break;
                case ACTION_WORKER_QUERY_PEERS_ONLINE_STATUS:
                    peerUid = (String) msg.obj;
                    mWorkerThread.queryPeersOnlineStatus(peerUid);
                    break;
                case ACTION_WORKER_MAKE_A_CALL:
                    data = (String[]) msg.obj;
                    mWorkerThread.makeACall(data[0], data[1], data[2]);
                    break;
                case ACTION_WORKER_MAKE_THE_CALL:
                    mWorkerThread.answerTheCall((RemoteInvitation) msg.obj);
                    break;
                case ACTION_WORKER_HANG_UP_THE_CALL:
                    mWorkerThread.hangupTheCall((RemoteInvitation) msg.obj);
                    break;
                case ACTION_WORKER_JOIN_CHANNEL:
                    data = (String[]) msg.obj;
                    mWorkerThread.joinChannel(data[0], msg.arg1, msg.arg2);
                    break;
                case ACTION_WORKER_LEAVE_CHANNEL:
                    String channel = (String) msg.obj;
                    mWorkerThread.leaveChannel(channel);
                    break;
                case ACTION_WORKER_CONFIG_ENGINE:
                    Object[] configData = (Object[]) msg.obj;
                    mWorkerThread.configEngine((VideoEncoderConfiguration.VideoDimensions) configData[0], (String) configData[1], (String) configData[2]);
                    break;*/
            }
        }

    }


    private WorkerThreadHandler mWorkerHandler;
    private Context mContext;
    private IndifunApplication indifunApplication;

    private boolean mReady;

    public final void waitForReady() {
        while (!mReady) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Log.v("AGORA","wait for " + WorkerThread.class.getSimpleName());
        }
    }

    @Override
    public void run() {
        super.run();
        Looper.prepare();
        mWorkerHandler = new WorkerThreadHandler(this);
        mReady = true;
        Looper.loop();
    }
    public final void exit() {
        if (Thread.currentThread() != this) {
            //QuickHelp.warn("exit() - exit app thread asynchronously");
            setEndApp(MySharePref.getUserId());
            mWorkerHandler.sendEmptyMessage(ACTION_WORKER_THREAD_QUIT);
            return;
        }

        mReady = false;
        // exit thread looper
        Objects.requireNonNull(Looper.myLooper()).quit();
        mWorkerHandler.release();
    }

    public IndiFunThread(Context context, IndifunApplication indifunApplication) {
        this.mContext = context;
        this.indifunApplication = indifunApplication;
    }

    ////////////////////////////////////////////////////////////////////////////
    /*----------------------------------------------------------*/
    /***send end sessiont report to server***/
    public void setEndApp(String uid){
        loge(TAG, "setEndLiveSession", uid);
        if (Thread.currentThread() != this) {
            loge(TAG, "endSession 2", uid);
            //QuickHelp.warn("disconnectFromRtmService() - worker thread asynchronously");
            Message envelop = new Message();
            envelop.what = CHANGE_ONLINE_STATUS;
            envelop.obj = new String[]{uid};
            mWorkerHandler.sendMessage(envelop);
        }
    }

    private void setEndApp2(String uid){
        loge(TAG, "endSession", uid);
        this.indifunApplication.proxy().sendRequest(CHANGE_ONLINE_STATUS,uid
                /*getData(this.mContext, "userId", null)*/);
    }

    public void setEndLiveSession(String bid){
        loge(TAG, "setEndLiveSession", bid);
        if (Thread.currentThread() != this) {
            loge(TAG, "endSession 2", bid);
            //QuickHelp.warn("disconnectFromRtmService() - worker thread asynchronously");
            Message envelop = new Message();
            envelop.what = UPDATE_BROADCASTING;
            envelop.obj = new String[]{bid};
            mWorkerHandler.sendMessage(envelop);
        }
    }

    private void endSession(String bid){
        loge(TAG, "endSession", bid);
        this.indifunApplication.proxy().sendRequest(UPDATE_BROADCASTING,bid /*getData(this.mContext, "userId", null)*/);
    }



    public void send(){
        if (Thread.currentThread() != this) {
            //QuickHelp.warn("leaveChannel() - worker thread asynchronously " + channel);
            Message envelop = new Message();
            //envelop.what = ACTION_WORKER_LEAVE_CHANNEL;
            // envelop.obj = channel;
            // envelop.obj = new Object[]{videoDimension, encryptionKey, encryptionMode};
            // envelop.obj = new Object[]{start, view, uid};
            mWorkerHandler.sendMessage(envelop);
            return;
        }
    }



}
