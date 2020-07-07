package org.wyc.example.monitor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

@Slf4j
public class FileListener implements FileAlterationListener {

    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {
    }

    @Override
    public void onDirectoryCreate(File file) {
        log.info("this dir is created, " + file.getAbsolutePath());
    }

    @Override
    public void onDirectoryChange(File file) {
        log.info("this dir is changed, " + file.getAbsolutePath());
    }

    @Override
    public void onDirectoryDelete(File file) {
        log.info("this dir is deleted, " + file.getAbsolutePath());
    }

    @Override
    public void onFileCreate(File file) {
        log.info("this file is created, " + file.getAbsolutePath());
    }

    @Override
    public void onFileChange(File file) {
        log.info("this file is changed, " + file.getAbsolutePath());
    }

    @Override
    public void onFileDelete(File file) {
        log.info("this file is deleted, " + file.getAbsolutePath());
    }

    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {
    }
}
