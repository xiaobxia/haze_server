package com.info.web.util.yjr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * 压缩工具类
 */
public class CompressUtil {

    private static final Logger log =LoggerFactory.getLogger(CompressUtil.class);

    /**
     * 使用deflate 压缩文件内容,做base64编码
     *
     * @param filePath 文件路径
     */
    public static String enCodeFileContent(String filePath) {
        String baseFileContent = "";

        File file = new File(filePath);
        if (!file.exists()) {
            return baseFileContent;
        }
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            int fileLength = in.available();
            byte[] fileContent = new byte[fileLength];
            in.read(fileContent, 0, fileLength);
            baseFileContent = new String(Base64.encode(deflater(fileContent)), "UTF-8");
        } catch (Exception e) {
            log.error("enCodeFileContent error:{}",e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("enCodeFileContent error:{}",e);
                }
            }
        }
        return baseFileContent;
    }

    /**
     * 功能：解压fileContent字符串并写入对应的文件中（ 解base64，解DEFLATE压缩）
     *
     * @param fileContent 压缩文件
     * @param fileAbsPath 写入的文件（绝对路径）
     */
    public static boolean deCodeFileContent(String fileContent, String fileAbsPath) {
        boolean decodeResult = false;
        if (null != fileContent && !"".equals(fileContent)) {
            FileOutputStream fileOutputStream = null;
            try {
                byte[] fileArray = inflater(Base64.decode(fileContent.getBytes("UTF-8")));

                File file = new File(fileAbsPath);
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();

                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(fileArray, 0, fileArray.length);
                fileOutputStream.flush();

                decodeResult = true;
            } catch (Exception e) {
                log.error("deCodeFileContent error:{}",e);
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        log.error("deCodeFileContent error:{}",e);
                    }
                }
            }
        }
        return decodeResult;
    }

    /**
     * 解压缩.
     *
     * @param inputByte byte[]数组类型的数据
     * @return 解压缩后的数据
     * @throws IOException
     */
    public static byte[] inflater(final byte[] inputByte) throws IOException {
        int compressedDataLength;
        Inflater compresser = new Inflater();
        compresser.setInput(inputByte, 0, inputByte.length);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(inputByte.length);
        byte[] result = new byte[1024];
        try {
            while (!compresser.finished()) {
                compressedDataLength = compresser.inflate(result);
                if (compressedDataLength == 0) {
                    break;
                }
                byteArrayOutputStream.write(result, 0, compressedDataLength);
            }
        } catch (Exception e) {
            log.error("inflater error:{}",e);
        } finally {
            byteArrayOutputStream.close();
        }
        compresser.end();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 压缩.
     *
     * @param inputByte 需要解压缩的byte[]数组
     * @return 压缩后的数据
     */
    public static byte[] deflater(final byte[] inputByte) throws IOException {
        int compressedDataLength;
        Deflater compresser = new Deflater();
        compresser.setInput(inputByte);
        compresser.finish();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(inputByte.length);
        byte[] result = new byte[1024];
        try {
            while (!compresser.finished()) {
                compressedDataLength = compresser.deflate(result);
                if (compressedDataLength == 0) {
                    break;
                }
                byteArrayOutputStream.write(result, 0, compressedDataLength);
            }
        } finally {
            byteArrayOutputStream.close();
        }
        compresser.end();
        return byteArrayOutputStream.toByteArray();
    }

}
