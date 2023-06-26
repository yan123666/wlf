package com.car.car

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

/**
 * 车辆列表界面
 */
class MainActivity : AppCompatActivity() {
    private  lateinit var listView: ListView
    private var carAdapter: CarAdapter?=null
    private  var data= mutableListOf<CarBean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_wlf)
        listView = findViewById<ListView>(R.id.lv)

        initData()
        //跳转到详情界面
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                //传递车辆信息
                val intent = Intent(this, DetailsActivity::class.java);
                intent.putExtra("car", data[i])
                startActivity(intent)
            }

    }

    /**
     * 初始化数据
     */
    private fun initData() {
        //数据库开启
        val dbUtil=DatabaseUtil(this);
        dbUtil.open()
        //查询数据库里是否存在数据
        var queryAllPersonData = dbUtil.queryAllPersonData()

        //存在数据
        if (null!=queryAllPersonData&&queryAllPersonData.size>0){
            data= queryAllPersonData as MutableList<CarBean>
            //初始化是脾气
            carAdapter = CarAdapter(this,data)
            //设置适配器
            listView.adapter=carAdapter
        }else{
            //不存在 插入3个默认数据
            dbUtil.open()
            //插入数据
            dbUtil.insert("轿车","小鹏汽车P7 2022款","18.5","2023年(700公里)",R.mipmap.img1);
            dbUtil.insert("轿车","保时捷 2021款","158.5","2023年(2700公里)",R.mipmap.img2);
            dbUtil.insert("轿车","别克E5 2023款","20.5","2023年(500公里)",R.mipmap.img3);
            //再次查询数据库  确认查询成功  设置适配器现实数据
            var queryAllPersonData1 = dbUtil.queryAllPersonData()
            data= queryAllPersonData1 as MutableList<CarBean>
            carAdapter = CarAdapter(this,data)
            listView.adapter=carAdapter
        }

    }
}