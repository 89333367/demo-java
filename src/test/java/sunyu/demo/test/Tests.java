package sunyu.demo.test;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.dialect.Props;
import org.junit.jupiter.api.Test;
import sunyu.example.config.ConfigProperties;

public class Tests {
    Log log = LogFactory.get();
    Props props = ConfigProperties.getProps();

    @Test
    void test() {
        log.info("hello world");
    }

}