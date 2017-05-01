package com.device;

import com.device.vk.VkThread;

import java.io.IOException;


public class Application {

    public static void main(String[] args) throws IOException {
        Thread thread = new Thread(new VkThread());
        thread.run();
    }
}
