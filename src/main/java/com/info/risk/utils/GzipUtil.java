package com.info.risk.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

@Slf4j
public final class GzipUtil {

	// 解压缩
	public static String uncompress(InputStream gzippedResponse) throws IOException {

		InputStream decompressedResponse = new GZIPInputStream(gzippedResponse);
		Reader reader = new InputStreamReader(decompressedResponse, "UTF-8");
		StringWriter writer = new StringWriter();

		char[] buffer = new char[10240];
		for (int length = 0; (length = reader.read(buffer)) > 0;) {
			writer.write(buffer, 0, length);
		}

		writer.close();
		reader.close();
		decompressedResponse.close();
		gzippedResponse.close();

		return writer.toString();
	}

	public static String gzipGet(String url1, String token) {
		String result = null;
		HttpURLConnection connection = null;
		try {
			URL url = new URL(url1);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
			connection.setRequestProperty("Authorization", "token " + token + "");
			connection.connect();
			result = uncompress(connection.getInputStream());
		} catch (Exception e) {
			log.error("gzipGet error:{}",e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return result;
	}
}
