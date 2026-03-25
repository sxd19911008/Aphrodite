package com.aphrodite.common.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * 文件操作工具类
 */
public class FileUtils {

    /**
     * 将字符串列表逐行写入文件
     * <p>1. 路径是相对路径，相对于当前工作目录（通常是微服务模块的根目录）
     * <p>2. 如果文件不存在，会自动创建
     * <p>3. 如果文件已存在，会追加内容
     *
     * @param lines        要写入的字符串列表
     * @param relativePath 相对路径（例如："src/main/resources/output.txt"）
     */
    public static void writeLinesToFile(List<String> lines, String relativePath) {
        if (lines == null || lines.isEmpty()) {
            return;
        }
        if (relativePath == null || relativePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be empty");
        }

        try {
            // 获取当前工作目录 (微服务模块根目录)
            String userDir = System.getProperty("user.dir");
            Path path = Paths.get(userDir, relativePath);

            // 确保父目录存在
            Path parent = path.getParent();
            if (parent != null && !Files.exists(parent)) {
                throw new RuntimeException("Parent directory does not exist: " + parent);
            }

            // 写入文件，如果不存在则创建，如果存在则追加
            // Files.write 默认会在每行内容后添加系统的换行符
            Files.write(
                    path,
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            throw new RuntimeException("Failed to write lines to file: " + relativePath, e);
        }
    }
}

