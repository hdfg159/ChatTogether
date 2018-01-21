package hdfg159.chattogether.service;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service
 * Created by hdfg159 on 2017-12-4 17:40.
 */
public interface MicroWordCommentService {
	/**
	 * 根据id删除一级评论
	 *
	 * @param id
	 * 		一级评论id
	 *
	 * @return boolean 是否成功
	 */
	boolean deleteById(Long id);
	
	/**
	 * 根据用户名、微说ID和通知地址保存一级评论
	 *
	 * @param username
	 * 		用户名
	 * @param microWordId
	 * 		微说ID
	 * @param content
	 * 		一级评论内容
	 * @param uri
	 * 		通知url
	 *
	 * @return boolean 是否成功
	 */
	boolean save(String username, Long microWordId, String content, String uri);
}
