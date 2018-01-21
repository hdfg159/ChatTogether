package hdfg159.chattogether.util;

import hdfg159.chattogether.domain.dto.BaseJsonObject;

import static hdfg159.chattogether.domain.dto.BaseJsonObject.STR_SUCCESS;
import static hdfg159.chattogether.domain.dto.BaseJsonObject.SUCCESS;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.util
 * Created by hdfg159 on 2017-12-4 11:27.
 */
public class ResponseUtils {
	private ResponseUtils() {}
	
	/**
	 * 成功时返回的JSON数据
	 *
	 * @return {@code BaseJsonObject<String>}
	 */
	public static BaseJsonObject<String> responseSuccess() {
		return BaseJsonObject.<String>builder()
				.code(SUCCESS)
				.message(STR_SUCCESS)
				.build();
	}
	
	/**
	 * 成功时返回的JSON数据
	 *
	 * @param data
	 * 		数据
	 * @param <T>
	 * 		类型
	 *
	 * @return {@code BaseJsonObject<?>}
	 */
	public static <T> BaseJsonObject<T> responseDataSuccess(T data) {
		return BaseJsonObject.<T>builder()
				.code(SUCCESS)
				.message(STR_SUCCESS)
				.data(data)
				.build();
	}
	
	/**
	 * 失败时返回的JSON数据
	 *
	 * @param data
	 * 		数据
	 * @param <T>
	 * 		类型
	 *
	 * @return {@code BaseJsonObject<?>}
	 */
	public static <T> BaseJsonObject<T> responseFail(T data) {
		return BaseJsonObject.<T>builder()
				.code(BaseJsonObject.FAIL)
				.message(BaseJsonObject.STR_FAIL)
				.data(data)
				.build();
	}
}
