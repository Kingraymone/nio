package core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

/**
 * 单独加载，父为ExtensionLoader
 */
public class MyLoader extends ClassLoader {
    Logger logger = LoggerFactory.getLogger(MyLoader.class);
    // 加载servlet的路径
    private final static String ROOT = Objects.requireNonNull(getSystemClassLoader().getResource("")).getPath();

    protected MyLoader() {
        super(ClassLoader.getSystemClassLoader().getParent());
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String className = name.replaceAll("\\.", System.getProperty("file.separator")) + ".class";
        Path path = Paths.get(ROOT, className);
        File file = path.toFile();
        if (!file.exists()) {
            throw new ClassNotFoundException("找不到servlet文件！");
        }
        logger.debug("servlet加载路径：" + file.getPath());
        try (ByteArrayOutputStream bao = new ByteArrayOutputStream(1024);
             FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)
        ) {
            ByteBuffer bb = ByteBuffer.allocate(1024);
            int len;
            while ((len = fileChannel.read(bb)) != -1) {
                bao.write(bb.array(), 0, len);
                bb.clear();
            }
            return defineClass(name, bao.toByteArray(), 0, bao.size());
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("servlet加载失败！");
            return null;
        }
    }
}
