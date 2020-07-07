package org.wyc.example.strategy;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RegisterSingleton {
    private final Map<EventType, Handler> typeHandlerMap = new ConcurrentHashMap<>();

    private RegisterSingleton() {
        //自定义注解target为type，scanner的时候需要针对type和subtype
        Reflections reflections = new Reflections("org.wyc.example.strategy", new TypeAnnotationsScanner(), new SubTypesScanner());
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(EventHandler.class);
        classes.forEach(this::buildHandlerMap);
    }

    private static class SingletonHolder {
        private static final RegisterSingleton INSTANCE = new RegisterSingleton();
    }

    public static RegisterSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private void buildHandlerMap(Class<?> clz) {
        EventHandler eventHandler = clz.getAnnotation(EventHandler.class);
        if (eventHandler != null) {
            EventType[] types = eventHandler.types();
            for (EventType type : types) {
                try {
                    typeHandlerMap.put(type, (Handler) clz.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    public Handler getHandler(EventType type) {
        return typeHandlerMap.get(type);
    }
}
