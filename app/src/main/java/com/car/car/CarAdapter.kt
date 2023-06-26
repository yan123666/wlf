package com.car.car

import android.content.Context
import android.view.View
import android.widget.BaseAdapter
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList

/**
 * 车辆列表适配器
 */
class CarAdapter(context: Context, mData: MutableList<CarBean>) : BaseAdapter() {
    private var data = mData
    private var  mContext=context;
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val   convertView = View.inflate(mContext, R.layout.item_layout_wlf, null)
        var car = this.data[position]

        /**
         * 初始化控件
         */
        val  img=convertView.findViewById<ImageView>(R.id.iv_img)  //车辆图片
        val  tv_title=convertView.findViewById<TextView>(R.id.tv_title) //车辆名
        val  tv_mileage=convertView.findViewById<TextView>(R.id.tv_mileage) //公里数
        val  tv_price=convertView.findViewById<TextView>(R.id.tv_price) //车辆价格
        //车辆图片
        img.setImageResource(car.img)
        //车辆名字
        tv_title.text = car.name
        //车辆公里数
        tv_mileage.text = car.describe;
        //车辆价格
        tv_price.text = car.price+"万";

        return convertView
    }
}