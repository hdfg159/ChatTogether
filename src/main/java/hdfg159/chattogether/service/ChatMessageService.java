package hdfg159.chattogether.service;

import hdfg159.chattogether.domain.dto.MessageJsonObject;
import hdfg159.chattogether.domain.vo.ChatMessageFormVO;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service
 * Created by hdfg159 on 18-3-23 上午10:51.
 */
public interface ChatMessageService {
	MessageJsonObject<?> sendChatMessage(String sendUsername, ChatMessageFormVO form);
}
