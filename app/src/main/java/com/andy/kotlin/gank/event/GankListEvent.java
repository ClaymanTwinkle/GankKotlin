package com.andy.kotlin.gank.event;

import com.andy.kotlin.gank.model.GankModel;
import com.andy.kotlin.gank.net.ApiResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * GankListEvent
 * @author andyqtchen <br/>
 *         gank list event
 *         创建日期：2017/6/9 18:25
 */
public class GankListEvent extends ApiEvent<ApiResponse<List<GankModel>>> {
    public GankListEvent(ApiResponse<List<GankModel>> body) {
        super(body);
    }

    public GankListEvent(int errorCode, @NotNull String errorMsg) {
        super(errorCode, errorMsg);
    }
}
