package com.kulian;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.kulian.comm.bean.Constant;
import com.kulian.comm.bean.DemoCache;
import com.kulian.utils.FrescoImageLoader;
import com.kulian.utils.SysUtils;
import com.squareup.okhttp.OkHttpClient;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Liang_Lu on 2017/9/7.
 */

public class DevMvpApplication extends MultiDexApplication {
    public static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();//分配的可用内存
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;//使用的缓存数量

    public static final int MAX_SMALL_DISK_VERYLOW_CACHE_SIZE = 5 * ByteConstants.MB;//小图极低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    public static final int MAX_SMALL_DISK_LOW_CACHE_SIZE = 10 * ByteConstants.MB;//小图低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    public static final int MAX_SMALL_DISK_CACHE_SIZE = 20 * ByteConstants.MB;//小图磁盘缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）

    public static final int MAX_DISK_CACHE_VERYLOW_SIZE = 10 * ByteConstants.MB;//默认图极低磁盘空间缓存的最大值
    public static final int MAX_DISK_CACHE_LOW_SIZE = 30 * ByteConstants.MB;//默认图低磁盘空间缓存的最大值
    public static final int MAX_DISK_CACHE_SIZE = 50 * ByteConstants.MB;//默认图磁盘缓存的最大值

    private static DevMvpApplication mInstance;
    public static Context mContext;
    public static final String IMAGE_PIPELINE_SMALL_CACHE_DIR = "KuLian_small_cache";//小图所放路径的文件夹名
    public static final String IMAGE_PIPELINE_CACHE_DIR = "KuLian_cache";//默认图所放路径的文件夹名
    public static Context context;
    public static String imgPath;
    public static IWXAPI mWxApi = null;
    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;
    private static DevMvpApplication app;

    public static Context getAppContext() {
        return app;
    }

    public static Resources getAppResources() {
        return app.getResources();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        DemoCache.setContext(this);
        context = getApplicationContext();
        initFresco();
        registToWX();
        mContext = getApplicationContext();
        mInstance = this;
        initScreenSize();
        initGaller();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
       initEM();
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(mContext.getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
    }
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
    private void initEM() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);
        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    public static Context getInstance() {
        return mInstance;
    }

    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }

    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID, true);
        // 将该app注册到微信
        mWxApi.registerApp(Constant.WX_APP_ID);
    }

    //图片选择器选择
    private void initGaller() {
        ThemeConfig theme = ThemeConfig.CYAN;
        ImageLoader imageLoader = new FrescoImageLoader(this);
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .setMutiSelectMaxSize(9)
                .build();
        CoreConfig coreConfig = new CoreConfig.Builder(context, imageLoader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }

    /**
     * 初始化facebook  加载图片
     */

    private void initFresco() {
        imgPath = SysUtils.getExternalSdCardPath();
        if (imgPath == null) {
            imgPath = getFilesDir().getAbsolutePath();
        }
        //内存配置
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                MAX_MEMORY_CACHE_SIZE, // 内存缓存中总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE, // 内存缓存中图片的最大数量。
                MAX_MEMORY_CACHE_SIZE, // 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE, // 内存缓存中准备清除的总图片的最大数量。
                Integer.MAX_VALUE); // 内存缓存中单个图片的最大大小。

        //修改内存图片缓存数量，空间策略（这个方式有点恶心）
        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };

        //小图片的磁盘配置
        DiskCacheConfig diskSmallCacheConfig = DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryPath(context.getApplicationContext().getCacheDir())//缓存图片基路径
                .setBaseDirectoryName(IMAGE_PIPELINE_SMALL_CACHE_DIR)//文件夹名
//                .setCacheErrorLogger(cacheErrorLogger)//日志记录器用于日志错误的缓存。
//                .setCacheEventListener(cacheEventListener)//缓存事件侦听器。
//                .setDiskTrimmableRegistry(diskTrimmableRegistry)//类将包含一个注册表的缓存减少磁盘空间的环境。
                .setMaxCacheSize(MAX_SMALL_DISK_CACHE_SIZE)//默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(MAX_SMALL_DISK_LOW_CACHE_SIZE)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_SMALL_DISK_VERYLOW_CACHE_SIZE)//缓存的最大大小,当设备极低磁盘空间
//                .setVersion(version)
                .build();

        //默认图片的磁盘配置
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryPath(new File(imgPath))//缓存图片基路径
                .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)//文件夹名
                .setMaxCacheSize(MAX_DISK_CACHE_SIZE)//默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(MAX_DISK_CACHE_LOW_SIZE)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_DISK_CACHE_VERYLOW_SIZE)//缓存的最大大小,当设备极低磁盘空间
                .build();

        //缓存图片配置
//        ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(context)
//              .setAnimatedImageFactory(AnimatedImageFactory animatedImageFactory)//图片加载动画
//              .setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)//内存缓存配置（一级缓存，已解码的图片）
//              .setCacheKeyFactory(cacheKeyFactory)//缓存Key工厂
//              .setEncodedMemoryCacheParamsSupplier(encodedCacheParamsSupplier)//内存缓存和未解码的内存缓存的配置（二级缓存）
//              .setExecutorSupplier(executorSupplier)//线程池配置
//              .setImageCacheStatsTracker(imageCacheStatsTracker)//统计缓存的命中率
//              .setImageDecoder(ImageDecoder imageDecoder) //图片解码器配置
//              .setIsPrefetchEnabledSupplier(Supplier<Boolean> isPrefetchEnabledSupplier)//图片预览（缩略图，预加载图等）预加载到文件缓存
//              .setMainDiskCacheConfig(diskCacheConfig)//磁盘缓存配置（总，三级缓存）
//              .setMemoryTrimmableRegistry(memoryTrimmableRegistry) //内存用量的缩减,有时我们可能会想缩小内存用量。比如应用中有其他数据需要占用内存，不得不把图片缓存清除或者减小 或者我们想检查看看手机是否已经内存不够了。
//              .setNetworkFetchProducer(networkFetchProducer)//自定的网络层配置：如OkHttp，Volley
//              .setPoolFactory(poolFactory)//线程池工厂配置
//              .setProgressiveJpegConfig(progressiveJpegConfig)//渐进式JPEG图
//              .setRequestListeners(requestListeners)//图片请求监听
//              .setResizeAndRotateEnabledForNetwork(boolean resizeAndRotateEnabledForNetwork)//调整和旋转是否支持网络图片
//              .setSmallImageDiskCacheConfig(diskSmallCacheConfig);//磁盘缓存配置（小图片，可选～三级缓存的小图优化缓存）

        OkHttpClient okHttpClient = new OkHttpClient();
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(this, okHttpClient)
                .setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setSmallImageDiskCacheConfig(diskSmallCacheConfig)
                .build();

        Fresco.initialize(this, config);
    }

    public void initJPush() {
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);

    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        String appVersion;
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            appVersion = "1.0.0";
        }
    }
}
