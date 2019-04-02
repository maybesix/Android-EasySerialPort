package top.maybesix.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ServiceUtils;

import top.maybesix.xhlibrary.serialport.ComPortData;
import top.maybesix.xhlibrary.serialport.SerialPortHelper;
import top.maybesix.xhlibrary.util.HexStringUtils;

/**
 * @author MaybeSix
 */
public class MainActivity extends AppCompatActivity implements SerialPortHelper.OnSerialPortReceivedListener {
    private static final String TAG = "MainActivity";
    SerialPortHelper serialPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initSerialPort();
    }

    private void initSerialPort() {
        String port = "/dev/ttyHSL1";
        int baudRate = 9600;
        //串口程序初始化
        serialPort = new SerialPortHelper(port, baudRate, this);
        //打开串口
        serialPort.open();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView() {
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serialPort.sendHex("A55A01000396");
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
