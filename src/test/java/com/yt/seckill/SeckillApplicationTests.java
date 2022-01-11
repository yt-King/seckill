package com.yt.seckill;

import com.yt.seckill.utils.IdcardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class SeckillApplicationTests {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void contextLoads() {
//        //向redis中添加数据
//        redisTemplate.opsForValue().set("keys", "value值");
//        //根据键值取出数据
//        System.out.println(redisTemplate.opsForValue().get("keys"));
        String result = Long.toBinaryString(112345l);
        long result1 = Long.parseLong("1000000000000000000000000000000000000100100100100111",2);
        System.out.println(result);
        System.out.println(result1);
    }
//    @Test
//    void contextLoads() {
//        String idcard = "330227200195111356";
//        System.out.println(IdcardUtils.getAgeByIdCard(idcard));
//        System.out.println(IdcardUtils.getBirthByIdCard(idcard));
//        System.out.println(IdcardUtils.getDateByIdCard(idcard));
//        System.out.println(IdcardUtils.getGenderByIdCard(idcard));
//        System.out.println(IdcardUtils.getMonthByIdCard(idcard));
//        System.out.println(IdcardUtils.getYearByIdCard(idcard));
//        System.out.println(IdcardUtils.validateIdCard18(idcard));
//
//    }

}
