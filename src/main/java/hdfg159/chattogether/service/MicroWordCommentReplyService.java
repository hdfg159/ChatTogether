package hdfg159.chattogether.service;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service
 * Created by hdfg159 on 2017-12-5 10:11.
 */
public interface MicroWordCommentReplyService {
	/**
	 * 保存二级回复评论
	 *
	 * @param microwordCommentId
	 * 		所属一级评论
	 * @param replyUserName
	 * 		回复用户的用户名
	 * @param repliedUserId
	 * 		被回复用户ID
	 * @param content
	 * 		回复内容
	 * @param url
	 * 		通知地址
	 *
	 * @return boolean 是否成功
	 */
	boolean save(Long microwordCommentId, String replyUserName, Long repliedUserId, String content, String url);
	
	/**
	 * 删除二级评论
	 *
	 * @param id
	 * 		二级评论Id
	 *
	 * @return boolean 是否成功
	 */
	boolean deleteById(Long id);
}
