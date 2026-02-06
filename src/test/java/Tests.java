import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.dialect.Props;
import org.junit.jupiter.api.Test;
import sunyu.example.config.ConfigProperties;
import sunyu.example.config.MyBatis;
import sunyu.example.entity.DP;
import sunyu.example.mapper.tdengine.TdengineMapper;

import java.time.LocalDateTime;
import java.util.List;

public class Tests {
    Log log = LogFactory.get();
    Props props = ConfigProperties.getProps();

    List<DP> selectWorkPoints(String did, LocalDateTime startTime, LocalDateTime endTime) {
        TdengineMapper mapper = MyBatis.getMapper(TdengineMapper.class);
        List<DP> l = mapper.selectWorkPoints(did, startTime, endTime, false);
        return l;
    }

    @Test
    void test() {
        String did = "LFS1032311100736";
        String startTime = "20260116104103";
        String endTime = "20260116153830";
        double jobWidth = 3.5;
        List<DP> l = selectWorkPoints(did, LocalDateTimeUtil.parse(startTime, "yyyyMMddHHmmss"), LocalDateTimeUtil.parse(endTime, "yyyyMMddHHmmss"));
        log.info("{}", l.size());
    }

}