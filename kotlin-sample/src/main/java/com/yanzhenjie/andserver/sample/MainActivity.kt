/*
 * Copyright © 2016 Zhenjie Yan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yanzhenjie.andserver.sample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.yanzhenjie.loading.dialog.LoadingDialog
import java.util.*

/**
 * Created by Zhenjie Yan on 2018/6/9.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var mServerManager: ServerManager? = null
    private var mBtnStart: Button? = null
    private var mBtnStop: Button? = null
    private var mBtnBrowser: Button? = null
    private var mTvMessage: TextView? = null
    private var mDialog: LoadingDialog? = null
    private var mRootUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        mBtnStart = findViewById(R.id.btn_start)
        mBtnStop = findViewById(R.id.btn_stop)
        mBtnBrowser = findViewById(R.id.btn_browse)
        mTvMessage = findViewById(R.id.tv_message)
        mBtnStart!!.setOnClickListener(this)
        mBtnStop!!.setOnClickListener(this)
        mBtnBrowser!!.setOnClickListener(this)

        // AndServer run in the service.
        mServerManager = ServerManager(this)
        mServerManager!!.register()

        // startServer;
        mBtnStart!!.performClick()
    }

    override fun onDestroy() {
        super.onDestroy()
        mServerManager!!.unRegister()
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.btn_start -> {
                showDialog()
                mServerManager!!.startServer()
            }
            R.id.btn_stop -> {
                showDialog()
                mServerManager!!.stopServer()
            }
            R.id.btn_browse -> {
                if (!TextUtils.isEmpty(mRootUrl)) {
                    val intent = Intent()
                    intent.action = "android.intent.action.VIEW"
                    intent.data = Uri.parse(mRootUrl)
                    startActivity(intent)
                }
            }
        }
    }

    /**
     * Start notify.
     */
    fun onServerStart(ip: String) {
        closeDialog()
        mBtnStart!!.visibility = View.GONE
        mBtnStop!!.visibility = View.VISIBLE
        mBtnBrowser!!.visibility = View.VISIBLE
        if (!TextUtils.isEmpty(ip)) {
            val addressList: MutableList<String?> = LinkedList()
            mRootUrl = "http://$ip:8080/"
            addressList.add(mRootUrl)
            addressList.add("http://$ip:8080/login.html")
            mTvMessage!!.text = TextUtils.join("\n", addressList)
        } else {
            mRootUrl = null
            mTvMessage!!.setText(R.string.server_ip_error)
        }
    }

    /**
     * Error notify.
     */
    fun onServerError(message: String?) {
        closeDialog()
        mRootUrl = null
        mBtnStart!!.visibility = View.VISIBLE
        mBtnStop!!.visibility = View.GONE
        mBtnBrowser!!.visibility = View.GONE
        mTvMessage!!.text = message
    }

    /**
     * Stop notify.
     */
    fun onServerStop() {
        closeDialog()
        mRootUrl = null
        mBtnStart!!.visibility = View.VISIBLE
        mBtnStop!!.visibility = View.GONE
        mBtnBrowser!!.visibility = View.GONE
        mTvMessage!!.setText(R.string.server_stop_succeed)
    }

    private fun showDialog() {
        if (mDialog == null) {
            mDialog = LoadingDialog(this)
        }
        if (!mDialog!!.isShowing) {
            mDialog!!.show()
        }
    }

    private fun closeDialog() {
        if (mDialog != null && mDialog!!.isShowing) {
            mDialog!!.dismiss()
        }
    }
}