package com.jd.uwp.common.utils;

/**
 * web 实例信息
 */
public class WebApp {
    /**
     * calss 文件存放路径
     */
    public static String workPath = getClassPath();

    public static String getRootPath(boolean webinf) {
        String result = WebApp.class.getResource("WebApp.class")
                .toString();
        int index = result.indexOf("WEB-INF");
        if (index == -1) {
            index = result.indexOf("bin");
            if (index == -1) {
                index = result.indexOf("lib");
            }
        }
        result = result.substring(0, index);
        if (result.startsWith("jar")) {
            result = result.substring(10);
        } else if (result.startsWith("file")) {
            result = result.substring(6);
        }
        if (result.endsWith("/"))
            result = result.substring(0, result.length() - 1);
        if (OS.isLinux()) {
            result = "/" + result;
        }
        // result=result.replace("%20", " ");
        result = result.replace("\\", "/");

        if (OS.isMacOSX() || OS.isLinux() && !result.startsWith("/")) {
            result = "/" + result;
        }
        ;
        if (webinf && result.contains("WebRoot")) {
            result += "/WEB-INF/classes";
        }
        return result;
    }

    public static String getConfigPath() {
        String rootPath = getRootPath();
        return rootPath + "/classes";
    }

    public static String getConfigPath(String sourcePath, String packetPath) {
        String rootPath = getRootPath();
        return rootPath + sourcePath + packetPath;
    }

    /**
     * 获取应用名称
     * 应用命名规范 应用名称_
     *
     * @return
     */
    public static String getAppName() {
        return "";
    }

    public static String getRootPath() {
        String result = WebApp.class.getResource("WebApp.class")
                .toString();
        int index = result.indexOf("WEB-INF");
        if (index == -1) {
            index = result.indexOf("bin");
            if (index == -1) {
                index = result.indexOf("lib");
            }
        }
        if (index == -1) {
            index = result.indexOf("build");
        }
        if (index == -1) {
            index = result.indexOf("target");
        }
        result = result.substring(0, index);
        if (result.startsWith("jar")) {
            result = result.substring(10);
        } else if (result.startsWith("file")) {
            result = result.substring(6);
        }
        if (result.endsWith("/"))
            result = result.substring(0, result.length() - 1);
        if (OS.isLinux() || OS.isMacOSX()) {
            result = "/" + result;
        }
        result = result.replace("\\", "/");
        if (OS.isMacOSX() || OS.isLinux() && !result.startsWith("/")) {
            result = "/" + result;
        }
        ;
        return result;
    }

    private static boolean isWeb = false;

    public static boolean isWeb() {
        return isWeb;
    }

    public static void setWeb(boolean isWeb) {
        WebApp.isWeb = isWeb;
    }

    public static boolean isWebProject() {
        return isWeb;
    }

    /**
     * @return
     */
    public static String getClassPath() {

        String result = "";
        if (isWebProject()) {
            result = getRootPath() + "/WEB-INF/classes";
        } else {
            result = getRootPath() + "/bin";
        }
        return result;
    }

    public static String getProjectPath() {
        String path = System.getProperty("user.dir");
        if (path.contains("\\"))
            path.replace("\\", "/");
        return path;
    }

    public static String getPlatformPatn() {
        String path = System.getProperty("user.dir");
        if (path.contains("\\"))
            path.replace("\\", "/");
        return path;
    }

    /**
     * CreateTime Apr 18, 2009 9:51:55 PM
     *
     * @param resourceName
     * @return
     */
    public static String getPath(String resourceName) {
        if (!resourceName.startsWith("/")) {
            resourceName = "/" + resourceName;
        }
        java.net.URL classUrl = new StringUtils().getClass().getResource(
                resourceName);

        if (classUrl != null) {
            return classUrl.getFile();
        }
        return null;
    }

    /**
     * 2009 12:22:13 AM
     *
     * @param resourcePath
     * @return
     */
    public static String getRealFilePath(String resourcePath) {
        java.net.URL inputURL = StringUtils.class.getResource(resourcePath);
        String filePath = inputURL.getFile();
        if (OS.isWindows() && filePath.startsWith("/")) {
            filePath = filePath.substring(1);
        }
        return filePath;
    }

    public static void main(String[] args) {
        WebApp app = new WebApp();
        System.out.println(app.getRootPath());
        System.out.println(getProjectPath());
    }

}
