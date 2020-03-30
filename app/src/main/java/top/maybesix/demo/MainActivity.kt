package top.maybesix.demo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import top.maybesix.easyserialport.EasySerialPort
import top.maybesix.easyserialport.util.HexStringUtils

class MainActivity : AppCompatActivity() {
    private lateinit var serialPort: EasySerialPort
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //初始化
        serialPort = EasySerialPort.Builder()
            .setBaudRate(9600)
            .setPort("")
            .setSatesListener(object : EasySerialPort.OnStatesChangeListener {
                /**
                 * 打开的状态回调
                 *
                 * @param isSuccess 是否成功
                 * @param reason    原因
                 */
                override fun onOpen(isSuccess: Boolean, reason: String) {
                    Log.i("EasySerialPort", "是否开启成功：$isSuccess,原因：$reason")
                    Toast.makeText(
                        applicationContext,
                        "是否开启成功：$isSuccess,原因：$reason",
                        Toast.LENGTH_SHORT
                    ).show()

                }

                /**
                 * 关闭的状态回调
                 */
                override fun onClose() {
                    Log.i("EasySerialPort", "已关闭")
                    Toast.makeText(applicationContext, "已关闭", Toast.LENGTH_SHORT).show()
                }
            })
            .setListener {
                //处理接收的串口消息
                val s: String = HexStringUtils.byteArray2HexString(it.recData)
                Log.i("EasySerialPort", "onReceived: $s,time:${it.recTime}")
                textView.text = s
            }
            .build()

        btn_open.setOnClickListener {
            serialPort.open()
        }
        btn_close.setOnClickListener {
            serialPort.close()
        }
        btn_send.setOnClickListener {
            if (serialPort.isNotOpen) {
                Toast.makeText(applicationContext, "请先开启串口", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "发送：${et_send.text.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
                serialPort.sendTxtString(et_send.text.toString())
            }
        }
    }

}
