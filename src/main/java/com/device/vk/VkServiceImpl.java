package com.device.vk;

import com.device.Exam.ExamController;
import com.device.Exam.ExamTest;
import com.device.Exam.Qeustion;
import com.device.Exam.QuestionType;
import com.device.vk.model.dialog.Dialog;
import com.device.vk.model.dialog.DialogResponse;
import com.device.vk.model.dialog.Message;
import com.device.vk.model.dialog.MessageResponse;
import com.device.vk.model.user.User;
import com.device.vk.model.user.UserResponse;
import org.apache.log4j.Logger;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Collectors;

@Transactional
@Service
public class VkServiceImpl {

    VkServiceImpl()
    {
        examController=new ExamController();
    }

    ExamController examController;

    private static final Logger log = Logger.getLogger(VkServiceImpl.class);


    private static String PEER_ID;

    private Long offset = 0L;
    private Long count = 0L;
    private Long lastMessageId = 0L;

    private Integer rev = 0;


    private static String ACCESS_TOKEN = "";
    private static Long ADMINID;
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream("src/main/resources/config.properties"));
            ACCESS_TOKEN = properties.getProperty("vk.access.token");
            ADMINID=Long.parseLong(properties.getProperty("vk.admin.id"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getSendUrl(String message, Long id_user) {
        return "https://api.vk.com/method/messages.send?" +
                "random_id=" + new Random().nextInt(10000) +
                "&peer_id=" + id_user +
                "&message=" + message +
                "&notification=0" +
                "&v=5.60" +
                "&access_token=" + ACCESS_TOKEN;

    }

    public String getReqUrl() {
        return "https://api.vk.com/method/messages.getDialogs?" +
                "offset=" + this.offset +
                "&count=" + this.count +
                "&unread=1" +
                "&v=5.60" +
                "&access_token=" + ACCESS_TOKEN;
    }

    public String getUserUrl(Long id) {

        return "https://api.vk.com/method/users.get?" +
                "user_ids=" + id +
                "&v=5.60" +
                "&access_token=" + ACCESS_TOKEN;

    }

    public String getMarkAsReadUrl(Long messageId) {
        return "https://api.vk.com/method/messages.markAsRead?" +
                "message_ids=" + messageId +
             //   "&peer_id=" + user_id +
                "&v=5.60" +
                "&access_token=" + ACCESS_TOKEN;
    }

    public void sendMessage(String message, Long id_user) {

        new RestTemplate().getForEntity(getSendUrl(message, id_user), String.class);

    }

    public String getNameAndSurname(Long id) {
        User user = new RestTemplate().getForObject(getUserUrl(id), UserResponse.class).getResponse().get(0);
        return user.getFirstName() + " " + user.getLastName();


    }

    public void markAsRead(Long messageId) {
        new RestTemplate().getForEntity(getMarkAsReadUrl(messageId), String.class);

    }

    @Scheduled(fixedRate = 700)
    public void getMessagesUpdate() throws FileNotFoundException {
        this.rev = 0;
        this.count = 50L;

        Dialog response = new RestTemplate().getForObject(getReqUrl(), DialogResponse.class).getResponse();

        if (response != null) {
            List<MessageResponse> messages = response.getMessages()
                    .stream()
                    .filter(message -> message.getMessage().getReadState() != 1).collect(Collectors.toList());

            for (int i = 0; i < messages.size(); i++) {
                Message receivedMsg = messages.get(i).getMessage();
                log.debug(receivedMsg.getBody());
                if(receivedMsg.getBody().equals("- начать тест")) {

                    if (examController.isStart(receivedMsg.getUserId()))
                    {
                        sendMessage("вы уже начали тест", receivedMsg.getUserId());
                        continue;
                    }
                    if (!examController.startTest(receivedMsg.getUserId()))
                    {
                        sendMessage("Ошибка запуска теста, обратитесь к администратору vk.com/cash1994", receivedMsg.getUserId());
                        continue;
                    }
                    ExamTest test = examController.getExamTest(receivedMsg.getUserId());
                    sendMessage(test.getStartText(),receivedMsg.getUserId());
                    Qeustion first =test.getCurrentQuestrion();
                    sendMessage(first.getQuestion(), receivedMsg.getUserId());
                    if (first.getType()!=QuestionType.notanswerq) continue;
                }

                if (!examController.isStart(receivedMsg.getUserId()))
                {
                    sendMessage("вас приветствует чат-бот для прохождения тестов! для начала теста введите \"- начать тест\"",receivedMsg.getUserId());
                    continue;
                }
                ExamTest test = examController.getExamTest(receivedMsg.getUserId());
                if (!test.isActive())
                {
                    sendMessage("вы уже проходили этот тест. доступных тестов нет", receivedMsg.getUserId());
                    continue;
                }
                Qeustion quest = test.getCurrentQuestrion();
                if (quest.check(receivedMsg.getBody()))
                {
                    if (quest.getType()!=QuestionType.notanswerq)
                        sendMessage("верно!",receivedMsg.getUserId());
                }
                else {
                    sendMessage("не верно!",receivedMsg.getUserId());
                }
                quest = test.getCurrentQuestrion();
                if (test.isActive())
                {
                    sendMessage(quest.getQuestion(),receivedMsg.getUserId());
                    if (quest.getType()== QuestionType.notanswerq)
                    {
                        quest = test.getCurrentQuestrion();
                        if (!test.isActive())
                        {
                            sendMessage("тест завершен! "+test.result(),receivedMsg.getUserId());
                            sendMessage(getNameAndSurname(receivedMsg.getUserId())+" прошел тест на "+test.result(),ADMINID);
                            examController.saveAndRemove(receivedMsg.getUserId(),getNameAndSurname(receivedMsg.getUserId()));
                            continue;
                        }
                        sendMessage(quest.getQuestion(),receivedMsg.getUserId());
                    }
                }
                else
                {
                    sendMessage("тест завершен! "+test.result(), receivedMsg.getUserId());
                    sendMessage(getNameAndSurname(receivedMsg.getUserId())+" прошел тест на "+test.result(),ADMINID);
                    examController.saveAndRemove(receivedMsg.getUserId(), getNameAndSurname(receivedMsg.getUserId()));
                }


                markAsRead(receivedMsg.getId());
            }
        }
    }

    public void dumpAllMessages() {

    }


}
