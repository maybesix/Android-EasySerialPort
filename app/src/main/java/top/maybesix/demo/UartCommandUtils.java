package top.maybesix.demo;

import android.util.Log;

import java.util.Random;

/**
 * @author xxh
 * @date 2019/1/9
 */
public class UartCommandUtils {
    private final static String START = "5AA5";
    private final static String END = "96";
    //消息类型type
    private final static String PACKET_TYPE_HEARTBEAT = "01";
    public final static String PACKET_TYPE_FAULT = "02";
    public final static String PACKET_TYPE_QUERY = "03";
    public final static String PACKET_TYPE_SET = "04";
    //具体的content
    public final static String PACKET_CONTENT_HEARTBEAT = "01";
    public final static String PACKET_CONTENT_BATTERY_VOLTAGE = "02";
    public final static String PACKET_CONTENT_CAPACITOR_VOLTAGE = "03";
    public final static String PACKET_CONTENT_SOLAR_VOLTAGE = "04";
    public final static String PACKET_CONTENT_THE_POWER_SUPPLY_SOURCE = "05";
    public final static String PACKET_CONTENT_CAPACITOR_CHARGING_STATUS = "06";
    public final static String PACKET_CONTENT_BATTERY_CHARGING_STATUS = "07";
    public final static String PACKET_CONTENT_POWER_MODULE_INFORMATION = "DE";

    /**
     * 获取0~15进制随机数
     * * @return String
     */
    public static String randomHexString(int max) {
        if (max > 16) {
            max = 16;
        }
        return Integer.toHexString(new Random().nextInt(16));
    }

    /**
     * 获取范围00H~6FH的两位十六进制PacketId，70H~7FH暂且保留
     *
     * @return String
     */
    private static String getRandomPacketId() {
        return (randomHexString(7) + randomHexString(16)).toUpperCase();
    }

    /**
     * 构建基础的串口通讯消息
     *
     * @param packetType
     * @param packetContent
     * @return
     */
    private static String baseCommand(String packetType, String packetContent) {
        String packetId = getRandomPacketId();
//        String packetLen = Integer.toHexString(packetContent.length());
//        String packetId = "00";
        String packetLen = "0100";
        String body = packetId + packetType + packetLen + packetContent;
        Log.i("", "baseCommand: body为："+body);
        String crc = CrcUtils.getCrcString(body);
        return START + body + crc + END;
    }

    /**
     * 心跳报文
     *
     * @return
     */
    public static String getHeartbeat() {
        return baseCommand(PACKET_TYPE_HEARTBEAT, PACKET_CONTENT_HEARTBEAT);
    }

    /**
     * 查询电池电压报文
     *
     * @return
     */
    public static String getBatteryVoltage() {
        return baseCommand(PACKET_TYPE_QUERY, PACKET_CONTENT_BATTERY_VOLTAGE);

    }

    /**
     * 查询电容电压报文
     *
     * @return
     */
    public static String getCapacitorVoltage() {
        return baseCommand(PACKET_TYPE_QUERY, PACKET_CONTENT_CAPACITOR_VOLTAGE);

    }

    /**
     * 查询太阳能电压报文
     *
     * @return
     */
    public static String getSolarVoltage() {
        return baseCommand(PACKET_TYPE_QUERY, PACKET_CONTENT_SOLAR_VOLTAGE);
    }

    /**
     * 当前主供电来源报文
     *
     * @return
     */
    public static String getThePowerSupplySource() {
        return baseCommand(PACKET_TYPE_QUERY, PACKET_CONTENT_THE_POWER_SUPPLY_SOURCE);
    }
    /**
     * 查询当前电容充电状态报文
     *
     * @return
     */
    public static String getCapacitorChargingStatus() {
        return baseCommand(PACKET_TYPE_QUERY, PACKET_CONTENT_CAPACITOR_CHARGING_STATUS);
    }

    /**
     * 查询当前电池充电状态报文
     *
     * @return
     */
    public static String getBatteryChargingStatus() {
        return baseCommand(PACKET_TYPE_QUERY, PACKET_CONTENT_BATTERY_CHARGING_STATUS);
    }

    /**
     * 查询当前电源供电模块的关键信息（Debug 用）
     *
     * @return
     */
    public static String getPowerModuleInformation() {
        return baseCommand(PACKET_TYPE_QUERY, PACKET_CONTENT_POWER_MODULE_INFORMATION);
    }

}
