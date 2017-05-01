package com.device.Exam;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by Александр Горбатов on 30.04.2017.
 */
public class ExamTest {
    String name;

    List<Qeustion> qeustionList;

    int _currentQuestion = 0;

    String _username;
    String _starttext;

    public ExamTest(String username) throws FileNotFoundException {
        _username = username;
        qeustionList = new ArrayList<Qeustion>();
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Exam.json"));
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(br).getAsJsonObject();
        name = obj.get("name").getAsString();
        _starttext = obj.get("start").getAsString();
        JsonArray arr = obj.get("questlist").getAsJsonArray();
        for (int i = 0; i < arr.size(); i++) {
            JsonObject q = arr.get(i).getAsJsonObject();
            String typeq = q.get("type").getAsString();
            Qeustion quest;
            switch (typeq) {
                case "singleq":
                    quest = new QuestionSingle(q);
                    break;
                case "multiq":
                    quest = new QuestionMulti(q);
                    break;
                case "notcheckedq":
                    quest = new QuestionNotChecked(q);
                    break;
                default:
                    quest = new Qeustion(q);
            }
            qeustionList.add(quest);
        }
    }

    public String getStartText()
    {
        return _starttext;
    }

    public boolean isActive() {
        return (_currentQuestion < qeustionList.size());
    }

    public Qeustion getCurrentQuestrion() {
        if (qeustionList.get(_currentQuestion).isAnswer())
            _currentQuestion++;
        if (!isActive()) return null;

        return qeustionList.get(_currentQuestion);
    }

    public String result()
    {
        int all = qeustionList.size();
        int right = 0;
        for (int i = 0 ; i < qeustionList.size(); i++) {
            Qeustion check = qeustionList.get(i);
            if (check.getType()==QuestionType.notanswerq)
            {
                all--;
                right--;
            }
            if (check.ok)
                right++;
        }
        return (String.valueOf(right)+"/"+String.valueOf(all));
    }

    public JsonObject jsonResult()
    {
        JsonArray ret = new JsonArray();
        for (int i = 0 ; i < qeustionList.size(); i++)
        {
            JsonObject qjson = new JsonObject();
            Qeustion qq = qeustionList.get(i);
            if (!qq.isAnswer()) continue;
            qjson.addProperty("question",qq._question);
            qjson.addProperty("answer",qq._answer);
            qjson.addProperty("check",qq.ok);
            ret.add(qjson);
        }
        JsonObject obj = new JsonObject();
        obj.add("questions",ret);
        obj.addProperty("id", _username);
        return  obj;
    }


}
