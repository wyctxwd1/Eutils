package org.wyc.example.strategy;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class Register {
    private static final Map<EventType, Handler> typeHandlerMap = new ConcurrentHashMap<>();

    static {
        init();
    }

    private static void init() {
        //自定义注解target为type，scanner的时候需要针对type和subtype
        Reflections reflections = new Reflections("org.wyc.pattern.strategy", new TypeAnnotationsScanner(), new SubTypesScanner());
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(EventHandler.class);
        classes.forEach(Register::buildHandlerMap);
    }

    private static void buildHandlerMap(Class<?> clz) {
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

    public static Handler getHandler(EventType type) {
        return typeHandlerMap.get(type);
    }
}
