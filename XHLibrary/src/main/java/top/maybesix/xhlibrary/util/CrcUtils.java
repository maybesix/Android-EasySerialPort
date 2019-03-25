package top.maybesix.xhlibrary.util;

/**
 * @Author MaybeSix
 * @Date 2019/1/13 14:29
 * @Version 1.0
 * @Description 用于CRC16 CCITT(1024)算法的CRC校验
 */
public class CrcUtils {
    private static final String TAG = "CrcUtils";

    /**
     * 放入完整的bytes数据，返回验证结果
     * 例如：{0x5A,0xA5,0xD5,0x01,0x01,0x00,0x00,0x3D,0x44,0x96}
     * head：0xA5,0x5A;
     * end:0x96;
     * bodyAndCrc:0xD5,0x01,0x01,0x00,0x00,0x3D,0x44
     * 验证的内容包含：
     * 1、验证head是否为：0xA5,0x5A。
     * 2、验证end是否为：0x96
     * 3、验证getCrc(bodyAndCrc)结果是否为0
     * 注：crc验证码有可能高八位和低八位颠倒，所以实际过程中计算两次：
     * 一次直接运算，另一次将高低位颠倒过来再运算一次，取或值返回
     *
     * @param bytes 参与运算的全部串口数据
     * @return 结果
     */
    public static boolean isPassCRC(byte[] bytes) {
        int length = bytes.length;
        byte[] head = new byte[2];
        byte[] end = new byte[1];
        byte[] body = new byte[length - 5];
        byte valueHead1 = (byte) 0x5A;
        byte valueHead2 = (byte) 0xA5;
        byte valueEnd = (byte) 0x96;
        byte crcLow = bytes[length - 2];
        byte crcHigh = bytes[length - 3];
        System.arraycopy(bytes, 0, head, 0, 2);
        System.arraycopy(bytes, length - 1, end, 0, 1);
        System.arraycopy(bytes, 2, body, 0, length - 5);
        byte[] bodyAndCrcOne = new byte[body.length + 2];
        System.arraycopy(body, 0, bodyAndCrcOne, 0, body.length);
        bodyAndCrcOne[body.length] = crcHigh;
        bodyAndCrcOne[body.length + 1] = crcLow;
        byte[] bodyAndCrcTwo = new byte[body.length + 2];
        System.arraycopy(body, 0, bodyAndCrcTwo, 0, body.length);
        bodyAndCrcOne[body.length] = crcLow;
        bodyAndCrcOne[body.length + 1] = crcHigh;
        if (head[0] == valueHead1 && head[1] == valueHead2) {
            if (end[0] == valueEnd) {
                return getCrc(bodyAndCrcOne) == 0 || getCrc(bodyAndCrcTwo) == 0;
            } else {
                System.out.println("end校验失败 ");
            }
        } else {
            System.out.println("head校验失败 ");
        }
        return false;
    }

    /**
     * 放入完整的hexString数据，返回验证结果
     * 例如："5A A5 D5 01 01 00 00 3D 44 96"
     * head：5A A5;
     * end:96;
     * bodyAndCrc: D5 01 01 00 00 3D 44
     * 验证的内容包含：
     * 1、验证head是否为：5A A5。
     * 2、验证end是否为：96。
     * 3、验证getCrc(bodyAndCrc)结果是否为0。
     * 注：crc验证码有可能高八位和低八位颠倒，所以实际过程中计算两次：
     * 一次直接运算，另一次将高低位颠倒过来再运算一次，取或值返回。
     *
     * @param hexString 参与运算的全部串口数据
     * @return 结果
     */
    public static boolean isPassCRC(String hexString) {
        try {
            hexString = hexString.replace(" ", "");
            int length = hexString.length();
            String head = hexString.substring(0, 4);
            String end = hexString.substring(length - 2, length);
            String body = hexString.substring(4, length - 6);
            String crcHigh = hexString.substring(length - 6, length - 4);
            String crcLow = hexString.substring(length - 4, length - 2);
            String bodyAndCrcOne = body + crcHigh + crcLow;
            String bodyAndCrcTwo = body + crcLow + crcHigh;
            String valueHead = "5AA5";
            String valueEnd = "96";
            if (head.equals(valueHead)) {
                if (end.equals(valueEnd)) {
                    return getCrc(bodyAndCrcOne) == 0 || getCrc(bodyAndCrcTwo) == 0;
                } else {
                    System.out.println("end校验失败 ");
                }
            } else {
                System.out.println("head校验失败 ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 获取十六进制的crc字符串
     *
     * @param hexString String字符串
     * @return crc字符串
     */
    public static String getCrcString(String hexString) {
        return toHexString(getCrc(hexString));
    }

    /**
     * 获取十六进制的crc字符串
     *
     * @param bytes byte数组
     * @return 十六进制crc字符串
     */
    public static String getCrcString(byte[] bytes) {
        return toHexString(getCrc(bytes));
    }

    /**
     * int转化为十六进制字符串，并且判断十六进制字符串长度是否为4，小于4在高位补零
     *
     * @param crc 需要转化的int值
     * @return 返回十六进制字符串
     */
    private static String toHexString(int crc) {
        String result = Integer.toHexString(crc).toUpperCase();
        int length = result.length();
        int fixedLength = 4;
        if (length != fixedLength) {
            for (int i = (fixedLength - length); i > 0; i--) {
                result = "0" + result;
            }
        }
        return result;
    }

    /**
         * 传入bytes，计算得到CRC验证码
     *
     * @param bytes 参与运算的数组
     * @return CRC十六进制字符串的验证码
     */
    private static int getCrc(byte[] bytes) {
        //初始值
        int crc = 0xffff;
        //公式
        int polynomial = 0x1021;
        for (byte b : bytes) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) {
                    crc ^= polynomial;
                }
            }
        }
        crc &= 0xffff;
        return crc;
    }


    /**
     * 传入十六进制字符串，计算得到CRC验证码
     *
     * @param hexString 参与运算的字符串
     * @return CRC十六进制字符串的验证码
     */
    private static int getCrc(String hexString) {
        return getCrc(hexToByte(hexString));
    }

    /**
     * 十六进制字符串转byte数组
     * 每两个字符描述一个字节
     *
     * @param hex 十六进制字符串
     * @return 返回byte
     */
    private static byte[] hexToByte(String hex) {
        //去空格
        hex = hex.replace(" ", "");
        int m = 0, n = 0;
        int byteLen = hex.length() / 2;
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = (byte) intVal;
        }
        return ret;
    }

}
