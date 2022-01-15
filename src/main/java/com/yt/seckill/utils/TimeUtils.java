package com.yt.seckill.utils;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author 应涛
 * @date 2022/1/15
 * @function：时间工具类
 */
public class TimeUtils {

    /**
     * @Title: getNowTime
     * @Description: 得到当前格式化统一时间
     * @return   得到2015-02-03 12:02:23
     * @return String
     * @author huzhihui_c@qq.com
     * @date 2016年7月14日 下午5:40:48
     */
    public static String getNowTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = sdf.format(date);
        return time;
    }

    /**
     * @Title: getCuitPreTime
     * @Description:     日期紧凑型返回
     * @return   yyyyMMdd
     * @return String    yyyyMMdd
     * @author huzhihui_c@qq.com
     * @date 2016年7月14日 下午5:42:37
     */
    public static String getCuitPreTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String time = sdf.format(date);
        return time;
    }

    /**
     * @Title: getImageNamePrefix
     * @Description:     生成新图片的前缀
     * @return
     * @return String
     * @author huzhihui_c@qq.com
     * @date 2016年7月14日 下午5:43:46
     */
    public synchronized static String getImageNamePrefix(){
        String namePrefix = String.valueOf(System.currentTimeMillis());
        namePrefix += (int)((Math.random()*899+100));
        return getCuitPreTime()+namePrefix;
    }
}

