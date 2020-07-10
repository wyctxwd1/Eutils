package org.wyc.example.monitor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
public class FileListenerFactory {
    //轮询间隔
    private final long interval = TimeUnit.SECONDS.toMillis(10);
    private final FileAlterationMonitor monitor = new FileAlterationMonitor(interval);

    public FileListenerFactory() throws Exception {
        monitor.start();
    }

    public void addMonitor(String monitorDir, FileListener... listeners) {
        IOFileFilter dirs = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);
        IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(),
                HiddenFileFilter.VISIBLE, FileFilterUtils.suffixFileFilter(".txt"));
        IOFileFilter filter = FileFilterUtils.or(dirs, files);
        //使用过滤器
        FileAlterationObserver observer = new FileAlterationObserver(monitorDir, filter);
        Stream.of(listeners).forEach(observer::addListener);

        monitor.addObserver(observer);

    }

    public void removeMonitor(String monitorDir) {
        StreamSupport.stream(monitor.getObservers().spliterator(), false)
                .filter(observer -> observer.getDirectory().getPath().equalsIgnoreCase(monitorDir))
                .forEach(monitor::removeObserver);
    }

    public void stopAllMonitor() throws Exception {
        monitor.stop();
    }
}
