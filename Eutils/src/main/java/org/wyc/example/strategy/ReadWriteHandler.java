package org.wyc.example.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventHandler(types = {EventType.READ_MSG, EventType.WRITE_MSG})
public class ReadWriteHandler implements Handler {
    @Override
    public void doWork() {
        log.info("this is read or write msg.");
    }
}
