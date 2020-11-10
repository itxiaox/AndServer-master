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

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.yanzhenjie.andserver.AndServer
import com.yanzhenjie.andserver.Server
import com.yanzhenjie.andserver.Server.ServerListener
import com.yanzhenjie.andserver.sample.util.NetUtils
import java.util.concurrent.TimeUnit

/**
 * Created by Zhenjie Yan on 2018/6/9.
 */
class CoreService : Service() {
    private var mServer: Server? = null
    override fun onCreate() {
        mServer = AndServer.webServer(this)
                .port(8080)
                .timeout(10, TimeUnit.SECONDS)
                .listener(object : ServerListener {
                    override fun onStarted() {
                        val address = NetUtils.getLocalIPAddress()
                        ServerManager.onServerStart(this@CoreService, address.hostAddress)
                    }

                    override fun onStopped() {
                        ServerManager.onServerStop(this@CoreService)
                    }

                    override fun onException(e: Exception) {
                        ServerManager.onServerError(this@CoreService, e.message)
                    }
                })
                .build()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        startServer()
        return START_STICKY
    }

    override fun onDestroy() {
        stopServer()
        super.onDestroy()
    }

    /**
     * Start server.
     */
    private fun startServer() {
        mServer!!.startup()
    }

    /**
     * Stop server.
     */
    private fun stopServer() {
        mServer!!.shutdown()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}