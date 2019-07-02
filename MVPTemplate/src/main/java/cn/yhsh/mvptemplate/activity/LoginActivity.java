package cn.yhsh.mvptemplate.activity;

import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.yhsh.mvptemplate.R;
import cn.yhsh.mvptemplate.bean.LoginBean;
import cn.yhsh.mvptemplate.implement.LoginModelImple;
import cn.yhsh.mvptemplate.model.LoginModel;
import cn.yhsh.mvptemplate.presenter.LoginPresenter;
import cn.yhsh.mvptemplate.utils.MyUtils;
import cn.yhsh.mvptemplate.view.LoginView;

/**
 * Created by MVP
 */
public class LoginActivity extends BaseMvpActivity<LoginModel, LoginView, LoginPresenter> implements LoginView {


    @BindView(R.id.edt_account)
    EditText edtAccount;
    @BindView(R.id.edt_psw)
    EditText edtPsw;


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void init() {

    }


    @Override
    public LoginModel createModel() {
        return new LoginModelImple();
    }

    @Override
    public LoginView createView() {
        return this;
    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }


    @Override
    public String getAccount() {
        return edtAccount.getText().toString();
    }

    @Override
    public String getPassword() {
        return edtPsw.getText().toString();
    }

    @Override
    @OnClick(R.id.btn_login)
    public void clickLoginBtn() {
        presenter.login(this);
    }

    @Override
    public void loginSuccess(LoginBean t) {
        MyUtils.showToast(this, "登录成功!" + "token:" + t.getToken());
    }

    @Override
    public void loginFail(String msg) {
        MyUtils.showToast(this, msg);
    }


}
