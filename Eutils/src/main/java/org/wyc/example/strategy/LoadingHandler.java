package org.wyc.example.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventHandler(types = EventType.LOADING_MSG)
public class LoadingHandler implements Handler {
    @Override
    public void doWork() {
        log.info("this is loading msg.");
    }
}
