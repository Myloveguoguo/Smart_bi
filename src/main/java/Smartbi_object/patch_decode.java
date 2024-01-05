package Smartbi_object;

import org.apache.commons.codec.binary.Base64InputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.*;

public class patch_decode {
    public static void main(String[] args) throws Exception {
        ZipInputStream zip = open();
        ZipEntry entry = zip.getNextEntry();
        while(true) {
            if (entry == null) {
                break;
            }
            System.out.println(entry.getName());
            byte[] patchContent = new byte[0];
            patchContent = readFromStream(new FilterInputStream(zip) {
                public void close() {
                }
            }, "UTF-8");
            String className = entry.getName().substring(entry.getName().lastIndexOf("/") + 1);
            if (className.equals("patch.patches")){
                className = "patched.patches";
            }
            writeFile(className, patchContent);
            entry = zip.getNextEntry();
        }
    }

    public static void writeFile(String filePath, byte[]content) throws Exception {
        FileOutputStream outputStream = new FileOutputStream(filePath);
        outputStream.write(content);
        outputStream.close();
    }
    public static byte[] readFromStream(InputStream is, String charset) throws IOException {
        byte[] bs = streamToByteArray(is);
        return bs;
    }
    public static byte[] streamToByteArray(InputStream is) throws IOException {
        if (is == null) {
            return null;
        } else {
            BufferedInputStream in = new BufferedInputStream(is);
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(bo);
            byte[] tmpBytes = new byte[1024];
            int readed;
            while((readed = in.read(tmpBytes)) != -1) {
                bos.write(tmpBytes, 0, readed);
            }

            in.close();
            bos.close();
            byte[] bs = bo.toByteArray();
            return bs;
        }
    }
    public static ZipInputStream open() throws Exception {
        FileInputStream in = new FileInputStream("C:\\Users\\nsfocus\\Desktop\\sj\\Smartbi\\src\\main\\java\\Smartbi_object\\patch.patches");
        Base64InputStream bin = new Base64InputStream(in);
        String mode = "AES/CBC/PKCS5Padding";
        String key = "1234567812345678";
        String iv = "1234567812345678";
        Cipher cipher = Cipher.getInstance(mode);
        SecretKeySpec keyspec = new SecretKeySpec(key.getBytes("utf-8"), "AES");
        IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes("utf-8"));
        cipher.init(2, keyspec, ivspec);
        CipherInputStream cin = new CipherInputStream(bin, cipher);
        return new ZipInputStream(cin);
    }

}