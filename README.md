# seckill
银行秒杀系统开发记录,具体的开发流程同步分享到了掘金专栏-->https://juejin.cn/column/7054419593372106789
## 2021-11-29（登录功能）

完成了秒杀系统登录功能，采用AOP监控登陆状态（LonginAspect）,使用Validation API进行参数校验，通过自定义切面实现监控，但是要注意去掉秒杀部分的监控，在秒杀的那一小段时间里去除所有不必要的开销

![image-20220111144255005](README.images/image-20220111144255005.png)

通过reids实现分布式登录,实现session一致性，采用后端统一存储的方式，实现分布式登录可以为了以后如果有集群搭建的需求做准备。在yml文件里面配置好redis的连接信息，然后再RedisConfig配置RedisTemplate操作数据库。

```java
@Bean
public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    //对redis进行相应的配置,使得redis存储时可以直观显示
    //key序列化
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    //value序列化
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    //hash类型key序列化
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    //hash类型value序列化
    redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
    //注入连接工厂
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    return  redisTemplate;
}
```

使用的时候只要自动装配一下就可以了

```java
@Autowired
RedisTemplate redisTemplate;
。。。
。。。
//set操作
redisTemplate.opsForValue().set("user:" + ticket, sysUser, 60 * 60 * 12, TimeUnit.SECONDS);
```

<img src="README.images/1735a37e17e67100tplv-t2oaga2asx-watermark.awebp" alt="img" style="zoom: 67%;" />

## 2022-1-12（统一接口返回和全局异常处理）

统一接口返回和全局异常处理,规定将所有错误信息统一以异常抛出，示例：https://juejin.cn/post/6986800656950493214

![image-20220112215356823](README.images/image-20220112215356823.png)

## 2022-1-13（乐观锁防止超卖）

新增秒杀订单表，秒杀商品表，通过乐观锁实现下单防止超卖

![image-20220113213447061](README.images/image-20220113213447061.png)

## 2022-1-14（配置swagger3，令牌桶限流）

导入swagger，选用swagger3，比之前的2更加简单，引入依赖即可，并通过Swagger3Config类进行配置

```yml
implementation group: 'io.springfox', name: 'springfox-boot-starter', version: '3.0.0'
```

引入依赖后需要解决之前配置的@RestControllerAdvice和swagger的冲突，直接使用会导致swagger返回的结果被@RestControllerAdvice封装，页面会跳出如下提示

![image-20220114123643600](README.images/image-20220114123643600.png)

进行配置将swagger结果直接返回即可,具体操作：https://juejin.cn/post/6921700441038258189

![image-20220114123816959](README.images/image-20220114123816959.png)

在面临高并发的请购请求时，如果不对接口进行限流，可能会对后台系统造成极大的压力。尤其是对于下单的接口，过多的请求打到数据库会对系统的稳定性造成影响。同时有人用软件恶意刷单抢货，需要限流防止机器参与活动，所以需要对接口进行限流。这里在之前讲过的乐观锁抢购接口上增加该令牌桶限流。

```apl
令牌桶算法最初来源于计算机网络。在网络传输数据时，为了防止网络拥塞，需限制流出网络的流量，使流量以比较均匀的速度向外发送。令牌桶算法就实现了这个功能，可控制发送到网络上数据的数目，并允许突发数据的发送。

大小固定的令牌桶可自行以恒定的速率源源不断地产生令牌。如果令牌不被消耗，或者被消耗的速度小于产生的速度，令牌就会不断地增多，直到把桶填满。后面再产生的令牌就会从桶中溢出。最后桶中可以保存的最大令牌数永远不会超过桶的大小。
```

![img](README.images/format,png.png)

先引入Google的guava依赖，其内包含令牌桶算法

```yml
    implementation group: 'com.google.guava', name: 'guava', version: '30.1.1-jre'
```

在下单接口加上令牌桶限流

```java
 //每秒放行100个请求
    RateLimiter rateLimiter = RateLimiter.create(100);

    @PostMapping("/create")
    @Operation(summary = "用户下单")
    public TOrderRecord createWrongOrder(String dataid) throws Exception {
        //非阻塞式获取令牌：请求进来后，若令牌桶里没有足够的令牌，会尝试等待设置好的时间（这里写了1000ms），其会自动判断在1000ms后，
        //这个请求能不能拿到令牌，如果不能拿到，直接返回抢购失败。如果timeout设置为0，则等于阻塞时获取令牌。
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS))
            throw new RuntimeException("抢购失败，限流");
        TOrderRecord tOrderRecord = tOrderRecordService.createOrder(dataid);
        return tOrderRecord;
    }
```

由于需求是支付成功后减少库存，所以将下单接口拆分，变为createTempOrder和createRealOrder两个方法，第一个方法用于在用户点击下单时生成临时订单返回给用户（不写入数据库），在用户成功付款后（点击确认支付按钮时）调用第二个方法将订单写入数据库表示抢购成功，两个方法调用时都会先检查销售数量。

## 2022-1-15（接口隐藏）

