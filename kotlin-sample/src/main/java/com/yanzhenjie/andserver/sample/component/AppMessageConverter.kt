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
//import com.yanzhenjie.andserver.annotation.Converter
//import com.yanzhenjie.andserver.framework.MessageConverter
//import com.yanzhenjie.andserver.framework.body.JsonBody
//import com.yanzhenjie.andserver.http.ResponseBody
//import com.yanzhenjie.andserver.sample.util.JsonUtils
//import com.yanzhenjie.andserver.util.IOUtils
//import com.yanzhenjie.andserver.util.MediaType
//import java.io.IOException
//import java.io.InputStream
//import java.lang.reflect.Type
//
///**
// * Created by Zhenjie Yan on 2018/9/11.
// */
//@Converter
//class AppMessageConverter : MessageConverter {
//    override fun convert(output: Any?, mediaType: MediaType?): ResponseBody {
//        return JsonBody(JsonUtils.successfulJson(output))
//    }
//
//    @Throws(IOException::class)
//    override fun <T> convert(stream: InputStream, mediaType: MediaType?, type: Type): T? {
//        val charset = mediaType?.charset
//                ?: return JsonUtils.parseJson(IOUtils.toString(stream), type)
//        return JsonUtils.parseJson(IOUtils.toString(stream, charset), type)
//    }
//}