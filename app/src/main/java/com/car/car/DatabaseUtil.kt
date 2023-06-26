package com.car.car

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


/**
 * sqlite 数据库操作类
 */
class DatabaseUtil
(

    private val mCtx: Context
) {
    private var mDbHelper: DatabaseHelper? = null
    private var mDb: SQLiteDatabase? = null


    private class DatabaseHelper  constructor(context: Context?) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            //创建表
            db.execSQL(CREATE_TABLE)
        }


        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        }
    }

    /**
     * 打开数据库
     *
     * @return instance of DatabaseUtil
     * @throws SQLException
     */
    @Throws(SQLException::class)
    fun open(): DatabaseUtil {
        mDbHelper = DatabaseHelper(mCtx)
        mDb = mDbHelper!!.writableDatabase
        return this
    }

    /**
     * This method is used for closing the connection.
     */
    fun close() {
        mDbHelper!!.close()
    }

    /**
     * 插入数据
     *
     * @param name
     * @param grade
     * @return long
     */
    fun insert(type: String?, name: String?, price: String?, describe: String?, img:Int): Long {
        val initialValues = ContentValues()
                initialValues.put("type", type);
        initialValues.put("name", name);
        initialValues.put("price", price);
        initialValues.put("describe", describe);
        initialValues.put("img", img);
        return mDb!!.insert(DATABASE_TABLE, null, initialValues)
    }

    /**
     * 查询全部数据
     */
    @SuppressLint("Range")
    fun queryAllPersonData(): List<CarBean>? {

        //查询全部数据
        val cursor: Cursor =
            mDb!!.query(DATABASE_TABLE, null, null, null, null, null, null, null)
        val list: MutableList<CarBean> = ArrayList()
        if (cursor.count > 0) {
            //移动到首位
            cursor.moveToFirst()
            for (i in 0 until cursor.count) {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val type = cursor.getString(cursor.getColumnIndex("type"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val price = cursor.getString(cursor.getColumnIndex("price"))
                val describe = cursor.getString(cursor.getColumnIndex("describe"))
                val pic = cursor.getInt(cursor.getColumnIndex("img"))
                val model = CarBean(id,type,name,price,describe,pic)


                list.add(model)
                //移动到下一位
                cursor.moveToNext()
            }
        }
        cursor.close()
        mDb!!.close()
        return list
    }

    /**
     * This method will deleteAll record.
     *
     * @return
     */
    fun deleteAll(): Boolean {
        return mDb!!.delete(DATABASE_TABLE, " 1 ", null) > 0
    }


    companion object {
        private const val TAG = "DatabaseUtil"

        /**
         * Database Name  数据库名
         */
        private const val DATABASE_NAME = "car_data"

        /**
         * Database Version  数据库版本
         */
        private const val DATABASE_VERSION = 1

        /**
         * Table Name   数据库表名
         */
        private const val DATABASE_TABLE = "tb_car"

        /**
         * Table columns  数据库字段名
         */
        const val KEY_ID = "id"
        const val KEY_TYPE = "type"
        const val KEY_NAME = "name"
        const val KEY_PRICE = "price"
        const val KEY_DESCRIBE = "describe"
        const val KEY_IMG = "img"

        /**
         * Database creation sql statement
         */
        private const val CREATE_TABLE =
            ("create table " + DATABASE_TABLE + " (" + KEY_ID + " integer primary key autoincrement, "
                    + KEY_TYPE + " text not null, "
                    + KEY_NAME + " text not null, "
                    + KEY_PRICE + " text not null, "
                    + KEY_DESCRIBE + " text not null, "
                    + KEY_IMG + " integer not null);")
    }
}