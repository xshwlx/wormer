package com.jd.uwp.common.utils;

import java.awt.Toolkit;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 操作系统信息
 *
 * @author xu shiheng (2014-12-4上午8:58:40)
 */
public class OS {

    public static final int LINUX = 1;
    public static final int WINDOWS = 0;
    private static boolean osIsMacOsX;
    private static boolean osIsWindows;
    private static boolean osIsWindowsXP;
    private static boolean osIsWindows2003;
    private static boolean osIsLinux;

    public static String getOsDesc() {
        return osDesc;
    }

    public static void setOsDesc(String osDesc) {
        OS.osDesc = osDesc;
    }

    private static String osDesc;
    public static String fileSeparator = System.getProperty("file.separator");


    public static void initOS() {

        osDesc = System.getProperty("os.name").toLowerCase();
        osIsMacOsX = "mac os x".equals(osDesc);
        osIsWindows = osDesc.indexOf("windows") != -1;
        osIsWindowsXP = "windows xp".equals(osDesc);
        osIsWindows2003 = "windows 2003".equals(osDesc);
        osIsLinux = "linux".equals(osDesc);
    }

    /**
     * @return true if this VM is running on Mac OS X
     */
    public static boolean isMacOSX() {
        return osIsMacOsX;
    }

    /**
     * @return true if this VM is running on Windows
     */
    public static boolean isWindows() {
        return osIsWindows;
    }

    /**
     * @return true if this VM is running on Windows XP
     */
    public static boolean isWindowsXP() {
        return osIsWindowsXP;
    }

    /**
     * @return true if this VM is running on Windows 2003
     */
    public static boolean isWindows2003() {
        return osIsWindows2003;
    }

    /**
     * @return true if this VM is running on Linux
     */
    public static boolean isLinux() {
        return osIsLinux;
    }

    /**
     * @return true if the VM is running Windows and the Java application is
     *         rendered using XP Visual Styles.
     */
    public static boolean isUsingWindowsVisualStyles() {
        if (!isWindows()) {
            return false;
        }

        boolean xpthemeActive = Boolean.TRUE.equals(Toolkit.getDefaultToolkit()
                .getDesktopProperty("win.xpstyle.themeActive"));
        if (!xpthemeActive) {
            return false;
        } else {
            try {
                return System.getProperty("swing.noxp") != null;
            } catch (RuntimeException e) {
                return true;
            }
        }
    }

    /**
     * 获取本机IP
     */
    public static String getLocalIP() {
//	    Configuration config = BusinessAppServer.getInstance().getConfiguration();
//	    String address = config.getServerConf().getHttpEndpointConf().getAddress();
//	    if(address == null || "localhost".equals(address) || "127.0.0.1".equals(address)) {
//	     try {
//	       address = InetAddress.getLocalHost().getHostAddress();
//	     } catch (UnknownHostException e) {
//	     }
//	    }

        String ip = "";
        try {
            if (isLinux()) {
                Enumeration<?> e1 = (Enumeration<?>) NetworkInterface
                        .getNetworkInterfaces();
                while (e1.hasMoreElements()) {
                    NetworkInterface ni = (NetworkInterface) e1.nextElement();
                    if (!ni.getName().equals("")) {
                        continue;
                    } else {
                        Enumeration<?> e2 = ni.getInetAddresses();
                        while (e2.hasMoreElements()) {
                            InetAddress ia = (InetAddress) e2.nextElement();
                            if (ia instanceof Inet6Address)
                                continue;
                            ip = ia.getHostAddress();
                        }
                        break;
                    }
                }
            } else {
                ip = InetAddress.getLocalHost().getHostAddress().toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ip;
    }

    public static void main(String[] args) {
        System.out.println(getLocalIP());
    }

    /**
     * 获取主机名
     */
    public static String getLocalHostName() {
        InetAddress addr;
        String hostname = "";
        try {
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName().toString();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return hostname;
    }

    /**
     * 获取机器内存
     */
    public static String getMemory() {
        Runtime r = Runtime.getRuntime();
        return String.valueOf(r.totalMemory() / 1024 / 1024);
    }

    /**
     * 获取CPU主频率
     */
    public static String getCpuFre() {
        Runtime r = Runtime.getRuntime();
        return String.valueOf(r.availableProcessors());
    }

    /**
     * 获取CPU个数
     */
    public static int getCpuNum() {
        Runtime r = Runtime.getRuntime();
        return r.availableProcessors();
    }

    /**
     * 获取硬盘容量
     */
    public static String getDiskRam() {
        return null;
    }

    /**
     * 获取硬盘容量
     */
    public static String getOsName() {
        String os = "";
        try {
            os = System.getProperty("os.name") + "(" + System.getProperty("os.version") + ")" + System.getProperty("os.arch");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return os;
    }

    /**
     * 获取硬盘容量
     */
    public static String getUserName() {
        String userName = "";
        try {
            userName = System.getProperty("user.name");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return userName;
    }

    /**
     * 获取硬盘容量
     */
    public static String getJavaVersion() {
        String userName = "";
        try {
            userName = System.getProperty("java.version");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return userName;
    }

}