package sunyu.demo.test;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.dialect.Props;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sunyu.example.config.ConfigProperties;
import sunyu.util.MongoDBUtil;

public class TestMongodb {
    Log log = LogFactory.get();
    static Props props = ConfigProperties.getProps();
    static MongoDBUtil mongoDBUtil;
    static MongoDatabase qcDB;
    static MongoCollection<Document> qcLogCollection;
    static MongoCollection<Document> qcResultCollection;
    static MongoCollection<Document> qcReportCollection;

    @BeforeAll
    public static void init() {
        mongoDBUtil = MongoDBUtil.builder()
                // MongoDB连接字符串，包含用户名、密码、主机地址和连接参数
                .setUri(props.getStr("mongodb.uri"))
                .build();
        qcDB = mongoDBUtil.getDatabase("qc");
        qcLogCollection = qcDB.getCollection("qc_log");
        qcResultCollection = qcDB.getCollection("qc_result");
        qcReportCollection = qcDB.getCollection("qc_report");
    }

    @AfterAll
    public static void destroy() {
        mongoDBUtil.close();
    }

    @Test
    void hello() {
        log.info("qcLogCollection countDocuments: {}", qcLogCollection.countDocuments());
        log.info("qcResultCollection countDocuments: {}", qcResultCollection.countDocuments());
        log.info("qcReportCollection countDocuments: {}", qcReportCollection.countDocuments());
    }

}
