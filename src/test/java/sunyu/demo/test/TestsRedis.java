package sunyu.demo.test;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.dialect.Props;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sunyu.example.config.ConfigProperties;
import sunyu.util.RedisClusterUtil;
import sunyu.util.RedisUtil;

public class TestsRedis {
    Log log = LogFactory.get();
    static Props props = ConfigProperties.getProps();
    static RedisClusterUtil clusterUtil;
    static RedisUtil standaloneUtil;

    @BeforeAll
    public static void init() {
        clusterUtil = new RedisClusterUtil.Builder()
                .nodes(props.getStr("redis.cluster.nodes"))
                .build();

        standaloneUtil = new RedisUtil.Builder()
                .uri(props.getStr("redis.standalone.uri"))
                .build();
    }

    @AfterAll
    public static void destroy() {
        clusterUtil.close();
        standaloneUtil.close();
    }

    @Test
    void testCluster() {
        String v = clusterUtil.get("p:r:d:600243");
        log.info("{}", v);
    }

    @Test
    void testStandalone() {
        String v = standaloneUtil.georadiusWithCountOne("pca:tianditu", 86.018138, 28.283572, 1000);
        log.info("{}", v);
    }

}