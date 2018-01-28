package hdfg159.chattogether.domain.ao;

import hdfg159.chattogether.domain.MicroWord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.domain.ao
 * Created by hdfg159 on 2018-1-28 19:10.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MicroWordAO {
	private String username;
	private MicroWord microWord;
	private MultipartFile[] pictures;
	private String webRootPath;
}
