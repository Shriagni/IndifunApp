package com.deificindia.indifun1.Utility;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.StringDef;
import androidx.multidex.MultiDex;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.deificindia.indifun1.BuildConfig;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.agora.framework.PreprocessorFaceUnity;
import com.deificindia.indifun1.agorlive.Config;
import com.deificindia.indifun1.agorlive.agora.AgoraEngine;
import com.deificindia.indifun1.agorlive.agora.rtc.RtcEventHandler;
import com.deificindia.indifun1.agorlive.proxy.ClientProxy;
import com.deificindia.indifun1.agorlive.utils.Global;
import com.deificindia.indifun1.agorlive.utils.UserUtil;
import com.deificindia.indifun1.pojo.userpojo.Result;
import com.deificindia.indifun1.rtm.ChatManager;
import com.deificindia.indifun1.services.IndiFunThread;
import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.flattener.PatternFlattener;
import com.elvishew.xlog.formatter.message.json.DefaultJsonFormatter;
import com.elvishew.xlog.formatter.message.throwable.DefaultThrowableFormatter;
import com.elvishew.xlog.formatter.message.xml.DefaultXmlFormatter;
import com.elvishew.xlog.formatter.stacktrace.DefaultStackTraceFormatter;
import com.elvishew.xlog.formatter.thread.DefaultThreadFormatter;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.FileSizeBackupStrategy;
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.faceunity.FURenderer;
import com.google.gson.Gson;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

import io.agora.capture.video.camera.CameraManager;
import io.agora.rtc.RtcEngine;
import io.agora.rtm.RtmClient;

import static com.deificindia.indifun1.Utility.MySharePref.deleteData;
import static com.deificindia.indifun1.fcm.Config.unsubscribetopic;

public class IndifunApplication extends Application {

    private static final String APP_LEVEL_PREFS = "applogindata";
    public static final String CONSTANT_USERDATA = "logindata";
    private static final String CONSTANT_PASSWORD = "password";
    private static final String CONSTANT_LANGUAGE = "language";
    private static final String CONSTANT_USERID = "userid";
    private static final String CONSTANT_USERNAME = "username";
    private static final String CONSTANTMOBILE = "mobile";
    private static final String CONSTANT_EMAIL = "email";
    private static final String CONSTANUSERIMG = "userimg";
    private static final String CONSTANT_COUNTRYID = "countryid";
    private static final String CONSTANT_COUNTRYENAME = "ecountryname";
    private static final String CONSTANT_COUNTRYANAME = "acountryname";
    private static final String CONSTANT_COUNTRYCODE = "countrycode";
    private static final String ADRESSID = "addressid";
    private static final String WALLET = "wallet";
    private static final String CONSTANT_CITYNAME = "cityname";
    private static final String CONSTANT_CITYID = "cityid";
    private static final String CONSTANT_COUNTRYCURRENCY = "countrycurrency";
    private static final String CONSTANT_ECITYNAME = "ecityname";

