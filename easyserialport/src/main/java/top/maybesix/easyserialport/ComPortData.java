package top.maybesix.easyserialport;

/**
 * @author MaybeSix
 * @date 2019/3/18
 */
public class ComPortData {
    private byte[] recData;
    private long recTime;
    private String comPort;

    ComPortData(String comPort, byte[] buffer, int size) {
        this.comPort = comPort;
        recData = new byte[size];
        System.arraycopy(buffer, 0, recData, 0, size);
        recTime = System.currentTimeMillis();
    }

    public byte[] getRecData() {
        return recData;
    }

    public void setRecData(byte[] recData) {
        this.recData = recData;
    }

    public long getRecTime() {
        return recTime;
    }

    public void setRecTime(long recTime) {
        this.recTime = recTime;
    }

    public String getComPort() {
        return comPort;
    }

    public void setComPort(String comPort) {
        this.comPort = comPort;
    }

}
