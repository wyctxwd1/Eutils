package org.wyc.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ZipUtil {

    /**
     * 压缩成.zip的压缩包
     *
     * @param input  待压缩的文件夹
     * @param outZip 压缩后输出的zip包，需要以.zip结尾，如果没有该文件则会创建
     */
    public static void zip(final Path input, final Path outZip) {
        Map<String, String> env = new HashMap<>(1);
        env.put("create", String.valueOf(Files.notExists(outZip)));
        URI uri = URI.create("jar:" + outZip.toUri());
        try (FileSystem fs = FileSystems.newFileSystem(uri, env)) {
            Files.walkFileTree(input, new SimpleFileVisitor<Path>() {
                private final Path zipRoot = fs.getRootDirectories().iterator().next();
                private final boolean isDirectory = Files.isDirectory(input);

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path inZipPath = resolveDstInZip(file);
                    Files.copy(file, inZipPath, StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path inZipDir = resolveDstInZip(dir);
                    if (Files.notExists(inZipDir)) {
                        Files.createDirectories(inZipDir);
                    }
                    return FileVisitResult.CONTINUE;
                }

                private Path resolveDstInZip(Path path) {
                    if (isDirectory) {
                        //返回文件相对原文件夹的相对路径，在zip包中生成在根目录下  resolve可理解为Linux下的cd
                        return zipRoot.resolve(input.relativize(path).toString());
                    } else {
                        return zipRoot.resolve(path.getFileName().toString());
                    }
                }
            });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }

    /**
     * 把.zip结尾的压缩包解压缩
     *
     * @param zipFile zip包，需要以.zip结尾
     * @param outDir  解压缩文件路径
     */
    public static void unzip(final Path zipFile, final Path outDir) {
        try (FileSystem fs = FileSystems.newFileSystem(zipFile, null)) {
            Files.walkFileTree(fs.getPath("/"), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path dstPath = Paths.get(outDir.toString(), file.toString());
                    Files.copy(file, dstPath, StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path dstDir = Paths.get(outDir.toString(), dir.toString());
                    if (Files.notExists(dstDir)) {
                        Files.createDirectories(dstDir);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
