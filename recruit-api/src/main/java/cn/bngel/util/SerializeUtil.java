package cn.bngel.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class SerializeUtil {

    private static final String DEFAULT_CHARSET = "ISO-8859-1";

    /**
     * 将对象序列化为String
     * @param obj 被序列化的对象
     * @return 返回序列化后的String
     * @throws Exception 捕获IO异常
     */
    public static String serializeToString(Object obj){
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
            objOut.writeObject(obj);
            return byteOut.toString(DEFAULT_CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将String反序列化为对象
     * @param str 被反序列化的String
     * @return 返回反序列化后的对象
     * @throws Exception 捕获IO异常
     */
    public static Object deserializeToObject(String str){
        try {
            ByteArrayInputStream byteIn = new ByteArrayInputStream(str.getBytes(DEFAULT_CHARSET));
            ObjectInputStream objIn = new ObjectInputStream(byteIn);
            return objIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
