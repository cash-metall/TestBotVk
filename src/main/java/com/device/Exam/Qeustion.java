package com.device.Exam;

import com.google.gson.JsonObject;

public class Qeustion {
    public String _question;
    public String _answer;
    public boolean ok;

    public Qeustion(JsonObject obj)
    {
        _answer=new String();
        _question=obj.get("question").getAsString();
    }

    public boolean check(String answer)
    {
        _answer="NULL";
        ok=true;
        return true;
    }

    public QuestionType getType()
    {
        return QuestionType.notanswerq;
    }

    public String getQuestion(){
        return _question;
    }

    public boolean isAnswer()
    {
        return !_answer.isEmpty();
    }
}

