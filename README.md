# Android-XHLibrary
一个简易的串口操作程序
## 使用说明
第一步，在gradle(Project)下添加
```
allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```
第二步，导入依赖
```
dependencies {
	        implementation 'com.github.maybesix:Android-XHLibrary:v1.0.0'
	}
```
在需要实现Activity或者Service中这样写：

``` 
SerialPortHelper serialPort;
String port = "/dev/ttyHSL1";
int baudRate = 9600;
//串口程序初始化
serialPort = new SerialPortHelper(port, baudRate, this);
//打开串口
serialPort.open();
```
串口发送：
```
//发送十六进制
 serialPort.sendHex("A55A0010002096");
//发送文本
 serialPort.sendHex("hello world");
```
串口接收：实现SerialPortHelper.OnSerialPortReceivedListener接口
```
public class MainActivity extends AppCompatActivity implements SerialPortHelper.OnSerialPortReceivedListener {
...
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
...
    }
...
@Override
    public void onSerialPortDataReceived(ComPortData comPortData) {
        //处理接收的串口消息
        String s = HexStringUtils.byteArray2HexString(comPortData.getRecData());
        Log.i(TAG, "onReceived: " + s);
    }
}
```
至此，串口的打开、发送、接收就全部完成了。
## 串口相关
> 串口操作类 → [SerialPortHelper](https://github.com/maybesix/Android-XHLibrary/blob/master/XHLibrary/src/main/java/top/maybesix/xhlibrary/serialport/SerialPortHelper.java)
```
isOpen                        : 是否开启串口
getBaudRate                   : 获取波特率
setBaudRate                   : 设置波特率
getPort                       :  获取串口名称
setPort                       :  设置串口名称
open                          :  打开串口
close                         :  关闭串口
sendHex                       :  以16进制发送
sendTxtString                 :  以文本发送
getLoopData                   :  获取循环发送的数据
setLoopData                   :  设置循环发送的数据
getDelay                      :  获取延迟
setDelay                      :  设置延时（毫秒）
startSend                     :  开启循环发送
stopSend                      :  停止循环发送
OnSerialPortReceivedListener  :  串口数据接收回调
```
> 串口数据基类 → [ComPortData](https://github.com/maybesix/Android-XHLibrary/blob/master/XHLibrary/src/main/java/top/maybesix/xhlibrary/serialport/ComPortData.java)
```
getRecData   :  获取串口数据
setRecData   :  设置串口数据
getRecTime   :  获取接收时间
setRecTime   :  设置接受时间
getComPort   :  获取串口名称
setComPort   :  设置串口名称
```
## 串口数据处理相关
> 十六进制转换 → [HexStringUtils](https://github.com/maybesix/Android-XHLibrary/blob/master/XHLibrary/src/main/java/top/maybesix/xhlibrary/util/HexStringUtils.java)
```
isOdd                  : 判断是否为奇数
hexString2Int          : 16进制字符串转int
hexString2Byte         : 16进制字符串转byte
byte2HexString         : byte转16进制字符串
byteArray2HexString    : byte数组转16进制字符串
hexString2ByteArray    : 16进制字符串转byte数组
```
> CRC校验 → [CrcUtils](https://github.com/maybesix/Android-XHLibrary/blob/master/XHLibrary/src/main/java/top/maybesix/xhlibrary/util/CrcUtils.java)
```
isPassCRC     : 返回是否通过验证
getCrcString  : 获取16进制的crc字符串
toHexString   : int转16进制字符串
getCrc        : 传入bytes，计算得到CRC验证码
hexToByte     : 16进制字符串转byte数组
```
## 如果这个项目对你有帮助，请点个star！