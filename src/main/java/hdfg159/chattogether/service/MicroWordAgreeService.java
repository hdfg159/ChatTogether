package hdfg159.chattogether.service;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service
 * Created by hdfg159 on 2017-12-3 11:15.
 */
public interface MicroWordAgreeService {
	/**
	 * 保存用户点赞信息
	 *
	 * @param username
	 * 		用户名
	 * @param microWordId
	 * 		微说ID
	 * @param uri
	 * 		地址
	 *
	 * @return boolean 是否成功
	 */
	boolean save(String username, Long microWordId, String uri);
}
