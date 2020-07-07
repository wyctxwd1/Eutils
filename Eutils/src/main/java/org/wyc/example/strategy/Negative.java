package org.wyc.example.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Negative {
    public static void main(String[] args) {
        doWork(EventType.LOADING_MSG);
        doWork(EventType.FINISH_MSG);
    }

    private static void doWork(EventType type) {
        if (type == EventType.LOADING_MSG) {
            //模拟复杂操作
            log.info("this is loading msg.");
        } else if (type == EventType.READ_MSG) {
            log.info("this is read msg");
        } else if (type == EventType.WRITE_MSG) {
            log.info("this is write msg.");
        } else if (type == EventType.FINISH_MSG) {
            log.info("this is finish msg.");
        } else {
            log.error("not support this msg.");
        }
    }
}
