package mate.academy.internetshop.lib;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.apache.log4j.Logger;

public class Injector {
    private static List<Class> classes = new ArrayList<>();
    private static final String PROJECT_MAIN_PACKAGE = "mate.academy.internetshop";
    private static Logger logger = Logger.getLogger(Injector.class);

    static {
        try {
            classes.addAll(getClasses(PROJECT_MAIN_PACKAGE));
        } catch (ClassNotFoundException | IOException e) {
            logger.error("Error while adding classes to classes list", e);
        }
    }

    public static void injectDependencies() throws IllegalAccessException {
        for (Class certainClass : classes) {
            for (Field field : certainClass.getDeclaredFields()) {
                if (field.getDeclaredAnnotation(Inject.class) != null) {
                    Object implementation = AnnotatedClassMap.getImplementation(field.getType());
                    if (implementation.getClass().getDeclaredAnnotation(Service.class) != null
                            || implementation.getClass().getDeclaredAnnotation(Dao.class) != null) {
                        field.setAccessible(true);
                        field.set(null, implementation);
                    }
                }
            }
        }
    }

    private static List<Class> getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    private static List<Class> findClasses(File directory, String packageName)
            throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    assert !file.getName().contains(".");
                    classes.addAll(findClasses(file, packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    classes.add(Class.forName(packageName + '.'
                            + file.getName().substring(0, file.getName().length() - 6)));
                }
            }
        }
        return classes;
    }
}
