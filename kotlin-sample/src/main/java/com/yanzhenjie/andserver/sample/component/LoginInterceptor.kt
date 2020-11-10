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
//import com.yanzhenjie.andserver.annotation.Interceptor
//import com.yanzhenjie.andserver.error.BasicException
//import com.yanzhenjie.andserver.framework.HandlerInterceptor
//import com.yanzhenjie.andserver.framework.handler.MethodHandler
//import com.yanzhenjie.andserver.framework.handler.RequestHandler
//import com.yanzhenjie.andserver.framework.mapping.Addition
//import com.yanzhenjie.andserver.http.HttpRequest
//import com.yanzhenjie.andserver.http.HttpResponse
//import org.apache.commons.lang3.ArrayUtils
//
///**
// * Created by Zhenjie Yan on 2018/9/11.
// */
//@Interceptor
//class LoginInterceptor : HandlerInterceptor {
//    override fun onIntercept(request: HttpRequest, response: HttpResponse,
//                             handler: RequestHandler): Boolean {
//        if (handler is MethodHandler) {
//            val addition = handler.addition
//            if (!isLogin(request, addition)) {
//                throw BasicException(401, "You are not logged in yet.")
//            }
//        }
//        return false
//    }
//
//    private fun isNeedLogin(addition: Addition?): Boolean {
//        if (addition == null) {
//            return false
//        }
//        val stringType = addition.stringType
//        if (ArrayUtils.isEmpty(stringType)) {
//            return false
//        }
//        val booleanType = addition.booleanType
//        return if (ArrayUtils.isEmpty(booleanType)) {
//            false
//        } else stringType[0].equals("login", ignoreCase = true) && booleanType[0]
//    }
//
//    private fun isLogin(request: HttpRequest, addition: Addition): Boolean {
//        if (isNeedLogin(addition)) {
//            val session = request.session
//            if (session != null) {
//                val o = session.getAttribute(LOGIN_ATTRIBUTE)
//                return o is Boolean && o
//            }
//            return false
//        }
//        return true
//    }
//
//    companion object {
//        const val LOGIN_ATTRIBUTE = "USER.LOGIN.SIGN"
//    }
//}