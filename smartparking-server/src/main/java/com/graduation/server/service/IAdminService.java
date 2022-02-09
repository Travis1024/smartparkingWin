package com.graduation.server.service;

import com.graduation.server.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.server.pojo.Admin;
import com.graduation.server.pojo.RespBean;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author travis-wei
 * @since 2022-01-23
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 登陆之后返回token
     * 
     * @author travis-wei
     * @createTime 2022/1/25
     * @param userid	
     * @param password	
     * @param request	
     * @return com.graduation.server.pojo.RespBean
     **/
    
    RespBean login(String userid, String password, HttpServletRequest request);

    /**
     * 根据用户id获取用户信息
     *
     * @author travis-wei
     * @createTime 2022/1/25
     * @param userid
     * @return com.graduation.server.pojo.Admin
     **/

    Admin getAdminByUserId(String userid);
}
