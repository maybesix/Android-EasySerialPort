package top.maybesix.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import top.maybesix.xhlibrary.serialport.ComPortData;
import top.maybesix.xhlibrary.serialport.SerialPortHelper;
import top.maybesix.xhlibrary.util.HexStringUtils;

/**
 * @author MaybeSix
 */
public class MainActivity extends AppCompatActivity implements SerialPortHelper.OnSerialPortReceivedListener {
    private static final String TAG = "MainActivity";
    private Button button;
    SerialPortHelper serialPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initSerialPort();
    }

    private void initSerialPort() {
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        //执行发送
        serialPort.sendHex("A55A0010002096");
        serialPort.sendHex("hello world");

    }

    private void initView() {
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行发送
                serialPort.sendHex("A55A0010002096");
                serialPort.sendHex("hello world");
            }
        });
    }

    @Override
    public void onSerialPortDataReceived(ComPortData comPortData) {
        //处理接收的串口消息
        String s = HexStringUtils.byteArray2HexString(comPortData.getRecData());
        Log.i(TAG, "onReceived: " + s);
    }
}
