package hdfg159.chattogether.service;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.service
 * Created by hdfg159 on 18-3-13 下午7:55.
 */
public interface MicroWordCommentAgreeService {
	boolean save(String username, Long microWordCommentId, String uri);
}
