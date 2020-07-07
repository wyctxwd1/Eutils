package org.wyc.example.strategy;

public class Positive {
    public static void main(String[] args) {
        Handler handler1 = SimpleFactory.createHandler(EventType.LOADING_MSG);
        Handler handler2 = SimpleFactory.createHandler(EventType.READ_MSG);
        Handler handler3 = SimpleFactory.createHandler(EventType.WRITE_MSG);
        Handler handler4 = SimpleFactory.createHandler(EventType.FINISH_MSG);
        handler1.doWork();
        handler2.doWork();
        handler3.doWork();
        handler4.doWork();
    }
}
