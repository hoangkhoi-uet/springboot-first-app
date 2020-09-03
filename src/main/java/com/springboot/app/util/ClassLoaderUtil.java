//package com.springboot.app.util;
//
//import java.io.File;
//import java.net.URL;
//import java.net.URLClassLoader;
//import java.net.URLDecoder;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class ClassLoaderUtil {
//    protected static final Logger LOGGER = LoggerFactory.getLogger(ClassLoaderUtil.class);
//
//    private static String classPath = "";
//
//    private static ClassLoader loader = Thread.currentThread().getContextClassLoader();
//
//    private ClassLoaderUtil() {}
//
//    //get class path
//    static {
//        if (loader == null) {
//            LOGGER.info("using system class loader");
//            loader = ClassLoader.getSystemClassLoader();
//        }
//        try {
//            URL url = loader.getResource("");
//
//            File f = new File(url.toURI());
//            classPath = f.getAbsolutePath();
//            classPath = URLDecoder.decode(classPath, "utf-8");
//
////            if (classPath.contains(".jar!")) {
////                LOGGER.warn("using config file inline jar!" + classPath);
////                classPath = System.getProperty("user.dir");
////                addCurrentWorkingDir2ClassPath(classPath);
////            }
//
//        } catch (Exception e) {
//            LOGGER.warn("cannnot get classpath using getResource(), now using user.dir");
//            classPath = System.getProperty("user.dir");
//
//            addCurrentWorkingDir2ClassPath(classPath);
//        }
//        LOGGER.info("classpath: {}", classPath);
//    }
//
//
//
//    private static void addCurrentWorkingDir2ClassPath(String path2Added) {
//        URLClassLoader urlClassLoader;
////        try {
////            urlClassLoader = new URLClassLoader(
////                    new URL[] { new File(path2Added).toURI().toURL() }, loader);
////            )
////        }
//    }
//
//    public static String getClassPath() { return classPath; }
//
//    public static ClassLoader getLoader() { return loader; }
//}
