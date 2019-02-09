package maks.chornyi.sarafan.controller;

import maks.chornyi.sarafan.exception.NotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {

    private int counter = 4;

    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First message"); }});
        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second message"); }});
        add(new HashMap<String, String>() {{ put("id", "3"); put("text", "Third message"); }});
    }};



    @GetMapping
    public List<Map<String, String>> list() {
        return this.messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getMessageById(@PathVariable String id) {
        return messages.stream()
                .filter(msg -> msg.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String,String> createMessage(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));

        messages.add(message);

        return message;
    }

    @PutMapping
    public Map<String,String> updateMessage(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDb = getMessageById(id);

        messageFromDb.putAll(message);
        messageFromDb.put("id", id);

        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void  deleteMessageById(@PathVariable String id) {
        Map<String, String> msg = getMessageById(id);
        messages.remove(msg);
    }

}