    public static final String TAG = IndifunApplication.class
            .getSimpleName();


    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;


    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ENGLISH, ARABIC})
    public @interface LocaleDef {
        String[] SUPPORTED_LOCALES = {ENGLISH, ARABIC};
    }

    public static final String ENGLISH = "en";
    public static final String ARABIC = "ar";
    private static final String LANGUAGE_KEY = "language_key";

    private static IndifunApplication mInstance;
    private IndiFunThread indiFunThread;

    private SharedPreferences mPref;
    private SharedPreferences sharedPreferences;

    private Config mConfig;
    private AgoraEngine mAgoraEngine;
    private CameraManager mCameraVideoManager;
    private ChatManager mChatManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        sharedPreferences = getSharedPreferences(APP_LEVEL_PREFS, Context.MODE_PRIVATE);
        mPref = getSharedPreferences(Global.Constants.SF_NAME, Context.MODE_PRIVATE);
        Fresco.initialize(this);
        initWorkerThread();
        //getWorkerThread().eventHandler().addEventHandler(this); // Move to User session if error

        mConfig = new Config(this);

        initXLog();
        initVideoGlobally();
        initCrashReport();
        //initEngine(getString(R.string.agora_app_id)/*response.data.config.appId*/);
        XLog.i("onApplicationCreate");
    }

    public static synchronized IndifunApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public synchronized Context setLocale(Context mContext) {
        return updateResources(mContext, getlang(mContext));
    }

    public synchronized String getlang(Context mContext) {
        return sharedPreferences.getString(CONSTANT_LANGUAGE, "");

    }


    public synchronized void savelang(Context mContext, String localeKey) {

        sharedPreferences.edit().putString(CONSTANT_LANGUAGE, localeKey).apply();
    }

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    /**
     * get current locale
     */
    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? config.getLocales().get(0) : config.locale;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLocale(this);
    }

    public synchronized void saveCredential(Result loginModels) {
        if (loginModels != null) {
            Gson gson = new Gson();
            String json = gson.toJson(loginModels);
            sharedPreferences.edit().putString(CONSTANT_USERDATA, json).apply();
        }
    }

    public synchronized Result getCredential() {
        Result loginModel = null;
        try {
            Gson gson = new Gson();
            String json = sharedPreferences.getString(CONSTANT_USERDATA, null);
            loginModel = gson.fromJson(json, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginModel;
    }

    protected void setDefaultLocale(Context context, Locale locale) {
        Locale.setDefault(locale);
        Configuration appConfig = new Configuration();
        appConfig.locale = locale;
        context.getResources().updateConfiguration(appConfig, context.getResources().getDisplayMetrics());

    }

    public synchronized void logout() {
        unsubscribetopic();
        sharedPreferences.edit().remove(CONSTANT_USERDATA).apply();
        SharedPreferences preferences = getSharedPreferences("counter_preference", Context.MODE_PRIVATE);
        preferences.edit().clear().apply();

        deleteData(getApplicationContext());


    }

    ///////////Agora application///////////////////////////////////////////////////////////////////

    public Config config() {
        return mConfig;
    }

    public SharedPreferences preferences() { return mPref; }

    public SharedPreferences getSharedPreferences(){ return sharedPreferences; }

    public void initEngine(String appId) {
        mAgoraEngine = new AgoraEngine(this, appId);
    }

    public RtcEngine rtcEngine() {
        return mAgoraEngine != null ? mAgoraEngine.rtcEngine() : null;
    }

    public RtmClient rtmClient() {
        return mAgoraEngine != null ? mAgoraEngine.rtmClient() : null;
    }

    public ClientProxy proxy() {
        return ClientProxy.instance();
    }

    public void registerRtcHandler(RtcEventHandler handler) {
        if(mAgoraEngine!=null)
            mAgoraEngine.registerRtcHandler(handler);
    }

    public void removeRtcHandler(RtcEventHandler handler) {
        if(mAgoraEngine!=null)
            mAgoraEngine.removeRtcHandler(handler);
    }

    public CameraManager cameraVideoManager() {
        return mCameraVideoManager;
    }

    private void initVideoGlobally() {
        new Thread(() -> {
            FURenderer.initFURenderer(getApplicationContext());
            PreprocessorFaceUnity preprocessor = new PreprocessorFaceUnity(this);
            mCameraVideoManager = new io.agora.capture.video.camera.CameraManager(this, preprocessor);
            mCameraVideoManager.setCameraStateListener(preprocessor);
        }).start();
    }

    private void initXLog() {
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(BuildConfig.DEBUG ?
                        LogLevel.DEBUG : LogLevel.INFO)                         // Specify log level, logs below this level won't be printed, default: LogLevel.ALL
                .tag("AgoraLive")                                               // Specify TAG, default: "X-LOG"
                //.t()                                                            // Enable thread info, disabled by default
                .st(Global.Constants.LOG_CLASS_DEPTH)                           // Enable stack trace info with depth 2, disabled by default
                // .b()                                                            // Enable border, disabled by default
                .jsonFormatter(new DefaultJsonFormatter())                      // Default: DefaultJsonFormatter
                .xmlFormatter(new DefaultXmlFormatter())                        // Default: DefaultXmlFormatter
                .throwableFormatter(new DefaultThrowableFormatter())            // Default: DefaultThrowableFormatter
                .threadFormatter(new DefaultThreadFormatter())                  // Default: DefaultThreadFormatter
                .stackTraceFormatter(new DefaultStackTraceFormatter())          // Default: DefaultStackTraceFormatter
                .build();

        Printer androidPrinter = new AndroidPrinter();                          // Printer that print the log using android.util.Log

        String flatPattern = "{d yy/MM/dd HH:mm:ss} {l}|{t}: {m}";
        Printer filePrinter = new FilePrinter                                   // Printer that print the log to the file system
                .Builder(UserUtil.appLogFolderPath(this))         // Specify the path to save log file
                .fileNameGenerator(new DateFileNameGenerator())                 // Default: ChangelessFileNameGenerator("log")
                .backupStrategy(new FileSizeBackupStrategy(
                        Global.Constants.APP_LOG_SIZE))                         // Default: FileSizeBackupStrategy(1024 * 1024)
                .cleanStrategy(new FileLastModifiedCleanStrategy(
                        Global.Constants.LOG_DURATION))
                .flattener(new PatternFlattener(flatPattern))                   // Default: DefaultFlattener
                .build();

        XLog.init(                                                              // Initialize XLog
                config,                                                         // Specify the log configuration, if not specified, will use new LogConfiguration.Builder().build()
                androidPrinter,
                filePrinter);
    }

    private void initCrashReport() {
        /*String buglyAppId = getResources().getString(R.string.bugly_app_id);
        if (TextUtils.isEmpty(buglyAppId)) {
            XLog.i("Bugly app id not found, crash report initialize skipped");
        } else {
            *//*CrashReport.initCrashReport(getApplicationContext(),
                    buglyAppId, BuildConfig.DEBUG);*//*
        }*/
    }

    @Override
    public void onTerminate() {
        XLog.i("onApplicationTerminate");
        super.onTerminate();
        mAgoraEngine.release();
        deInitWorkerThread();
    }
    //////////////////////////////////////////////////////End agora app//////

    /*---indifun thread---*/
    public synchronized void initWorkerThread() {
        if (indiFunThread == null) {
            indiFunThread = new IndiFunThread(getApplicationContext(), getInstance());
            indiFunThread.start();

            indiFunThread.waitForReady();
        }
    }

    public synchronized IndiFunThread getWorkerThread() {
        return indiFunThread;
    }

    public synchronized void deInitWorkerThread() {

        if (indiFunThread != null){

            indiFunThread.exit();
            try {
                indiFunThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            indiFunThread = null;
        }


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    //////////////////////////////////////////////////////////////////////



}
