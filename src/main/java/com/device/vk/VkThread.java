package com.device.vk;

import java.io.FileNotFoundException;

public class VkThread extends Thread {
    public static VkServiceImpl vkService = new VkServiceImpl();
    @Override
    public void run() {
        while (true){
            try {
                vkService.getMessagesUpdate();
                currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
