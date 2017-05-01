package com.device.Exam;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.ArrayList;

public class QuestionMulti extends Qeustion {

    List<String> rightanswer;

    public QuestionMulti(JsonObject obj)
    {
        super(obj);
        rightanswer = new ArrayList<String>();

        JsonArray arr = obj.get("answer").getAsJsonArray();
        for (int i = 0 ; i < arr.size(); i++)
        {
            rightanswer.add(arr.get(i).getAsString());
        }
    }

    public boolean check(String answer){
        _answer=answer;
        String[] splitstr = answer.split(", ");
        for (int i = 0 ; i < splitstr.length; i++)
        {
            if (!rightanswer.contains(splitstr[i]))
            {
                ok=false;
                return  false;
            }
        }
        ok=true;
        return true;
    }

    public QuestionType getType()
    {
        return QuestionType.multiq;
    }

}
