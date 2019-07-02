package cn.yhsh.mvptemplate.model;

import io.reactivex.Observable;
import cn.yhsh.mvptemplate.bean.LoginBean;
import cn.yhsh.mvptemplate.rxjava.BaseEntity;

public interface LoginModel extends BaseModel {
    /**
     * 登录
     */
    Observable<BaseEntity<LoginBean>> login(String account, String psw);


}
