package org.wyc.example.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventHandler(types = EventType.FINISH_MSG)
public class FinshHandler implements Handler {
    @Override
    public void doWork() {
        log.info("this is finish msg.");
    }
}
