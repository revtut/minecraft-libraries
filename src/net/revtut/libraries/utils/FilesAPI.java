package net.revtut.libraries.utils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Files Library.
 *
 * <P>A library with methods related to files.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class FilesAPI {

    /**
     * Constructor of FilesAPI
     */
    private FilesAPI() {}

    /**
     * Copy from a file to another one
     *
     * @param inFile file to be copied
     * @param outFile file to copy to
     * @return true if successfull
     */
    public static boolean copyFile(final File inFile, final File outFile) {
        try {
            InputStream inputStream = new FileInputStream(inFile);
            OutputStream outputStream = new FileOutputStream(outFile);
            final byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Copy existing directory to new location.
     *
     * @param srcDir source of the folder to copy
     * @param trgDir target of the folder
     * @return true if successfull
     */
    public static boolean copyDirectory(final File srcDir, final File trgDir) {
        try {
            if (srcDir.isDirectory()) {
                // Check if target folder exists
                if (!trgDir.exists())
                    if(!trgDir.mkdirs())
                        return false;
                // List of files inside source directory
                String[] fList = srcDir.list();
                for (String aFList : fList) {
                    File dest = new File(trgDir, aFList);
                    File source = new File(srcDir, aFList);

                    // Copy that file / directory
                    copyDirectory(source, dest);
                }
            } else {
                // Copy the file
                copyFile(srcDir, trgDir);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Delete directory. Sub-files and sub-directories will be deleted to.
     *
     * @param dir folder to remove
     * @return true it successfull when removing directory
     */
    public static boolean removeDirectory(final File dir) {
        try {
            if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                if (files != null)
                    for (File c : files)
                        removeDirectory(c);
            }
            if(!dir.delete())
                Logger.getLogger("Minecraft").log(Level.WARNING, "Error while trying to delete " + dir.getName() + ".");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
