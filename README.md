# Android-XHLibrary
一个简易的串口操作程序
## 使用方法
```
SerialPortHelper serialPort;
//串口程序初始化
serialPort = new SerialPortHelper();
//设置端口
serialPort.setPort("/dev/ttyHSL1");
//设置波特率
serialPort.setBaudRate("9600");
//设置接收消息监听
serialPort.setSerialPortReceivedListener(this);
//打开串口
serialPort.open();
```
接收需要实现SerialPortHelper.OnSerialPortReceivedListener接口
```
implements SerialPortHelper.OnSerialPortReceivedListener

   @Override
    public void onSerialPortDataReceived(ComPortData comPortData) {
        //处理接收的串口消息
        String s = HexStringUtils.byteArray2HexString(comPortData.getRecData());
        Log.i(TAG, "onReceived: " + s);
    }
```
