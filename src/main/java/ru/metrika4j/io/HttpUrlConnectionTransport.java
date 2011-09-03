/*
 * Copyright (C) 2011 Artur Suilin
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.metrika4j.io;

import ru.metrika4j.error.AuthException;
import ru.metrika4j.error.TransportException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/** Реализация транспорта на базе класса HttpURLConnection, входящего в JDK. */
public class HttpUrlConnectionTransport implements HttpTransport {
    private final String oAuthToken;

    public HttpUrlConnectionTransport(String oAuthToken) {
        this.oAuthToken = oAuthToken;
    }

    public String doRequest(String url, HttpMethod method, String content) {
        try {
            URL address = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) address.openConnection();
            conn.setRequestProperty("Authorization", "OAuth " + oAuthToken);
            conn.setRequestProperty("User-Agent", "JMetrika/1.0");
            conn.setRequestProperty("Accept", "application/json");
            conn.setAllowUserInteraction(false);
            conn.setRequestMethod(method.name());

            StringBuilder result = new StringBuilder();
            try {
                if (content != null) {
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/json");
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                    try {
                        writer.write(content);
                    } finally {
                        writer.close();
                    }
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"), 8192);
                try {
                    char[] buffer = new char[8192];
                    int read;
                    while ((read = reader.read(buffer)) >= 0) {
                        result.append(buffer, 0, read);
                    }
                } finally {
                    reader.close();
                }
            } catch (IOException e) {
                if (conn.getResponseCode() == 401) {
                    throw new AuthException();
                } else {
                    throw new TransportException(e);
                }
            }
            return result.toString();
        } catch (IOException e) {
            throw new TransportException(e);
        }

    }
}
