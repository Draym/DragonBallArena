package com.andres_k.utils.tools;

import com.andres_k.utils.configs.GameInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

/**
 * Created by kevin on 18/07/2017.
 */
public class DLLTools {
    private final static String[] WIN_DLL32 = new String[]{"jinput-dx8.dll", "jinput-raw.dll", "lwjgl.dll", "OpenAL32.dll"};
    private final static String[] WIN_DLL64 = new String[]{"jinput-dx8_64.dll", "jinput-raw_64.dll", "lwjgl64.dll","OpenAL64.dll"};
    private final static String[] LIN_DLL32 = new String[]{"libjinput-linux.so", "liblwjgl.so", "libopenal.so"};
    private final static String[] LIN_DLL64 = new String[]{"libjinput-linux64.so", "liblwjgl64.so", "libopenal64.so"};
    private final static String[] MAC_DLL = new String[]{"libjinput-osx.jnilib", "liblwjgl.jnilib", "openal.dylib"};

    public static void init() {
        Console.write("DLL: Loading");
        String[] dlls = new String[]{};
        String os = System.getProperty("os.name");
        String arch = System.getProperty("os.arch");
        String libPath = "";

        if (os.contains("Windows")) {
            libPath = "/native/windows/";
            if (arch.contains("64")) {
                dlls = WIN_DLL64;
            } else {
                dlls = WIN_DLL32;
            }
        } else if (os.contains("Linux")) {
            libPath = "/native/linux/";
            if (arch.contains("64")) {
                dlls = LIN_DLL64;
            } else {
                dlls = LIN_DLL32;
            }
        } else if (os.contains("Mac")) {
            libPath = "/native/macosx/";
            dlls = MAC_DLL;
        }

        loadFromJar(libPath, dlls);
    }

    /**
     * When packaged into JAR extracts DLLs, places these into
     */
    private static void loadFromJar(String libPath, String[] dlls) {
        String path = GameInfo.get().getGamePathTMP() + libPath;
        try {
            for (String dll : dlls) {
                File f = new File(path + dll);
                if (!f.exists()) {
                    loadLib(libPath, path, dll);
                }
            }

            System.setProperty("java.library.path", path);
            Field fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
            fieldSysPath.setAccessible( true );
            fieldSysPath.set( null, null );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Puts library to temp dir and loads to memory
     */
    private static void loadLib(String srcPath, String finalPath, String name) throws Exception {
        try {
            InputStream in = DLLTools.class.getResourceAsStream(srcPath + name);
            File fileOut = new File(finalPath + name);
            OutputStream out = FileUtils.openOutputStream(fileOut);
            IOUtils.copy(in, out);
            in.close();
            out.close();
        } catch (Exception e) {
            throw new Exception("Failed to load required DLL", e);
        }
    }
}
