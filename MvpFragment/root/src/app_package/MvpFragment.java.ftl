package ${packageName}.mvp.view.fragment;

import ${packageName}.R;
import ${packageName}.mvp.base.BaseFragment;
import ${packageName}.mvp.contract.${ContractName};
import ${packageName}.mvp.presenter.${PresenterName};
import android.os.Bundle;

/**
 * Created by HuangQiang on 2017/12/21.
 * @author HuangQiang
 * @github https://github.com/HuangQiang
 */

public class ${FragmentName} extends BaseFragment<${PresenterName}> implements ${ContractName}.${IViewName}{

			
	@Override
    protected void initView() {
        
		
    }
	
	public static ${FragmentName} newInstance() {
		${FragmentName} fragment = new ${FragmentName}();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
	
	@Override
    public int getLayoutRes() {
        return R.layout.fragment_${fragment_layout};
    }
	
	 @Override
    public void createPresenter() {
        mPresenter = new ${PresenterName}(mContext, this);
    }
	
	@Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }
		
}
