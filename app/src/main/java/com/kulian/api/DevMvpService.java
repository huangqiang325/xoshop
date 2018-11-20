package com.kulian.api;


import com.alibaba.fastjson.JSONObject;
import com.kulian.mvp.bean.ItemHomeData;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Liang_Lu on 2017/9/1.
 */

public interface DevMvpService {

    /**
     * 首页数据
     * @param data
     * @return
     */
    @POST(Url.HOME_DATA)
    Observable<Object> getHomeData(@QueryMap Map<String, String> data);

    /**
     * banner数据
     * @param data
     * @return
     */
    @POST(Url.BANNER)
    Observable<Object> getBanner(@QueryMap Map<String, String> data);
    /**
     * 分类数据左边
     * @param
     * @return
     */
    @POST(Url.CLASSIFY_LEFT)
    Observable<Object> getClassifyLeft();
    /**
     * 分类数据右边
     * @param
     * @return
     */
    @POST(Url.CLASSIFY_RIGHT)
    @FormUrlEncoded
    Observable<Object> getClassifyRight(@Field("id") String id,
                                        @Field("page") String page,
                                        @Field("page_num") String page_num
    );
    /**
     * 发现数据
     * @param
     * @return
     */
    @POST(Url.FOUND_DATA)
    Observable<Object> getFoundData(@QueryMap Map<String, String> data);
}
