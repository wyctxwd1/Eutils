package org.wyc.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Slf4j
public class FileUtil {
    /**
     * 简单的查询目录下符合条件的文件路径
     *
     * @param searchDir 被搜索的目录
     * @param matcher   对Path和BasicFileAttributes的过滤要求  例如(p,a) -> p.endsWith(".txt")/(p,a) -> !a.isDirectory()
     * @return 符合matcher条件的path路径
     * @throws IOException IO报错
     */
    public static List<Path> simpleFind(Path searchDir, BiPredicate<Path, BasicFileAttributes> matcher) throws IOException {
        //如果需要及时处理文件系统资源，应该使用try-with-resources构造来确保在流之后调用流的{@link Stream＃close close}方法
        try (Stream<Path> stream = Files.find(searchDir, Integer.MAX_VALUE, matcher)) {
            return stream.collect(toList());
        }
    }


}
