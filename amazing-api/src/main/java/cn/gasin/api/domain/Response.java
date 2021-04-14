package cn.gasin.api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 注册响应
 */
@Getter
@Setter
@Builder
public class Response {
    ResponseStatus status;
    String message;
}
