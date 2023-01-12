package letscode.sarafan.controller;

import letscode.sarafan.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/flower")
public class FlowerController {
    private int counter = 4;

    ArrayList<HashMap<String, String>> flowers = new ArrayList<>() {{
        add(new HashMap<>() {{ put("id", "1"); put("text", "Rose"); }} );
        add(new HashMap<>() {{ put("id", "2"); put("text", "Violet"); }} );
        add(new HashMap<>() {{ put("id", "3"); put("text", "Сhrysanthemum"); }} );
    }};

    @GetMapping
    public ArrayList<HashMap<String, String>> getFlowers() {
        return flowers;
    }

    @GetMapping("{id}")
    public HashMap<String, String> getById(@PathVariable String id) {
        return getFlower(id);
    }

    private HashMap<String, String> getFlower(String id) {
        return flowers.stream()
                .filter(flower -> flower.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping()
    public Map<String, String> saveFlower(@RequestBody Map<String, String> map) {
        HashMap<String, String> tempMap = new HashMap<>();
        System.out.println("Пришедший json: " + map.get("id") + ": " + map.get("text"));

        tempMap.put("id", String.valueOf(counter++));
        tempMap.putAll(map);

        flowers.add(tempMap);
        return tempMap;
    }

    @PutMapping("{id}")
    public Map<String, String> changeFlower(@RequestBody Map<String, String> map, @PathVariable String id) {
        Map<String, String> tempFlower = getFlower(id);

        tempFlower.putAll(map);
        //зачем нам вручную устанавливать id?

        return tempFlower;
    }
}
