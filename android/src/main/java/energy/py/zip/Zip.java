package energy.py.zip;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    private static final int BUFFER = 2048;

    private void addFileToZip(File sourceFile, String asName, ZipOutputStream out) throws IOException {

        byte data[] = new byte[BUFFER];

        FileInputStream fi = new FileInputStream(sourceFile);
        BufferedInputStream origin = new BufferedInputStream(fi, BUFFER);
        ZipEntry entry = new ZipEntry(asName);
        entry.setTime(sourceFile.lastModified()); // to keep modification time after unzipping
        out.putNextEntry(entry);
        int count;
        while ((count = origin.read(data, 0, BUFFER)) != -1) {
            out.write(data, 0, count);
        }
        origin.close();
    }


    public File zipDirectory(Context context, String sourceDir, String destFilename) {

        File sourceFile = new File(sourceDir);
        try {
            File outputDir = context.getCacheDir();
            File outputFile = new File(outputDir.getPath() + File.separator + destFilename);

            FileOutputStream dest = new FileOutputStream(outputFile);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));

            int baseLength;
            if (sourceFile.isDirectory()) {
                baseLength = sourceFile.getPath().length() + 1; // +slash
            } else {
                baseLength = sourceFile.getParent().length();
            }

            zipRecursive(out, sourceFile, baseLength);

            out.close();

            return outputFile;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void zipRecursive(ZipOutputStream out, File file,
                              int basePathLength) throws IOException {

        if (file.isDirectory()) {
            File[] fileList = file.listFiles();
            for (File child : fileList) {
                zipRecursive(out, child, basePathLength);
            }
        } else {
            String unmodifiedFilePath = file.getPath();
            String relativePath = unmodifiedFilePath
                    .substring(basePathLength);

            addFileToZip(file, relativePath, out);
        }
    }

}
