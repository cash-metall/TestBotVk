package com.device.Exam;

import com.google.gson.JsonObject;

public class QuestionNotChecked extends Qeustion {

    public QuestionNotChecked(JsonObject obj)
    {
        super(obj);
    }


    @Override
    public boolean check(String answer){
        _answer=answer;
        ok=true;
        return true;
    }

    @Override
    public QuestionType getType()
    {
        return QuestionType.notcheckedq;
    }

}
