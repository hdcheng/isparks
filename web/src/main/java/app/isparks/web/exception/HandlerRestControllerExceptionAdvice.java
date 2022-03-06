package app.isparks.web.exception;

import app.isparks.core.exception.AuthException;
import app.isparks.core.exception.ISparksException;
import app.isparks.core.exception.NoFoundException;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * 统一处理异常
 *
 * @author chenghd
 * @date 2020/7/31
 */
@RestControllerAdvice
public class HandlerRestControllerExceptionAdvice {

    private Logger log = LoggerFactory.getLogger(HandlerRestControllerExceptionAdvice.class);

    /**
     * 其他异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result handler(Exception e) {
        log.error("捕获未知异常", e);
        return ResultUtils.error("未知异常：" + e.getMessage());
    }


    /**
     * 系统
     */
    @ExceptionHandler(value = ISparksException.class)
    public Result fenceHandler(ISparksException e) {
        log.error("FENCE系统异常");
        return ResultUtils.error("Fence Exception:" + e.getMessage());
    }

    /**
     * 权限异常
     */
    @ExceptionHandler(value = AuthException.class)
    public Result fenceAuthException(AuthException e) {
        return ResultUtils.error(e.getSubject() + ":" + e.getMsg());
    }

    /**
     * 数据未找到
     */
    @ExceptionHandler(value = {NoFoundException.class, NoSuchElementException.class})
    public Result fenceNoFoundException(Exception e) {
        if (e instanceof NoFoundException) {
            NoFoundException nfe = (NoFoundException) e;
            return ResultUtils.fail(nfe.getMsg());
        } else if (e instanceof NoSuchElementException) {
            NoSuchElementException nse = (NoSuchElementException) e;
            return ResultUtils.fail("不存在");
        }
        return ResultUtils.fail("error" + e.getMessage());
    }

    /**
     * 元素验证异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result argumentValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("传参异常", e);
        BindingResult result = e.getBindingResult();
        List<ObjectError> errors = result.getAllErrors();
        StringBuilder messages = new StringBuilder();
        messages.append("[");
        for (int i = 0, len = errors.size() - 1; i <= len; ++i) {
            messages.append(errors.get(i).getDefaultMessage());
            if (i != len)
                messages.append(",");
        }
        messages.append("]");
        return ResultUtils.error(messages.toString());
    }

    /**
     * 捕获空指针异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    public Result nullPointerException(NullPointerException npe) {
        log.error("空指针异常", npe);
        return ResultUtils.error("数据为NULL");
    }
}
