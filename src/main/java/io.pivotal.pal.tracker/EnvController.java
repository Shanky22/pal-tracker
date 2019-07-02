package io.pivotal.pal.tracker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class EnvController {

    private final String port;
    private final String memoryLimit;
    private final String cfInstanceIndex;
    private final String cfInstanceAddress;

    public EnvController(@Value("${port:NOT SET}") String port,
                         @Value("${memory.limit:NOT SET}") String memoryLimit,
                         @Value("${cf.instance.index:NOT SET}") String cfInstanceIndex,
                         @Value("${cf.instance.addr:NOT SET}") String cfInstanceAddress ) {
        this.port = port;
        this.memoryLimit=memoryLimit;
        this.cfInstanceAddress = cfInstanceAddress;
        this.cfInstanceIndex=cfInstanceIndex;
    }

    @GetMapping("/env")
    public Map<String,String> getEnv(){
        Map<String,String> map = new HashMap<>();
        map.put("PORT", port);
        map.put("MEMORY_LIMIT", memoryLimit);
        map.put("CF_INSTANCE_INDEX", cfInstanceIndex);
        map.put("CF_INSTANCE_ADDR", cfInstanceAddress);
        return map;
    }
}
