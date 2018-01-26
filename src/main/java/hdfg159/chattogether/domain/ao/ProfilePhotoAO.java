package hdfg159.chattogether.domain.ao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.domain.ao
 * Created by hdfg159 on 2018-1-26 13:43.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfilePhotoAO {
	private String username;
	private MultipartFile profilePhoto;
	private String webRootPath;
}
