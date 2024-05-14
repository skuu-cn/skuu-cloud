package cn.skuu.member.service.auth;

import cn.hutool.core.util.ObjectUtil;
import cn.skuu.framework.common.enums.CommonStatusEnum;
import cn.skuu.framework.common.enums.UserTypeEnum;
import cn.skuu.framework.common.exception.util.ServiceExceptionUtil;
import cn.skuu.framework.common.util.monitor.TracerUtils;
import cn.skuu.framework.common.util.servlet.ServletUtils;
import cn.skuu.member.controller.app.auth.vo.*;
import cn.skuu.member.convert.auth.AuthConvert;
import cn.skuu.member.dal.dataobject.user.MemberUserDO;
import cn.skuu.member.enums.ErrorCodeConstants;
import cn.skuu.member.service.user.MemberUserService;
import cn.skuu.system.api.logger.LoginLogApi;
import cn.skuu.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.skuu.system.api.oauth2.OAuth2TokenApi;
import cn.skuu.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import cn.skuu.system.api.sms.SmsCodeApi;
import cn.skuu.system.api.social.SocialUserApi;
import cn.skuu.system.enums.logger.LoginLogTypeEnum;
import cn.skuu.system.enums.logger.LoginResultEnum;
import cn.skuu.system.enums.oauth2.OAuth2ClientConstants;
import cn.skuu.system.enums.sms.SmsSceneEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;


/**
 * 会员的认证 Service 接口
 *
 * @author 芋道源码
 */
@Service
@Slf4j
public class MemberAuthServiceImpl implements MemberAuthService {

    @Resource
    private MemberUserService userService;
    @Resource
    private SmsCodeApi smsCodeApi;
    @Resource
    private LoginLogApi loginLogApi;
    @Resource
    private SocialUserApi socialUserApi;
//    @Resource
//    private SocialClientApi socialClientApi;
    @Resource
    private OAuth2TokenApi oauth2TokenApi;

