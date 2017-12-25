package com.igustudio.tmanager.model;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Reads lines from "backwards" InputStream. This class facilitates reading
 * files from bottom up.
 * 
 * This source code was kindly contributed by Kan Ogawa.
 * 
 */
public class BackwardsLineReader {

	private BufferedInputStream bis;
	private boolean skipLF = true;
	private String encoding;

	public BackwardsLineReader(InputStream is) {
		this(is, null);
	}

	public BackwardsLineReader(InputStream is, String encoding) {
		this.bis = new BufferedInputStream(is, 8192);
		this.encoding = encoding;
	}

	public String readLine() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
		boolean empty = false;
		while (true) {
			byte b = (byte) bis.read();
			if (b != -1) {
				if (b == '\n') {
					skipLF = false;
					// quit this loop
					break;
				}
				if (b == '\r') {
					if (skipLF) {
						// quit this loop. if the carriage return only was read
						break;
					} else {
						// go to next loop, if both the carriage return and
						// the line feed were read
						continue;
					}
				}
				baos.write(b);
			} else {
				// quit this loop, if the first of the backwards stream is
				// reached
				if (baos.toByteArray().length == 0) {
					empty = true;
				}
				break;
			}
		}
		if (!empty) {
			byte[] byteArray = baos.toByteArray();
			reverse(byteArray);
			return encoding == null ? new String(byteArray) : new String(
					byteArray, encoding);
		} else {
			// return null if the end of the stream has been reached
			return null;
		}
	}

	public void close() throws IOException {
		if (bis != null) {
			bis.close();
		}
	}

	private void reverse(byte[] byteArray) {
		for (int i = 0; i < byteArray.length / 2; i++) {
			byte temp = byteArray[i];
			byteArray[i] = byteArray[byteArray.length - i - 1];
			byteArray[byteArray.length - i - 1] = temp;
		}
	}
}
