package com.car.car

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/**
 * 计算金额界面
 */
class CalculateActivity : AppCompatActivity() {
    private  var  dividel:Boolean=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_wlf)
         //获取传递过来车辆信息
        var carBean:CarBean = intent.getSerializableExtra("car") as CarBean

        //显示车辆价格
        var et_price = findViewById<TextView>(R.id.et_price)
        et_price.setText("￥"+(carBean.price!!.toDouble()*10000))

        //初始化布局
        var seek_DisCount_Percent = findViewById<SeekBar>(R.id.seek_DisCount_Percent)
        var tv_DisCount_Account = findViewById<TextView>(R.id.tv_DisCount_Account)
        var tv_Total = findViewById<TextView>(R.id.tv_Total)
        var tv_percent = findViewById<TextView>(R.id.tv_percent)
        var rg = findViewById<RadioGroup>(R.id.rg)
        var rb_no = findViewById<RadioButton>(R.id.rb_no)
        var rb_yes = findViewById<RadioButton>(R.id.rb_yes)
        var tv_DownPayment = findViewById<TextView>(R.id.tv_DownPayment)
        var tv_Loan = findViewById<TextView>(R.id.tv_Loan)
        var sp_Periods = findViewById<Spinner>(R.id.sp_Periods)
        var tv_period = findViewById<TextView>(R.id.tv_period)


        //给下拉框添加监听事件  选择分期时间
        sp_Periods.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                //判断是否分期 ,如果分期 计算
              if (dividel){
                  //首付费用 分期费用各一半
                  tv_DownPayment.setText("￥"+(carBean.price!!.toDouble()*10000/2))
                  //分期费用
                  tv_Loan.setText("￥"+(carBean.price!!.toDouble()*10000/2))
                    //6 个月  12个月  24个月  每期的费用
                  if (i==0){
                      tv_period.setText("￥"+(carBean.price!!.toDouble()*10000/2/6))
                  }else if (i==1){
                      tv_period.setText("￥"+(carBean.price!!.toDouble()*10000/2/12))
                  }else if (i==2){
                      tv_period.setText("￥"+(carBean.price!!.toDouble()*10000/2/24))
                  }
              }

            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        //是否分期切换监听
        rg.setOnCheckedChangeListener { group, checkedId ->
            //分期
            if (checkedId == R.id.rb_yes) {
                dividel = true
                tv_DownPayment.setText("￥"+(carBean.price!!.toDouble()*10000/2))
                tv_Loan.setText("￥"+(carBean.price!!.toDouble()*10000/2))

                val i=sp_Periods.selectedItemPosition  //当前选中期数

                //每期费用
                if (i==0){
                    tv_period.setText("￥"+(carBean.price!!.toDouble()*10000/2/6))
                }else if (i==1){
                    tv_period.setText("￥"+(carBean.price!!.toDouble()*10000/2/12))
                }else if (i==2){
                    tv_Loan.setText("￥"+(carBean.price!!.toDouble()*10000/2/24))
                }
            } else {
                //不分期
                dividel = false
                tv_DownPayment.setText("")
                tv_Loan.setText("")
                //每期费用
                tv_period.setText("")

            }
        }
        /**
         * 监听优惠力度 当优惠力度发生改变的时候 实际总的金额也变化
         */
        seek_DisCount_Percent.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                tv_percent.setText(""+(progress)+"%")
                tv_Total.setText("￥"+(carBean.price!!.toDouble()*10000-carBean.price!!.toDouble()*10000*progress/100))
                tv_DisCount_Account.setText("￥"+(carBean.price!!.toDouble()*10000*progress/100))

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }
}