# seckill
银行秒杀系统开发记录
## 2021-11-29

完成了秒杀系统登录功能，采用AOP监控登陆状态（LonginAspect）,使用Validation API进行参数校验，通过自定义切面实现监控，但是要注意去掉秒杀部分的监控，在秒杀的那一小段时间里去除所有不必要的开销

![image-20220111144255005](README.images/image-20220111144255005.png)

通过reids实现分布式登录,实现session一致性，采用后端统一存储的方式，实现分布式登录可以为了以后如果有集群搭建的需求做准备。

<img src="README.images/1735a37e17e67100tplv-t2oaga2asx-watermark.awebp" alt="img" style="zoom: 67%;" />

## 2022-1-12

统一接口返回和全局异常处理,规定将所有错误信息统一以异常抛出，示例：https://juejin.cn/post/6986800656950493214

![image-20220112215356823](README.images/image-20220112215356823.png)

## 2022-1-13

新增秒杀订单表，秒杀商品表，通过乐观锁实现下单防止超卖

![image-20220113213447061](README.images/image-20220113213447061.png)

## 2022-1-14

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

将下单接口拆分，变为createTempOrder和createRealOrder两个方法，第一个方法用于在用户点击下单时生成临时订单返回给用户（不写入数据库），在用户成功付款后（点击支付按钮时）调用第二个方法将订单写入数据库表示抢购成功，两个方法调用时都会先检查销售数量。
