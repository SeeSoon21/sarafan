package letscode.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import letscode.sarafan.domain.Message;
import letscode.sarafan.domain.Views;
import letscode.sarafan.repo.MessageRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {
    //without autowired bcs constructor is exists
    private final MessageRepo messageRepo;

    public MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> list() {
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id") Message message) {
        System.out.println("вернутый @PathVariable('id') message = " + message.toString());
        return message;
    }

    @PostMapping()
    public Message create(@RequestBody Message message) {
        message.setCreationDate(LocalDateTime.now());
        System.out.println("Созданный пользователем и спрингом message: " + message + " + lDTime" + message.getCreationDate());

        return messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messageFromDb,
            @RequestBody Message message
    ) {
        System.out.println("message from DB: " + messageFromDb);
        System.out.println("message edited by user: " + message);

        //source, target, not editable field
        BeanUtils.copyProperties(message, messageFromDb, "id");

        return messageRepo.save(messageFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        System.out.println("удаляем сообщение; " + message);

        messageRepo.delete(message);
    }

    //websocket
    //c помощью аннотации MessageMapping настраиваем, что при отправки сообщения
    ///app
    @MessageMapping("/changeMessage")
    @SendTo("/topic/activity")
    public Message message(Message message) {
        System.out.println("(WS)пришедшее сообщение: " + message.toString());
        return messageRepo.save(message);
    }
}
