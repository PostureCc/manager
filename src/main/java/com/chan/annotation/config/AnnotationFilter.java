package com.chan.annotation.config;

import com.chan.common.Token;
import com.chan.dao.StudentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 声明自定义SpringMVC拦截器
 */
public class AnnotationFilter implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass().toString());

    @Autowired
    @Qualifier("studentDao")
    public StudentDao studentDao;

    /**
     * 该方法将在请求处理之前进行调用，只有该方法返回true，才会继续执行后续的Interceptor和Controller，当返回值为true 时就会继续调用下一个Interceptor的preHandle 方法，如果已经是最后一个Interceptor的时候就会是调用当前请求的Controller方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("WrongCodeInterceptor, preHandle......");
        if (handler instanceof HandlerMethod) {
            String token = request.getHeader(Token.TOKEN);
            logger.info("token:{}", token);
//            Map<String, Object> params = new HashMap<>(3);
//            List<Map<String, Object>> userList = studentDao.search(params);
//            if (CollectionUtils.isEmpty(userList)) {
//                for (Map<String, Object> map : userList) {
//
//                }
//            }
        }
        return true;
    }

    /**
     * 该方法将在请求处理之后，DispatcherServlet进行视图返回渲染之前进行调用，可以在这个方法中对Controller 处理之后的ModelAndView 对象进行操作。
     */
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        logger.info("WrongCodeInterceptor, postHandle......");
    }

    /**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行，该方法将在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。用于进行资源清理。
     */
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger.info("WrongCodeInterceptor, afterCompletion......");
    }

}
