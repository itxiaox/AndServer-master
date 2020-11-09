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
package com.yanzhenjie.andserver.error;

/**
 * Created by Zhenjie Yan on 2018/7/19.
 */
public class BasicException extends RuntimeException {

    /**
     * Status code.
     */
    private int mStatusCode;

    public BasicException(int statusCode, String message) {
        super(message);
        this.mStatusCode = statusCode;
    }

    public BasicException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.mStatusCode = statusCode;
    }

    public BasicException(int statusCode, Throwable cause) {
        super(cause);
        this.mStatusCode = statusCode;
    }

    public int getStatusCode() {
        return mStatusCode;
    }
}