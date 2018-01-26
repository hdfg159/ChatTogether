package hdfg159.chattogether.util;

import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.remove;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.util
 * Created by hdfg159 on 2018-1-26 13:19.
 */
public class UUIDUtils {
	public static String uuid() {
		return remove(UUID.randomUUID().toString(), "-");
	}
}
