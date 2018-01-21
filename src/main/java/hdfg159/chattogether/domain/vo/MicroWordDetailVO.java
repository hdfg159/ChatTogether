package hdfg159.chattogether.domain.vo;

import hdfg159.chattogether.domain.MicroWord;
import hdfg159.chattogether.domain.MicroWordComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.domain.vo
 * Created by hdfg159 on 2017-12-4 16:19.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MicroWordDetailVO {
	private MicroWord microWord;
	private Page<MicroWordComment> microWordCommentPage;
}
