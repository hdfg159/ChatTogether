package hdfg159.chattogether.service.impl;

import hdfg159.chattogether.data.MicroWordAttachmentRepository;
import hdfg159.chattogether.data.MicroWordCommentRepository;
import hdfg159.chattogether.data.MicroWordRepository;
import hdfg159.chattogether.data.UserRepository;
import hdfg159.chattogether.domain.MicroWord;
import hdfg159.chattogether.domain.MicroWordAttachment;
import hdfg159.chattogether.domain.MicroWordComment;
import hdfg159.chattogether.domain.User;
import hdfg159.chattogether.domain.ao.MicroWordAO;
import hdfg159.chattogether.domain.vo.MicroWordDetailVO;
import hdfg159.chattogether.exception.MicroWordNotFoundException;
import hdfg159.chattogether.exception.SaveProfilePhotoException;
import hdfg159.chattogether.exception.UserNotFoundException;
import hdfg159.chattogether.service.MicroWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import static hdfg159.chattogether.util.UUIDUtils.uuid;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service.impl
 * Created by hdfg159 on 2017-11-29 22:52.
 */
@Slf4j
@Service
@Transactional
@PropertySource(value = {"classpath:appconfig.properties"})
public class MicroWordServiceImpl implements MicroWordService {
	private static final String IMAGES_PICTURE_DEFAULT_PATH = "images/picture/";
	private final MicroWordRepository microWordRepository;
	private final UserRepository userRepository;
	private final MicroWordCommentRepository microWordCommentRepository;
	private final MicroWordAttachmentRepository microWordAttachmentRepository;
	@Value("${microWord.picture.path}")
	private String savePicturePath;
	
	@Autowired
	public MicroWordServiceImpl(MicroWordRepository microWordRepository, UserRepository userRepository, MicroWordCommentRepository microWordCommentRepository, MicroWordAttachmentRepository microWordAttachmentRepository) {
		this.microWordRepository = microWordRepository;
		this.userRepository = userRepository;
		this.microWordCommentRepository = microWordCommentRepository;
		this.microWordAttachmentRepository = microWordAttachmentRepository;
	}
	
	@Override
	public Page<MicroWord> findAll(Pageable pageable) {
		return microWordRepository.findAll(pageable);
	}
	
	@Override
	public boolean deleteById(Long id) {
		microWordRepository.delete(id);
		return true;
	}
	
	@Override
	public boolean deleteBatch(Long... ids) {
		for (Long id : ids) {
			microWordRepository.delete(id);
		}
		return true;
	}
	
	@Override
	public boolean save(MicroWordAO microWordAO) {
		User user = userRepository.findByUsernameIgnoreCase(microWordAO.getUsername())
				.orElseThrow(() -> new UserNotFoundException("For Username:" + microWordAO.getUsername()));
		MicroWord saveMicroWord = MicroWord.builder()
				.user(user)
				.content(microWordAO.getMicroWord().getContent())
				.createTime(new Date())
				.modifiedTime(new Date())
				.build();
		MicroWord microWordSave = microWordRepository.save(saveMicroWord);
		
		saveMicroWordAttachment(microWordAO, microWordSave);
		return true;
	}
	
	private void saveMicroWordAttachment(MicroWordAO microWordAO, MicroWord microWordSave) {
		MultipartFile[] pictures = microWordAO.getPictures();
		String picturePath = isEmpty(savePicturePath) ? IMAGES_PICTURE_DEFAULT_PATH : savePicturePath;
		for (MultipartFile file : pictures) {
			if (!file.isEmpty()) {
				String saveFilePath = picturePath + microWordSave.getUser().getId() + "/" + getFilename(file);
				File dest = new File(microWordAO.getWebRootPath() + File.separator + saveFilePath);
				if (!dest.getParentFile().exists() && !dest.getParentFile().mkdirs()) {
					log.error("创建图片保存路径出现错误:{}", dest.getParentFile().getAbsolutePath());
					throw new SaveProfilePhotoException("保存图片失败!");
				}
				try {
					file.transferTo(dest);
				} catch (IOException e) {
					log.error("保存图片失败出现IO错误:{}", getMessage(e));
					throw new SaveProfilePhotoException("保存图片失败!");
				}
				
				Date now = new Date();
				MicroWordAttachment microWordAttachment = MicroWordAttachment.builder()
						.url(saveFilePath)
						.microWord(microWordSave)
						.createTime(now)
						.modifiedTime(now)
						.build();
				microWordAttachmentRepository.save(microWordAttachment);
			}
		}
	}
	
	private String getFilename(MultipartFile profilePhoto) {
		return uuid() + "." + substringAfter(profilePhoto.getContentType(), "/");
	}
	
	@Override
	public Page<MicroWord> findAllByContentContaining(String content, Pageable pageable) {
		return microWordRepository.findAllByContentContaining(content, pageable);
	}
	
	@Override
	public MicroWordDetailVO findMicroWordDetailById(Long id, Pageable pageable) {
		Optional<MicroWord> microWordOptional = Optional.ofNullable(microWordRepository.findOne(id));
		MicroWord microWord = microWordOptional.orElseThrow(() -> new MicroWordNotFoundException("For Id:" + id));
		Page<MicroWordComment> microWordComments = microWordCommentRepository.findAllByMicroWord(microWord, pageable);
		return new MicroWordDetailVO(microWord, microWordComments);
	}
	
	@Override
	public Page<MicroWord> findAllByUsername(String username, Pageable pageable) {
		return microWordRepository.findAllByUser_Username(username, pageable);
	}
	
	@Override
	public Page<MicroWord> findAllByUserFriend(String username, Pageable pageable) {
		return microWordRepository.findAllByUserFriend(username, pageable);
	}
}
