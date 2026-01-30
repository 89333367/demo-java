package sunyu.demo;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.dialect.Props;
import org.junit.jupiter.api.Test;
import sunyu.example.config.ConfigProperties;
import sunyu.util.RedisClusterUtil;

public class Tests {
    Log log = LogFactory.get();
    Props props = ConfigProperties.getProps();
    RedisClusterUtil redisClusterUtil = RedisClusterUtil.builder().nodes(props.getStr("redis.cluster.nodes")).build();

    @Test
    void test() {
        String value = redisClusterUtil.get("p:r:d:600243");
        log.info("value: {}", value);
    }

}