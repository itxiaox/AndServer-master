/*
 * Copyright 2018 Zhenjie Yan.
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
package com.yanzhenjie.andserver.sample.controller

import com.yanzhenjie.andserver.annotation.*
import com.yanzhenjie.andserver.http.HttpRequest
import com.yanzhenjie.andserver.http.HttpResponse
import com.yanzhenjie.andserver.http.cookie.Cookie
import com.yanzhenjie.andserver.http.multipart.MultipartFile
//import com.yanzhenjie.andserver.sample.component.LoginInterceptor
import com.yanzhenjie.andserver.sample.model.UserInfo
import com.yanzhenjie.andserver.sample.util.FileUtils
import com.yanzhenjie.andserver.sample.util.Logger
import com.yanzhenjie.andserver.util.MediaType
import java.io.IOException
import java.util.*

/**
 * Created by Zhenjie Yan on 2018/6/9.
 */
@RestController
@RequestMapping(path = ["/user"])
internal class TestController {
    @CrossOrigin(methods = [RequestMethod.POST, RequestMethod.GET])
    @RequestMapping(path = ["/get/{userId}"], method = [RequestMethod.PUT, RequestMethod.POST, RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun info(@PathVariable(name = "userId") userId: String): String {
        return userId
    }

    @PutMapping(path = ["/get/{userId}"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun modify(@PathVariable("userId") userId: String?, @RequestParam(name = "sex") sex: String?,
               @RequestParam(name = "age") age: Int): String {
        val message = "The userId is %1\$s, and the sex is %2\$s, and the age is %3\$d."
        return String.format(Locale.US, message, userId, sex, age)
    }

    @PostMapping(path = ["/login"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun login(request: HttpRequest, response: HttpResponse, @RequestParam(name = "account") account: String,
              @RequestParam(name = "password") password: String): String {
        val session = request.validSession
//        session.setAttribute(LoginInterceptor.LOGIN_ATTRIBUTE, true)
//        val cookie = Cookie("account", "$account=$password")
//        response.addCookie(cookie)
        return "Login successful."
    }

    @Addition(stringType = ["login"], booleanType = [true])
    @GetMapping(path = ["/userInfo"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun userInfo(@CookieValue("account") account: String): UserInfo {
        Logger.i("Account: $account")
        val userInfo = UserInfo()
        userInfo.userId = "123"
        userInfo.userName = "AndServer"
        return userInfo
    }

    @PostMapping(path = ["/upload"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    @Throws(IOException::class)
    fun upload(@RequestParam(name = "avatar") file: MultipartFile): String {
        val localFile = FileUtils.createRandomFile(file)
        file.transferTo(localFile)
        return localFile.absolutePath
    }

    @GetMapping(path = ["/consume"], consumes = ["application/json", "!application/xml"])
    fun consume(): String {
        return "Consume is successful"
    }

    @GetMapping(path = ["/produce"], produces = ["application/json; charset=utf-8"])
    fun produce(): String {
        return "Produce is successful"
    }

    @GetMapping(path = ["/include"], params = ["name=123"])
    fun include(@RequestParam(name = "name") name: String): String {
        return name
    }

    @GetMapping(path = ["/exclude"], params = ["name!=123"])
    fun exclude(): String {
        return "Exclude is successful."
    }

    @GetMapping(path = ["/mustKey", "/getName"], params = ["name"])
    fun getMustKey(@RequestParam(name = "name") name: String): String {
        return name
    }

    @PostMapping(path = ["/mustKey", "/postName"], params = ["name"])
    fun postMustKey(@RequestParam(name = "name") name: String): String {
        return name
    }

    @GetMapping(path = ["/noName"], params = ["!name"])
    fun noName(): String {
        return "NoName is successful."
    }

    @PostMapping(path = ["/formPart"])
    fun forPart(@FormPart(name = "user") userInfo: UserInfo): UserInfo {
        return userInfo
    }

    @PostMapping(path = ["/jsonBody"])
    fun jsonBody(@RequestBody userInfo: UserInfo): UserInfo {
        return userInfo
    }

    @PostMapping(path = ["/listBody"])
    fun jsonBody(@RequestBody infoList: List<UserInfo>): List<UserInfo> {
        return infoList
    }
}