package edu.neu.absorb.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.os.Environment;
public class FileUtil {


        private static File CacheRoot;
        /**
         * save Json file
         *
         * @param context
         * @param json json
         *
         * @param fileName
         *            fileName
         * @param append
         *            true add it to the end of file，false overwrite file
         */
        public static void writeJson(Context c, String json, String fileName,
                                     boolean append) {

            CacheRoot = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ? c
                    .getExternalCacheDir() : c.getCacheDir();
            FileOutputStream fos = null;
            ObjectOutputStream os = null;
            try {
                File ff = new File(CacheRoot, fileName);
                boolean boo = ff.exists();
                fos = new FileOutputStream(ff, append);
                os = new ObjectOutputStream(fos);
                if (append && boo) {
                    FileChannel fc = fos.getChannel();
                    fc.truncate(fc.position() - 4);

                }

                os.writeObject(json);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if (os != null) {

                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }

        /**
         * read json data
         *
         * @param c
         * @param fileName
         * @return return list
         */

        @SuppressWarnings("resource")
        public static List<String> readJson(Context c, String fileName) {

            CacheRoot = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ? c
                    .getExternalCacheDir() : c.getCacheDir();
            FileInputStream fis = null;
            ObjectInputStream ois = null;
            List<String> result = new ArrayList<String>();
            File des = new File(CacheRoot, fileName);
            try {
                fis = new FileInputStream(des);
                ois = new ObjectInputStream(fis);
                while (fis.available() > 0)
                    result.add((String) ois.readObject());

            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (StreamCorruptedException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            } catch (ClassNotFoundException e) {

                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            return result;
        }

    }