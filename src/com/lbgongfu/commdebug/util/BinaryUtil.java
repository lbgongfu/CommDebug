/**
 *
		Copyright (C) 2013 Guangzhou JHComn Technologies Ltd.
 *
 *
		本代码版权归广州佳和立创科技发展有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 *
		任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。

 */
package com.lbgongfu.commdebug.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 进制转换工具
 * 
 * @author lib(Administrator)
 * @date 2013-3-22
 * 
 */
public class BinaryUtil {
	private final static byte[] hex = "0123456789ABCDEF".getBytes();

	/**
	 * 求多个操作数连续异或之后的和
	 * 
	 * @param hex
	 *            十六进制数
	 * @return
	 */
	public static int xorand(int... hex) {
		int result = 0xff;

		return result;
	}

	/**
	 * 十进制转十六进制
	 * 
	 * @param src
	 * @return
	 */
	public static String toHex(int src) {
		String hex = "";
		if (src < 0xa) {
			hex = "0" + Integer.toHexString(src);
		} else if (src >= 0xa && src <= 0xf) {
			hex = "0" + Integer.toHexString(src).toUpperCase();
		} else if (src >= 0xa0 && src <= 0xff) {
			hex = Integer.toHexString(src).toUpperCase();
		} else {
			hex = Integer.toHexString(src);
		}
		return hex;
	}

	/**
	 * 从流里面读取字节并转换成十六进制的字符串表示形式
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String readToHex(InputStream in) throws IOException {
		StringBuffer buf = new StringBuffer();
		int c = 0;
		while (c != -1) {
			c = in.read();
			if (c == -1) {
				break;
			}
			if ('\r' == (char) c) {
				buf.append("\n");
			} else {
				buf.append(toHex(c));
			}
		}
		return buf.toString();
	}

	/**
	 * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" –> byte[]{0x2B, 0×44, 0xEF,
	 * 0xD9}
	 * 
	 * @param src
	 *            String
	 * @return byte[]
	 */
	public static byte[] HexString2Bytes(String src) {
		byte[] ret = new byte[src.length() / 2];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < tmp.length / 2; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}
        
	/**
	 * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
	 * 
	 * @param src0
	 *            byte
	 * @param src1
	 *            byte
	 * @return byte
	 */
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	/**
	 * 从输入流里面读数据到数组
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] readToByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int b = 0;
		b = in.read();
		while (b != -1) {
			out.write(b);
			b = in.read();
		}
		byte[] result = out.toByteArray();
		out.close();
		return result;
	}

	public static String Bytes2HexString(byte[] b) {
		byte[] buff = new byte[2 * b.length];
		for (int i = 0; i < b.length; i++) {
			buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
			buff[2 * i + 1] = hex[b[i] & 0x0f];
		}
		return new String(buff);
	}
}
