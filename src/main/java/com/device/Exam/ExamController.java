package com.device.Exam;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

/**
 * Created by Александр Горбатов on 30.04.2017.
 */
public class ExamController {
    Map<Long, ExamTest> users;

    public ExamController() {
        users = new HashMap<Long,ExamTest>();
    }

    public ExamTest getExamTest(Long id) {
        if (users.containsKey(id)) {
            return users.get(id);
        }
        return  null;
    }

    public boolean isStart(Long id){
        return users.containsKey(id);
    }

    public boolean startTest(Long id) throws FileNotFoundException {
        if (users.containsKey(id))
            return  false;
        ExamTest test = new ExamTest(id.toString());
        users.put(id, test);
        return true;
    }

    public void saveresult() throws IOException {
        JsonArray arr = new JsonArray();
        for (Long id : users.keySet())
        {
            arr.add(users.get(id).jsonResult());
        }
        FileWriter writer = new FileWriter("report.json");
        writer.write(arr.toString());
    }
    public void saveAndRemove(Long id, String name)
    {
            try {
                ExamTest exam = users.get(id);
                String time = new SimpleDateFormat("MM-dd HH-mm-ss").format(Calendar.getInstance().getTime());
                FileWriter writer = new FileWriter(time+" report-"+exam.name+"-"+name+".json");
                System.out.println(exam.jsonResult().toString());
                writer.write(exam.jsonResult().toString());
                writer.close();
                users.remove(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}
