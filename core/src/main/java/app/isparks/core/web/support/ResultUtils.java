package app.isparks.core.web.support;


import app.isparks.core.pojo.enums.ResultType;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.util.thread.LocalThreadUtils;

import java.util.Collection;
import java.util.Optional;

/**
 * result build utils
 *
 * @author chenghd
 * @date 2020/7/22
 */
public final class ResultUtils {
    private ResultUtils() {
    }

    public static <T> Result<T> build(ResultType type, T data) {
        return new Result(type.getCode(), type.getMsg()).setData(data);
    }

    /**
     * success
     */
    public static <T> Result<T> success(String msg) {
        return build(ResultType.SUCCESS).setMsg(msg);
    }

    public static <T> Result<T> success(String msg, T data) {
        return build(ResultType.SUCCESS, data).setMsg(msg);
    }

    public static Result success() {
        return build(ResultType.SUCCESS);
    }

    /**
     * fail
     */
    public static Result fail(String failMsg) {
        return build(ResultType.FAIL).setMsg(failMsg);
    }

    public static Result fail() {
        return build(ResultType.FAIL);
    }

    /**
     * error
     */
    public static Result error(String errorMsg) {
        return build(ResultType.ERROR).setMsg(errorMsg);
    }

    public static Result error() {
        return build(ResultType.ERROR);
    }

    /**
     * build
     */
    public static Result build(ResultType type) {
        return new Result(type.getCode(), type.getMsg());
    }

    /**
     * 根据 result 返回数据类型
     * true 返回SUCCESS
     * false 返回FAIL
     */
    public static Result build(boolean b) {
        if (b) {
            return build(ResultType.SUCCESS);
        } else {

            Result result = build(ResultType.FAIL);
            LocalThreadUtils.getMessage().ifPresent((msg)->result.setData(msg));

            return result;
        }
    }

    public static Result build(boolean b, String successMsg, String failMsg) {
        Result result = build(b);
        return b ? result.setMsg(successMsg) : result.setMsg(failMsg);
    }

    public static Result build(Optional opt) {
        Result result = build(opt.isPresent());
        if (opt.isPresent()) {
            result.setData(opt.get());
        }
        return result;
    }

    public static Result build(Collection collection){
        Result result = build(true);
        return result.setData(collection);
    }

    public static Result build(PageData pageData){
        if(pageData == null){
            return fail().setData(new PageData<>());
        }
        return success("分页查询成功").setData(pageData);
    }

    public static Result build(Exception e) {
        Result result = error(e.getMessage()).setData(e);
        return result;
    }
}
