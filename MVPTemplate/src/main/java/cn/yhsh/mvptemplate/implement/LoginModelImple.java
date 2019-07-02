package cn.yhsh.mvptemplate.implement;

import io.reactivex.Observable;
import cn.yhsh.mvptemplate.bean.LoginBean;
import cn.yhsh.mvptemplate.model.LoginModel;
import cn.yhsh.mvptemplate.retrofit.RetrofitFactory;
import cn.yhsh.mvptemplate.rxjava.BaseEntity;

/**
 * Created by MVP .
 * 在这里只负责处理或获取数据，不直接与View交互
 */
public class LoginModelImple implements LoginModel {

    @Override
    public Observable<BaseEntity<LoginBean>> login(String account, String psw) {
        return RetrofitFactory.getInstance().login(account, psw);
    }

}
