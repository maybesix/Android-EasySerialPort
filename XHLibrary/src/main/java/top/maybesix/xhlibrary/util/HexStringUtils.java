package top.maybesix.xhlibrary.util;

/**
 * @author MaybeSix
 * @date 2019/3/18
 */
public class HexStringUtils {
    /**
     * 判断是否为奇数，位运算，最后一位是1则为奇数，为0是偶数
     *
     * @param num 传入的int数据
     * @return 如果为奇数，返回true；如果为偶数返回false
     */
    public static boolean isOdd(int num) {
        return (num & 0x1) == 1;
    }

    /**
     * 16进制字符串转int
     *
     * @param hexString 传入的十六进制字符串
     * @return 转换后的结果
     */
    public static int hexString2Int(String hexString) {
        return Integer.parseInt(hexString, 16);
    }

    /**
     * 16进制字符串转byte
     *
     * @param hexString 传入的十六进制字符串
     * @return 转换后的结果
     */
    public static byte hexString2Byte(String hexString) {
        return (byte) Integer.parseInt(hexString, 16);
    }

    /**
     * Byte 转 十六进制字符串
     *
     * @param hexByte 传入的数据
     * @return 转换后的结果
     */
    public static String byte2HexString(Byte hexByte) {
        return String.format("%02x", hexByte).toUpperCase();
    }

    /**
     * 字节数组转hex字符串
     *
     * @param hexbytearray 传入的数据
     * @return 转换后的结果
     */
    public static String byteArray2HexString(byte[] hexbytearray) {

        return byteArray2HexString(hexbytearray, 0, hexbytearray.length);
    }

    /**
     * 字节数组转转hex字符串
     *
     * @param hexbytearray 传入的数据
     * @return 转换后的结果
     */
    public static String byteArray2HexString(byte[] hexbytearray, int beginIndex, int endIndex)//字节数组转转hex字符串，可选长度
    {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = beginIndex; i < endIndex; i++) {
            strBuilder.append(byte2HexString(hexbytearray[i]));
        }
        return strBuilder.toString();
    }

    /**
     * hex字符串转字节数组
     *
     * @param hexString 传入的数据
     * @return 转换后的结果
     */
    public static byte[] hexString2ByteArray(String hexString) {
        int len = hexString.length();
        byte[] result;
        if (isOdd(len)) {
            //奇数
            len++;
            result = new byte[(len / 2)];
            hexString = "0" + hexString;
        } else {
            //偶数
            result = new byte[(len / 2)];
        }
        int j = 0;
        for (int i = 0; i < len; i += 2) {
            result[j] = hexString2Byte(hexString.substring(i, i + 2));
            j++;
        }
        return result;
    }
}
