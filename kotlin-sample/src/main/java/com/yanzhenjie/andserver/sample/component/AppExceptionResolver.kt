///*
// * Copyright 2018 Zhenjie Yan.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.yanzhenjie.andserver.sample.component
//
//import com.yanzhenjie.andserver.annotation.Resolver
//import com.yanzhenjie.andserver.error.BasicException
//import com.yanzhenjie.andserver.framework.ExceptionResolver
//import com.yanzhenjie.andserver.framework.body.JsonBody
//import com.yanzhenjie.andserver.http.HttpRequest
//import com.yanzhenjie.andserver.http.HttpResponse
//import com.yanzhenjie.andserver.http.StatusCode
//import com.yanzhenjie.andserver.sample.util.JsonUtils
//
///**
// * Created by Zhenjie Yan on 2018/9/11.
// */
//@Resolver
//class AppExceptionResolver : ExceptionResolver {
//    override fun onResolve(request: HttpRequest, response: HttpResponse, e: Throwable) {
//        e.printStackTrace()
//        if (e is BasicException) {
//            response.status = e.statusCode
//        } else {
//            response.status = StatusCode.SC_INTERNAL_SERVER_ERROR
//        }
//        val body = JsonUtils.failedJson(response.status, e.message)
//        response.setBody(JsonBody(body))
//    }
//}