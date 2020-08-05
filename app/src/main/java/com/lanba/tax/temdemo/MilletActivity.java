//package ${packageName};
//
//import android.os.Bundle;
//
//import com.millet.mylibrary.mvvm.BaseBvmActivity;
//
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//
//public class ${activityClass} extends BaseBvmActivity<${viewModel}, ${activityBinding}> {
//
//    @Override
//    protected void initData(@Nullable Bundle savedInstanceState) {
//        mDataBinding.setViewModel(mViewModel);
//    }
//
//    @Override
//    protected void initView(@Nullable Bundle savedInstanceState) {
//
//    }
//
//    @Override
//    protected void loadData(@Nullable Bundle savedInstanceState) {
//
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.${layoutName};
//    }
//
//    @NotNull
//    @Override
//    protected ${viewModel} createViewModel() {
//        return new ${viewModel}();
//    }
//
//}
