
package com.chan.util;

import com.chan.exception.ErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

public class BaseService {

    private static final Logger LOG = LoggerFactory.getLogger(BaseService.class);


    private <T> ResultBean<T> handleResultBean(ResultBean<T> resultBean) {
        resultBean.setMsg(resultBean.getMsgCode());
        return resultBean;
    }

    private <T> ResultBean<T> handleResultBean(ResultBean<T> resultBean, Object value) {
        if (value instanceof String){
            resultBean.setMsg(String.format(resultBean.getMsgCode(), value));
            return resultBean;
        }else {
            return handleResultBean(resultBean);
        }
    }

    protected ResultBean errorResult(ErrorCodes error) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("errorResult>>>>errorCode:{},msg:{}", error.getCode(), error.getMsg());
        }
        return handleResultBean(new ResultBean(error.getCode(), error.getMsg()));
    }

    protected ResultBean errorResult(ErrorCodes error, Object value) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("errorResult>>>>errorCode:{},msg:{}", error.getCode(), error.getMsg());
        }
        ResultBean<Map<String, Object>> resultBean = new ResultBean<Map<String, Object>>(error.getCode(), error.getMsg());
        resultBean.putSimpleValue(value);
        return handleResultBean(resultBean, value);
    }

    protected <T> ResultBean<T> successResult(T data) {
        return handleResultBean(new ResultBean<T>(ErrorCodes.SUCCESS, data));
    }

    protected <T> ResultBean<T> successResult(T data, boolean isEnd) {
        return handleResultBean(new ResultBean<T>(ErrorCodes.SUCCESS, data, isEnd));
    }

    protected ResultBean<Map<String, Object>> successSingleResult(Object value) {
        ResultBean<Map<String, Object>> resultBean = new ResultBean<Map<String, Object>>(ErrorCodes.SUCCESS);
        resultBean.putSimpleValue(value);
        return handleResultBean(resultBean);
    }

}