为了防止脚本抢购秒杀商品等行为，需要将接口隐藏，抢购接口隐藏（接口加盐）的具体做法：

- 每次点击秒杀按钮，先从服务器获取一个秒杀验证值（接口内判断是否到秒杀时间等操作）。
- Redis以缓存用户ID和商品ID为Key，验证值为Value缓存验证值
- 用户请求秒杀商品的时候，带上秒杀验证值，得到一个临时订单数据
- 在临时订单支付时进行校验，完成下单写入数据库

由于业务需求将下单接口拆分后需要确保用户抢购时要按流程走过所有接口，从获取验证时-->获取临时订单-->真正下单，所以在获取验证值时程序会将用户id和商品id配合上盐（防止加密太简单被直接猜到）进行两次MD5加密后存入redis，并返回第一次加密后的值。

```java
 // 盐+用户id+商品id生成hash进行第一次MD5加密，用于临时订单接口生成真正的校验码
String verify = CacheKey.SALT_KEY.getKey() + user.getUserId() + dataId;
String verifyHash = DigestUtils.md5DigestAsHex(verify.getBytes());

//第二次加密，用于真正下单支付时校验
String verify2 = CacheKey.SEC_SALT_KEY.getKey() + verifyHash;
String verifyHash2 = DigestUtils.md5DigestAsHex(verify2.getBytes());

// 将第二次加密后的存入redis
String hashKey = CacheKey.VERIFY_KEY.getKey() + "_" + user.getUserId() + "_" + dataId;
redisTemplate.opsForValue().set(hashKey, verifyHash2, 600, TimeUnit.SECONDS);

//返回第一次加密的verifyHash
return verifyHash;
```

验证值获取后开始进行第二步获取临时订单，在这一步的时候会将传入验证值（第一次加密后的值）进行与第一步操作（获取验证值）的第二次加密一样的操作，从而获得真正的验证值（也就是在redis中存储的值），然后在生成订单时把这个值作为订单号返回。（把这个真正的验证值作为订单号的一个好处是这个值是个一个用户+一个商品所对应的，一般情况下秒杀商品是限购一份的，也就是说根据订单号唯一性（数据库设置该字段不可重复）可以保证每个用户限购一份相同的商品）

```java
//将临时VerifyHash再进行MD5加密生成真正的校验码作为订单号填入用于支付时校验
String verify = CacheKey.SEC_SALT_KEY.getKey() + params.getVerifyHash();
String verifyHash = DigestUtils.md5DigestAsHex(verify.getBytes());
order.setOrderId(verifyHash);
```

在第三步时会先对订单号进行校验，校验操作就是根据key查询redis中是否有这个订单号，成功后再进行后续操作。

**进行两次加密的原因：**如果只进行一次加密的话在第一步返回后redis中存储的就是最后的验证值，如果有人把接口都拿到的话可以直接进行第一步拿到验证值然后跳过第二步，进行第三步直接支付下单是可以通过验证的，但如果是两次MD5加密的话就必须依次经过这三个接口。

现阶段的下单大体流程如下：

![image-20220115211759127](README.images/image-20220115211759127.png)

## 2022-1-17（限制接口访问次数）

接口隐藏以后还需要对一些重要的接口限制访问次数，比如上面的三个接口都是跟下单有关的，如果被一些伄人拿到这些接口写个脚本一直访问服务器肯定会崩溃，所以需要对这些接口限制访问次数，常规做法还是在用reids实现，可以记录用户的访问次数，也可以记录ip的访问次（我采用的是这个）数等等。我这里为了数据库不太乱所以又用docker搞了个数据库专门记录ip访问次数。

在RedisConfig里面配置新的数据库连接，通过这种配置的方式不与之前的数据库起冲突，调用也很方便。

```java
@Bean(name = "IpCountRedisTemplate")
RedisTemplate<String,Object> IpCountRedisTemplate(){
    RedisStandaloneConfiguration server = new RedisStandaloneConfiguration();
    server.setHostName("150.158.28.238"); // 指定地址
    server.setDatabase(x); // 指定数据库
    server.setPort(xxxx); //指定端口
    LettuceConnectionFactory factory = new LettuceConnectionFactory(server);
    factory.afterPropertiesSet(); //刷新配置

    RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(factory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new FastJsonRedisSerializer<>(Object.class));
    return redisTemplate;
}
```

```java
//通过这种方式注入，因为之前已经有一个RedisTemplate类型的bean了，所以要byname注入
@Resource(name = "IpCountRedisTemplate")
RedisTemplate ipCountRedisTemplate;//专门用于统计IP访问次数的redis
```

配置完数据库后就可以对接口进行访问限制，我采用的是对ip进行限制，找一个iputil的工具类获取request的IP地址然后加盐当作key，访问次数作为value存到redis里面去。

