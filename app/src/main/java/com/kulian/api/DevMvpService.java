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
     *
     * @param data
     * @return
     */
    @POST(Url.HOME_DATA)
    Observable<Object> getHomeData(@QueryMap Map<String, String> data);

    /**
     * banner数据
     *
     * @param data
     * @return
     */
    @POST(Url.BANNER)
    Observable<Object> getBanner(@QueryMap Map<String, String> data);

    /**
     * 分类数据左边
     *
     * @param
     * @return
     */
    @POST(Url.CLASSIFY_LEFT)
    Observable<Object> getClassifyLeft();

    /**
     * 分类数据右边
     *
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
     *
     * @param
     * @return
     */
    @POST(Url.FOUND_DATA)
    Observable<Object> getFoundData(@QueryMap Map<String, String> data);

    /**
     * 获取验证码
     *
     * @param
     * @return
     */
    @POST(Url.GET_CODE)
    @FormUrlEncoded
    Observable<Object> getCode(@Field("phone") String phone);
    /**
     * 获取验证码
     *
     * @param
     * @return
     */
    @POST(Url.LOGIN)
    @FormUrlEncoded
    Observable<Object> dologin(@Field("phone") String phone,
                               @Field("code") String code
                                      );

    /**
     * 获取个人信息
     *
     * @param
     * @return
     */
    @POST(Url.PER_INFO)
    @FormUrlEncoded
    Observable<Object> getPerInfo(@Field("token") String token,
                               @Field("sign") String sign,
                               @Field("time") String time
    );

    /**
     * 发布朋友圈
     *
     * @param
     * @return
     */
    @POST(Url.RELEASE_FRIENDS)
    @FormUrlEncoded
    Observable<Object> releaseFriends(@Field("token") String token,
                                      @Field("sign") String sign,
                                      @Field("time") String time,
                                      @Field("content") String content,
                                      @Field("channelId") String channelId,
                                      @Field("industryId") String industryId
    );
    /**
     * 获取行业列表
     *
     * @param
     * @return
     */
    @POST(Url.INDUSTRY)
    Observable<Object> getIndustry( );
    /**
     * 获取频道栏目列表
     *
     * @param
     * @return
     */
    @POST(Url.CHANNEL)
    Observable<Object> getChannel( );
    /**
     * 朋友圈
     *
     * @param
     * @return
     */
    @POST(Url.MOMENTS)
    @FormUrlEncoded
    Observable<Object> getMoments(@Field("token") String token,
                                      @Field("sign") String sign,
                                      @Field("time") String time,
                                      @Field("industryId") String industryId,
                                      @Field("channelId") String channelId,
                                      @Field("pageLimit") String pageLimit,
                                      @Field("page") String page,
                                      @Field("filter") String filter
    );
}
