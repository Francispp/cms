package com.cyberway.weixin.encrypt;

import java.util.ArrayList;
/**
 * 
 * @com.cyberway.weixin.encrypt.ByteGroup
 * TODO :
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年2月10日 上午11:42:06
 */
class ByteGroup {
	ArrayList<Byte> byteContainer = new ArrayList<Byte>();

	public byte[] toBytes() {
		byte[] bytes = new byte[byteContainer.size()];
		for (int i = 0; i < byteContainer.size(); i++) {
			bytes[i] = byteContainer.get(i);
		}
		return bytes;
	}

	public ByteGroup addBytes(byte[] bytes) {
		for (byte b : bytes) {
			byteContainer.add(b);
		}
		return this;
	}

	public int size() {
		return byteContainer.size();
	}
}
