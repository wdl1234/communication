package com.xt.communication.sse.param;

import lombok.Data;

/**
 * @author dlwu4
 * @version V1.0
 * @className SendMegRequestParam
 * @description
 * @date 2024/08/23 10:53
 **/
@Data
public class SendMegRequestParam {

    /**
     * 发送者id
     */
    private String userId;

    /**
     * 发送消息
     */
    private String msg;
}
