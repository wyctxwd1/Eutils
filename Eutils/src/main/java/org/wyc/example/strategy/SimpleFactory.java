package org.wyc.example.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleFactory {
    public static Handler createHandler(EventType type) {
        return RegisterSingleton.getInstance().getHandler(type);
    }
}
