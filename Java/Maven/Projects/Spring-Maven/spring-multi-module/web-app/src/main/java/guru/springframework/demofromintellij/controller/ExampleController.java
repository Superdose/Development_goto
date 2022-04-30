package guru.springframework.demofromintellij.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

  @GetMapping(path = "/")
  public Map<String, Object> getIndex() {
    Map<String, Object> map = new HashMap<>();
    map.put("hello", "world");

    return map;
  }

}
