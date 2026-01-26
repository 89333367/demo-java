import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.dialect.Props;
import org.junit.jupiter.api.Test;
import org.ttzero.excel.entity.EmptySheet;
import org.ttzero.excel.entity.ListMapSheet;
import org.ttzero.excel.entity.ListSheet;
import org.ttzero.excel.entity.Workbook;
import org.ttzero.excel.reader.ExcelReader;
import org.ttzero.excel.reader.Row;
import org.ttzero.excel.reader.Sheet;
import sunyu.demo.entity.Student;
import sunyu.example.config.ConfigProperties;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Tests {
    Log log = LogFactory.get();
    Props props = ConfigProperties.getProps();

    @Test
    void test() {
        log.info("hello world");
    }

    /**
     * 在d:/tmp/excel文件夹下产生一个名为<新建文件.xlsx>的excel文件，显然打开之后是空白网格，因为添加了一个空的Sheet页（EmptySheet）
     */
    @Test
    void 写空文件() {
        try {
            new Workbook()
                    .bestSpeed() // 启用性能模式
                    .addSheet(new EmptySheet()).writeTo(Paths.get("d:/tmp/excel"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 打印所有worksheet的内容如果有多个sheet页时，因为我们调用了sheets()方法， 此方法会返回一个Stream<Sheet>它会带出所有Sheet页
     */
    @Test
    void 读取一个文件() {
        try (ExcelReader reader = ExcelReader.read(Paths.get("d:/tmp/excel/2026016发货明细.xlsx"))) {
            reader.sheets().flatMap(Sheet::rows).forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 读取第一个Sheet() {
        try (ExcelReader reader = ExcelReader.read(Paths.get("d:/tmp/excel/2026016发货明细.xlsx"))) {
            // 按行读取第1个Sheet并打印
            reader.sheet(0).rows().forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 读取第一个Sheet2() {
        try (ExcelReader reader = ExcelReader.read(Paths.get("d:/tmp/excel/2026016发货明细.xlsx"))) {
            // 按行读取第1个Sheet并打印
            reader
                    .sheet(0)// 获取第1个Sheet页
                    .dataRows()// 读取第一个非空行做为表头解析
                    .forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 读取第一个Sheet为map() {
        try (ExcelReader reader = ExcelReader.read(Paths.get("d:/tmp/excel/2026016发货明细.xlsx"))) {
            reader
                    .sheet(0)
                    .asFullSheet()
                    .copyOnMerged() // <- 转为FullSheet并复制合并单元格
                    .header(1)
                    .rows()
                    .map(Row::toMap)
                    .forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 写出一个集合() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
        try {
            new Workbook("简单数据类型") // 新增一个Workbook并指定名称，也就是Excel文件名
                    .bestSpeed() // 启用性能模式
                    .addSheet(new ListSheet<>(list)) // 添加一个Sheet页，并指定导出数据
                    .writeTo(Paths.get("d:/tmp/excel")); // 指定导出位置
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 强制写出一个集合() {
        List<Student> list = Arrays.asList(
                new Student("张三"),
                new Student(2, "李四"),
                new Student(3, "王五", 20));
        try {
            new Workbook("简单数据类型") // 新增一个Workbook并指定名称，也就是Excel文件名
                    .bestSpeed() // 启用性能模式
                    .forceExport() // 强制导出，即使是Student没有添加注解
                    .addSheet(new ListSheet<>(list)) // 添加一个Sheet页，并指定导出数据
                    .writeTo(Paths.get("d:/tmp/excel")); // 指定导出位置
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 写出一个map集合() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(new HashMap<String, Object>() {{
            //put("id", 1);
            put("name", "张三");
            //put("age", 20);
        }});
        list.add(new HashMap<String, Object>() {{
            put("id", 2);
            put("name", "李四");
            //put("age", 21);
        }});
        list.add(new HashMap<String, Object>() {{
            put("id", 3);
            put("name", "王五");
            put("age", 22);
        }});
        try {
            new Workbook("Map数据类型") // 新增一个Workbook并指定名称，也就是Excel文件名
                    .bestSpeed() // 启用性能模式
                    .addSheet(new ListMapSheet<>(list)) // 添加一个Sheet页，并指定导出数据
                    .writeTo(Paths.get("d:/tmp/excel")); // 指定导出位置
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}