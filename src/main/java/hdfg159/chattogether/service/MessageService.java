package hdfg159.chattogether.service;

import hdfg159.chattogether.domain.Message;
import hdfg159.chattogether.domain.ao.ChatMessageAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service
 * Created by hdfg159 on 2018-1-18 15:26.
 */
public interface MessageService {
	/**
	 * 分页查找量用户之间的所有信息
	 *
	 * @param sendUsername
	 * 		发送信息者用户名
	 * @param receiveUsername
	 * 		接收信息者用户名
	 * @param pageable
	 * 		分页参数
	 *
	 * @return {@code Page<Message>}
	 */
	Page<Message> findAllByUsers(String sendUsername, String receiveUsername, Pageable pageable);
	
	/**
	 * 保存发送的信息
	 *
	 * @param chatMessageAO
	 * 		用户发送信息
	 *
	 * @return Message 已经保存信息的内容
	 */
	Message save(ChatMessageAO chatMessageAO);
}
