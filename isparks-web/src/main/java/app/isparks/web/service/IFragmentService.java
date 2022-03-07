package app.isparks.web.service;

import app.isparks.web.pojo.dto.FragmentDTO;

import java.util.List;

/**
 * 获取资源工具包
 */
public interface IFragmentService {


    /**
     * 读取
     *   resource/admin/sections
     *   或者
     *   resource/sections
     * 中的涅日共
     */
    String fragment(FragmentDTO fragment);

}
