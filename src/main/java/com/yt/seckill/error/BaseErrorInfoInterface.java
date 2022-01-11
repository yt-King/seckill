package com.yt.seckill.error;

/**
 * @author 应涛
 * @date 2022/1/11
 * @function：服务接口类
 */

public interface BaseErrorInfoInterface {

    /**
     *  错误码
     * @return
     */
    String getResultCode();

    /**
     * 错误描述
     * @return
     */
    String getResultMsg();
}
