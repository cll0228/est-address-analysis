package com.lezhi.address.admin.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Colin Yan on 2016/7/19.
 */
public class ZipUtil {

    public static void compressFolder(File folder, File output) throws IOException {
        if (!folder.isDirectory())
            return;

        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(output));
        //zipOut.setComment(folder.getName());
        zipOut.setLevel(ZipOutputStream.STORED);

        File[] files = folder.listFiles();
        if (files == null || files.length == 0)
            return;

        byte[] buffer = new byte[102400];

        for (File file1 : files) {
            InputStream input = new FileInputStream(file1);
            zipOut.putNextEntry(new ZipEntry(file1.getName()));
            int offset;
            while ((offset = input.read(buffer)) != -1) {
                zipOut.write(buffer, 0, offset);
            }
            input.close();
        }
        zipOut.close();
    }

}
