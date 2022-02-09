package com.graduation.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.server.config.security.JwtTokenUtils;
import com.graduation.server.pojo.Admin;
import com.graduation.server.mapper.AdminMapper;
import com.graduation.server.pojo.RespBean;
import com.graduation.server.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author travis-wei
 * @since 2022-01-23
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 登录之后返回token
     *
     * @param userid
     * @param password
     * @param request
     * @return com.graduation.server.pojo.RespBean
     * @author travis-wei
     * @createTime 2022/1/25
     **/

    @Override
    public RespBean login(String userid, String password, HttpServletRequest request) {
        //登陆
        UserDetails userDetails = userDetailsService.loadUserByUsername(userid);
        if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("用户名或密码不正确!");
        }

        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails
                , null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        //生成token
        String token = jwtTokenUtils.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登陆成功!", tokenMap);
    }

    /**
     * 根据用户id获取用户信息
     * 
     * @author travis-wei
     * @createTime 2022/1/25
     * @param userid	
     * @return com.graduation.server.pojo.Admin
     **/
    
    @Override
    public Admin getAdminByUserId(String userid) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("admin_id",userid));
    }
}