```java
/**
* 功能描述:
* ip访问接口次数加1
*
* @param request
* @return int
* @author yt
* @date 2022/1/17 18:40
*/
public int addIpCount(HttpServletRequest request) throws Exception {
    String ipAddress = IpUtil.getIpAddr(request);
    String limitKey = CacheKey.LIMIT_KEY.getKey() + "_" + ipAddress;
    String limitNum = (String) ipCountRedisTemplate.opsForValue().get(limitKey);
    int limit = -1;
    if (limitNum == null) {
        ipCountRedisTemplate.opsForValue().set(limitKey, "0", 600, TimeUnit.SECONDS);
    } else {
        limit = Integer.parseInt(limitNum) + 1;
        ipCountRedisTemplate.opsForValue().set(limitKey, String.valueOf(limit), 600, TimeUnit.SECONDS);
    }
    return limit;
}

/**
* 功能描述:
* 检查IP访问接口次数
*
* @param request
* @return void
* @author yt
* @date 2022/1/17 18:40
*/
public void CheckIpIsBanned(HttpServletRequest request) {
    String ipAddress = IpUtil.getIpAddr(request);
    String limitKey = CacheKey.LIMIT_KEY.getKey() + "_" + ipAddress;
    String limitNum = (String) ipCountRedisTemplate.opsForValue().get(limitKey);
    if(null == limitNum) return;
    if (Integer.parseInt(limitNum) >= ALLOW_COUNT)
        throw new RuntimeException("禁止频繁访问，请十分钟后再试");
}
```

## 2022-1-22(完成shiro的整合)

整合shiro进行用户认证和权限管理，具体流程已发布掘金

[springboot整合shiro——前后端分离模式(上篇)](https://juejin.cn/post/7055838835942359076)

[springboot整合shiro——前后端分离模式(下篇)](https://juejin.cn/post/7056265836482265125)

[springboot整合shiro——前后端分离模式(浅析登录流程)](https://juejin.cn/post/7056644981452668965)

## 2022-1-23(新增商品的crud)

对商品进行了crud接口的编写，商品改变的同时要对秒杀商品表也做对应的改变，在增删改的方法上面加个事务@Transactional(rollbackFor = Exception.class)，查询列表的时候做了分页处理和根据关键字查询，根据关键字匹配：

```xml
<sql id="ConditionAll">
    <where>
        is_delete=0
        AND (1=2
        <if test="keyWord != null">
            OR goods_name LIKE CONCAT('%',#{keyWord},'%')
        </if>
        <if test="keyWord != null">
            OR goods_title LIKE CONCAT('%',#{keyWord},'%')
        </if>
        <if test="keyWord != null">
            OR goods_detail LIKE CONCAT('%',#{keyWord},'%')
        </if>)
    </where>
</sql>
```

分页查询：

```xml
<sql id="Pagination">
    LIMIT
    #{pageNum,jdbcType=INTEGER},
    #{pageSize,jdbcType=INTEGER}
</sql>
```

分页的时候写了个工具类先判断一下有没有传第一条记录位置和页面大小过来，没有的话默认第一页的前十条

```java
 public static void checkPage(Map params) {
     if(null==params.get("pageNum"))
     params.put("pageNum",0);
     if(null==params.get("pageSize"))
     params.put("pageSize",10);
 }
```

返回结果如下

![image-20220123225740227](README.images/image-20220123225740227.png)

## 2022-1-24(完成基础功能开发)

用户，商品，秒杀规则，申请记录，逾期——增删改查

具体联系如下：

商品表和规则表通过data_id关联，上架秒杀商品的时候可以配置他的准入规则，比如失信用户，有几次逾期记录，逾期金额超过多少等等就不可以参加，用户在点击商品详情时会先判断用户的资格，若没有资格则无法进入详情页，无论成功与否都会在申请记录表留下一次申请记录，获取商品详情方法如下

```java
public TGoods detail(String dataId) {
        QueryWrapper<TGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("data_id",dataId);
        TGoods tGoods = tGoodsMapper.selectOne(queryWrapper);
        if(null==tGoods)
            throw new RuntimeException("商品不存在");
        //从头里或请求里拿到token得到用户信息
        String token  = TokenUtil.getRequestToken(request);
        //根据token获取用户信息
        if(null==token)
            throw new RuntimeException("token为空，请重新登录");
        SysUser sysUser = (SysUser) redisTemplate.opsForValue().get(token);
        //查询准入规则
        TGoodsRule tGoodsRule = tGoodsRuleService.detail(dataId);
        //新增申请记录
        TApplyRecord tApplyRecord = new TApplyRecord();
        tApplyRecord.setUserId(sysUser.getUserId());
        tApplyRecord.setUserName(sysUser.getName());
        tApplyRecord.setGoodsId(tGoods.getDataId());
        tApplyRecord.setGoodsName(tGoods.getGoodsName());
        tApplyRecord.setApplyTime(new Date());
        //根据用户查询是否有参与活动的资格
        try {
            sysUserService.checkUserQualify(sysUser,tGoodsRule);
        }catch (Exception e){
            //结果不通过
            tApplyRecord.setApplyResult(1);
            tApplyRecordService.insertRecord(tApplyRecord);
            throw e;
        }
        //结果通过
        tApplyRecord.setApplyResult(0);
        tApplyRecordService.insertRecord(tApplyRecord);
        return tGoods;
    }
```

## 2022-3-5(数据库缓存一致性研究)

