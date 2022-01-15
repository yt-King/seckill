package com.yt.seckill.entity.Enum;

/**
 * @author 应涛
 * @date 2022/1/15
 * @function：
 */
public enum CacheKey {
    SALT_KEY("ytKing"),
    SEC_SALT_KEY("KingYT"),
    VERIFY_KEY("seckill");

    private String key;

    private CacheKey(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }
}
