package hdfg159.chattogether.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.domain.vo
 * Created by hdfg159 on 2017-12-4 12:53.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomExceptionVO {
	private String message;
	private String stackTrace;
}
