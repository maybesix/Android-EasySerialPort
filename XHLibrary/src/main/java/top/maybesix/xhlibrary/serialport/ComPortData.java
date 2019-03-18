package top.maybesix.xhlibrary.serialport;

import java.text.SimpleDateFormat;

/**
 * @author MaybeSix
 * @date 2019/3/18
 */
public class ComPortData {
    private byte[] recData;
    private String recTime;
    private String comPort;

    public ComPortData(String comPort, byte[] buffer, int size){
        this.comPort=comPort;
        recData=new byte[size];
        System.arraycopy(buffer, 0, recData, 0, size);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("hh:mm:ss");
        recTime = sDateFormat.format(new java.util.Date());
    }
    public byte[] getRecData() {
        return recData;
    }

    public void setRecData(byte[] recData) {
        this.recData = recData;
    }

    public String getRecTime() {
        return recTime;
    }

    public void setRecTime(String recTime) {
        this.recTime = recTime;
    }

    public String getComPort() {
        return comPort;
    }

    public void setComPort(String comPort) {
        this.comPort = comPort;
    }

}
