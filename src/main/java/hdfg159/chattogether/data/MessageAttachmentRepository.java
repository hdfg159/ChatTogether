package hdfg159.chattogether.data;

import hdfg159.chattogether.domain.MessageAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 2017-11-28 11:41.
 */
public interface MessageAttachmentRepository extends JpaRepository<MessageAttachment, Long> {
}
