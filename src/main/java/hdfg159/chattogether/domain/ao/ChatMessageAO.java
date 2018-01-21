package hdfg159.chattogether.domain.ao;

import hdfg159.chattogether.domain.vo.ChatMessageFormVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.domain.ao
 * Created by hdfg159 on 2018-1-18 18:26.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageAO {
	String sendUsername;
	String receiveUsername;
	String uri;
	ChatMessageFormVO chatMessageFormVO;
}