    @Override
    public AppAuthLoginRespVO login(AppAuthLoginReqVO reqVO) {
        // 使用手机 + 密码，进行登录。
        MemberUserDO user = login0(reqVO.getMobile(), reqVO.getPassword());

        // 如果 socialType 非空，说明需要绑定社交用户
        String openid = null;
        if (reqVO.getSocialType() != null) {
//            openid = socialUserApi.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
//                    reqVO.getSocialType(), reqVO.getSocialCode(), reqVO.getSocialState())).getCheckedData();
        }

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user, reqVO.getMobile(), LoginLogTypeEnum.LOGIN_MOBILE, openid);
    }

    @Override
    @Transactional
    public AppAuthLoginRespVO smsLogin(AppAuthSmsLoginReqVO reqVO) {
        // 校验验证码
        String userIp = ServletUtils.getClientIP();
//        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(reqVO, SmsSceneEnum.MEMBER_LOGIN.getScene(), userIp)).getCheckedData();

        // 获得获得注册用户
//        MemberUserDO user = userService.createUserIfAbsent(reqVO.getMobile(), userIp, WebFrameworkUtils.getTerminal());
//        Assert.notNull(user, "获取用户失败，结果为空");

        // 如果 socialType 非空，说明需要绑定社交用户
        String openid = null;
        if (reqVO.getSocialType() != null) {
//            openid = socialUserApi.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
//                    reqVO.getSocialType(), reqVO.getSocialCode(), reqVO.getSocialState())).getCheckedData();
        }

        // 创建 Token 令牌，记录登录日志
//        return createTokenAfterLoginSuccess(user, reqVO.getMobile(), LoginLogTypeEnum.LOGIN_SMS, openid);
        return null;
    }

    @Override
    @Transactional
    public AppAuthLoginRespVO socialLogin(AppAuthSocialLoginReqVO reqVO) {
        // 使用 code 授权码，进行登录。然后，获得到绑定的用户编号
//        SocialUserRespDTO socialUser = socialUserApi.getSocialUserByCode(UserTypeEnum.MEMBER.getValue(), reqVO.getType(),
//                reqVO.getCode(), reqVO.getState()).getCheckedData();
//        if (socialUser == null) {
//            throw exception(ErrorCodeConstants.AUTH_SOCIAL_USER_NOT_FOUND);
//        }
//
//        // 情况一：已绑定，直接读取用户信息
//        MemberUserDO user;
//        if (socialUser.getUserId() != null) {
//            user = userService.getUser(socialUser.getUserId());
//        // 情况二：未绑定，注册用户 + 绑定用户
//        } else {
//            user = userService.createUser(socialUser.getNickname(), socialUser.getAvatar(), ServletUtils.getClientIP(), WebFrameworkUtils.getTerminal());
//            socialUserApi.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
//                    reqVO.getType(), reqVO.getCode(), reqVO.getState()));
//        }
//        if (user == null) {
//            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
//        }
//
//        // 创建 Token 令牌，记录登录日志
//        return createTokenAfterLoginSuccess(user, user.getMobile(), LoginLogTypeEnum.LOGIN_SOCIAL, socialUser.getOpenid());
        return null;
    }

    @Override
    public AppAuthLoginRespVO weixinMiniAppLogin(AppAuthWeixinMiniAppLoginReqVO reqVO) {
        // 获得对应的手机号信息
//        SocialWxPhoneNumberInfoRespDTO phoneNumberInfo = socialClientApi.getWxMaPhoneNumberInfo(
//                UserTypeEnum.MEMBER.getValue(), reqVO.getPhoneCode()).getCheckedData();
//        Assert.notNull(phoneNumberInfo, "获得手机信息失败，结果为空");
//
//        // 获得获得注册用户
//        MemberUserDO user = userService.createUserIfAbsent(phoneNumberInfo.getPurePhoneNumber(),
//                ServletUtils.getClientIP(), TerminalEnum.WECHAT_MINI_PROGRAM.WebFrameworkUtils.getTerminal());
//        Assert.notNull(user, "获取用户失败，结果为空");
//
//        // 绑定社交用户
//        String openid = socialUserApi.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
//                SocialTypeEnum.WECHAT_MINI_APP.getType(), reqVO.getLoginCode(), reqVO.getState())).getCheckedData();
//
//        // 创建 Token 令牌，记录登录日志
//        return createTokenAfterLoginSuccess(user, user.getMobile(), LoginLogTypeEnum.LOGIN_SOCIAL, openid);
        return null;
    }

    private AppAuthLoginRespVO createTokenAfterLoginSuccess(MemberUserDO user, String mobile,
                                                            LoginLogTypeEnum logType, String openid) {
        // 插入登陆日志
//        createLoginLog(user.getId(), mobile, logType, LoginResultEnum.SUCCESS);
//        // 创建 Token 令牌
//        OAuth2AccessTokenRespDTO accessTokenRespDTO = oauth2TokenApi.createAccessToken(new OAuth2AccessTokenCreateReqDTO()
//                .setUserId(user.getId()).setUserType(getUserType().getValue())
//                .setClientId(OAuth2ClientConstants.CLIENT_ID_DEFAULT)).getCheckedData();
//        // 构建返回结果
//        return AuthConvert.INSTANCE.convert(accessTokenRespDTO, openid);
        return null;
    }

    @Override
    public String getSocialAuthorizeUrl(Integer type, String redirectUri) {
//        return socialClientApi.getAuthorizeUrl(type, UserTypeEnum.MEMBER.getValue(), redirectUri).getCheckedData();
        return null;
    }

    private MemberUserDO login0(String mobile, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_MOBILE;
        // 校验账号是否存在
        MemberUserDO user = userService.getUserByMobile(mobile);
        if (user == null) {
            createLoginLog(null, mobile, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            createLoginLog(user.getId(), mobile, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            createLoginLog(user.getId(), mobile, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    private void createLoginLog(Long userId, String mobile, LoginLogTypeEnum logType, LoginResultEnum loginResult) {
        // 插入登录日志
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logType.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(getUserType().getValue());
        reqDTO.setUsername(mobile);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(loginResult.getResult());
        loginLogApi.createLoginLog(reqDTO);
        // 更新最后登录时间
        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
            userService.updateUserLogin(userId, ServletUtils.getClientIP());
        }
    }

    @Override
    public void logout(String token) {
        // 删除访问令牌
        OAuth2AccessTokenRespDTO accessTokenRespDTO = oauth2TokenApi.removeAccessToken(token).getCheckedData();
        if (accessTokenRespDTO == null) {
            return;
        }
        // 删除成功，则记录登出日志
        createLogoutLog(accessTokenRespDTO.getUserId());
    }

    @Override
    public void sendSmsCode(Long userId, AppAuthSmsSendReqVO reqVO) {
        // 情况 1：如果是修改手机场景，需要校验新手机号是否已经注册，说明不能使用该手机了
        if (Objects.equals(reqVO.getScene(), SmsSceneEnum.MEMBER_UPDATE_MOBILE.getScene())) {
            MemberUserDO user = userService.getUserByMobile(reqVO.getMobile());
            if (user != null && !Objects.equals(user.getId(), userId)) {
                throw ServiceExceptionUtil.exception(ErrorCodeConstants.AUTH_MOBILE_USED);
            }
        }
        // 情况 2：如果是重置密码场景，需要校验手机号是存在的
//        if (Objects.equals(reqVO.getScene(), SmsSceneEnum.MEMBER_RESET_PASSWORD.getScene())) {
//            MemberUserDO user = userService.getUserByMobile(reqVO.getMobile());
//            if (user == null) {
//                throw ServiceExceptionUtil.exception(ErrorCodeConstants.USER_MOBILE_NOT_EXISTS);
//            }
//        }
        // 情况 3：如果是修改密码场景，需要查询手机号，无需前端传递
//        if (Objects.equals(reqVO.getScene(), SmsSceneEnum.MEMBER_UPDATE_PASSWORD.getScene())) {
//            MemberUserDO user = userService.getUser(userId);
//            // TODO 芋艿：后续 member user 手机非强绑定，这块需要做下调整；
//            reqVO.setMobile(user.getMobile());
//        }

        // 执行发送
//        smsCodeApi.sendSmsCode(AuthConvert.INSTANCE.convert(reqVO).setCreateIp(ServletUtils.getClientIP()));
    }

    @Override
    public void validateSmsCode(Long userId, AppAuthSmsValidateReqVO reqVO) {
//        smsCodeApi.validateSmsCode(AuthConvert.INSTANCE.convert(reqVO));
    }

    @Override
    public AppAuthLoginRespVO refreshToken(String refreshToken) {
        OAuth2AccessTokenRespDTO accessTokenDO = oauth2TokenApi.refreshAccessToken(refreshToken,
                OAuth2ClientConstants.CLIENT_ID_DEFAULT).getCheckedData();
        return AuthConvert.INSTANCE.convert(accessTokenDO, null);

    }

    private void createLogoutLog(Long userId) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(LoginLogTypeEnum.LOGOUT_SELF.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(getUserType().getValue());
        reqDTO.setUsername(getMobile(userId));
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(LoginResultEnum.SUCCESS.getResult());
        loginLogApi.createLoginLog(reqDTO);
    }

    private String getMobile(Long userId) {
        if (userId == null) {
            return null;
        }
        MemberUserDO user = userService.getUser(userId);
        return user != null ? user.getMobile() : null;
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.MEMBER;
    }

}
