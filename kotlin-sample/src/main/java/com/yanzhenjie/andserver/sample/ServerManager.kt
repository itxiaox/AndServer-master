/*
 * Copyright © 2018 Zhenjie Yan.
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

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

/**
 * Created by Zhenjie Yan on 2018/6/9.
 */
class ServerManager(private val mActivity: MainActivity) : BroadcastReceiver() {
    private val mService: Intent

    /**
     * Register broadcast.
     */
    fun register() {
        val filter = IntentFilter(ACTION)
        mActivity.registerReceiver(this, filter)
    }

    /**
     * UnRegister broadcast.
     */
    fun unRegister() {
        mActivity.unregisterReceiver(this)
    }

    fun startServer() {
        mActivity.startService(mService)
    }

    fun stopServer() {
        mActivity.stopService(mService)
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (ACTION == action) {
            val cmd = intent.getIntExtra(CMD_KEY, 0)
            when (cmd) {
                CMD_VALUE_START -> {
                    val ip = intent.getStringExtra(MESSAGE_KEY)
                    mActivity.onServerStart(ip)
                }
                CMD_VALUE_ERROR -> {
                    val error = intent.getStringExtra(MESSAGE_KEY)
                    mActivity.onServerError(error)
                }
                CMD_VALUE_STOP -> {
                    mActivity.onServerStop()
                }
            }
        }
    }

    companion object {
        private const val ACTION = "com.yanzhenjie.andserver.receiver"
        private const val CMD_KEY = "CMD_KEY"
        private const val MESSAGE_KEY = "MESSAGE_KEY"
        private const val CMD_VALUE_START = 1
        private const val CMD_VALUE_ERROR = 2
        private const val CMD_VALUE_STOP = 4

        /**
         * Notify serverStart.
         *
         * @param context context.
         */
        fun onServerStart(context: Context, hostAddress: String?) {
            sendBroadcast(context, CMD_VALUE_START, hostAddress)
        }

        /**
         * Notify serverStop.
         *
         * @param context context.
         */
        fun onServerError(context: Context, error: String?) {
            sendBroadcast(context, CMD_VALUE_ERROR, error)
        }

        /**
         * Notify serverStop.
         *
         * @param context context.
         */
        fun onServerStop(context: Context) {
            sendBroadcast(context, CMD_VALUE_STOP)
        }

        private fun sendBroadcast(context: Context, cmd: Int, message: String? = null) {
            val broadcast = Intent(ACTION)
            broadcast.putExtra(CMD_KEY, cmd)
            broadcast.putExtra(MESSAGE_KEY, message)
            context.sendBroadcast(broadcast)
        }
    }

    init {
        mService = Intent(mActivity, CoreService::class.java)
    }
}