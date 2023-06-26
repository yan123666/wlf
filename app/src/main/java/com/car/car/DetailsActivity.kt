package com.car.car

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

/**
 * 车辆详情界面
 */
class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_wlf)
        //获取传递数据
        var carBean:CarBean = intent.getSerializableExtra("car") as CarBean
        //初始化布局
        var iv_img = findViewById<ImageView>(R.id.iv_img)
        var tv_detail_msg = findViewById<TextView>(R.id.tv_detail_msg)
        var bt_buy = findViewById<Button>(R.id.bt_buy)

        //显示数据 图片显示和详情
        iv_img.setImageResource(carBean.img)
        tv_detail_msg.setText(carBean.name+carBean.describe+carBean.price+"万")
        //跳转到计算界面
        bt_buy.setOnClickListener {
            val intent = Intent(this, CalculateActivity::class.java);
            intent.putExtra("car", carBean)
            startActivity(intent)
        }

    }
}