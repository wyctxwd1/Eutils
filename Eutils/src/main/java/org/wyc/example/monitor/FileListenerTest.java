package org.wyc.example.monitor;

import lombok.extern.slf4j.Slf4j;

/**
 * 可以实现配置文件热更或者需要监控文件的场景
 */
@Slf4j
public class FileListenerTest {
    public static void main(String[] args) throws Exception {
        FileListener listener1 = new FileListener();
        FileListener listener2 = new FileListener();
        //factory最好用IOC注入,example没有
        FileListenerFactory factory = new FileListenerFactory();
        factory.addMonitor("C:\\Users\\Yuri\\Desktop\\software", listener1);
        factory.addMonitor("D:\\untitled", listener2);
        Thread.sleep(60 * 1000L);
        factory.removeMonitor("C:\\Users\\Yuri\\Desktop\\software");
        Thread.sleep(60 * 1000L);
        factory.removeMonitor("D:\\untitled");
    }
}
