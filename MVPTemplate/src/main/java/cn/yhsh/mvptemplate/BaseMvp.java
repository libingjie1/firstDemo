package cn.yhsh.mvptemplate;

import cn.yhsh.mvptemplate.model.BaseModel;
import cn.yhsh.mvptemplate.presenter.BasePresenter;
import cn.yhsh.mvptemplate.view.BaseView;

public interface BaseMvp<M extends BaseModel, V extends BaseView, P extends BasePresenter> {
    M createModel();

    V createView();

    P createPresenter();
}
