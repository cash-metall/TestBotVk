package com.device.Exam;

import com.google.gson.JsonObject;

public class QuestionSingle extends Qeustion {
    String rightanswer;

    public QuestionSingle(JsonObject obj)
    {
        super(obj);
        rightanswer=obj.get("answer").getAsString();
    }

    @Override
    public boolean check(String answer){
        _answer=answer;
        ok = (answer.equals(rightanswer));
        return ok;
    }

    @Override
    public QuestionType getType()
    {
        return QuestionType.singleq;
    }

}
