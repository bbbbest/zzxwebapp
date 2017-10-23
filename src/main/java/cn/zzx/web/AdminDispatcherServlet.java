package cn.zzx.web;

import cn.zzx.web.admin.handler.ErrorHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fzh
 * @since 2017/10/23
 */
public class AdminDispatcherServlet extends DispatcherServlet {
    @Override
    protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ErrorHandler.dealInvalidURL(response);
    }

    @Override
    protected ModelAndView processHandlerException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            ErrorHandler.dealInvalidMethod(response);
            return null;
        } else if (ex instanceof MissingServletRequestParameterException) {
            ErrorHandler.dealInvalidUrlArguments(response);
            return null;
        } else if (ex instanceof HttpMessageNotReadableException || ex instanceof HttpMediaTypeException) {
            ErrorHandler.dealInvalidMediaType(response);
            return null;
        } else {
            return super.processHandlerException(request, response, handler, ex);
        }
    }


}
