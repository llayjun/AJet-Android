package com.millet.mylibrary.base.vm

import androidx.lifecycle.*
import com.millet.mylibrary.bean.DialogBean
import com.millet.mylibrary.base.ViewModelLifecycle
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*

/**
 * BaseViewModel
 */
abstract class BaseViewModel : ViewModel(), ViewModelLifecycle {

    private lateinit var lifecycleOwner: LifecycleOwner

    // 管理RxJava请求
    public var mCompositeDisposable: CompositeDisposable? = null

    // 管理通知Activity/Fragment是否显示等待的Dialog
    public var mShowDialog: DialogLiveData<DialogBean> = DialogLiveData<DialogBean>()

    // 当ViewModel层出现错误需要通知到Activity／Fragment
    public var mThrowable: MutableLiveData<Throwable> = MutableLiveData<Throwable>()

    // 当ViewModel层请求正确但数据不对需要通知到Activity／Fragment
    public var mToast: MutableLiveData<String> = MutableLiveData<String>()

    // 当ViewModel层请求关闭界面时候通知到Activity / Fragment
    public var mFinish: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    /**
     * 使用协程
     */
    fun launchUI(
        block: suspend CoroutineScope.() -> Unit = {}
        , error: suspend CoroutineScope.(Throwable) -> Unit = {}
        , complete: suspend CoroutineScope.() -> Unit = {}
    ): Job {
      return viewModelScope.launch(Dispatchers.Main) {
            try {
                mShowDialog.setValue(true)
                block()
            } catch (e: Throwable) {
                mThrowable.value = e
                error(e)
            } finally {
                mShowDialog.setValue(false)
                complete()
            }
        }
    }

    /**
     * 添加 RxJava 发出的请求
     */
    fun addDisposable(disposable: Disposable) {
        if (mCompositeDisposable == null || mCompositeDisposable!!.isDisposed)
            mCompositeDisposable = CompositeDisposable()
        mCompositeDisposable?.add(disposable)
    }

    /**
     * 监听mShowDialog
     */
    fun getShowDialog(owner: LifecycleOwner?, observer: Observer<DialogBean>?) {
        if (owner != null && observer != null) {
            mShowDialog.observe(owner, observer)
        }
    }

    /**
     * 监听mThrowable
     */
    fun getThrowable(owner: LifecycleOwner?, observer: Observer<Throwable?>?) {
        if (owner != null && observer != null) {
            mThrowable.observe(owner, observer)
        }
    }

    /**
     * 监听mToast
     */
    fun getToast(owner: LifecycleOwner, observer: Observer<String>) {
        mToast.observe(owner, observer)
    }

    /**
     * 监听mFinish
     */
    fun getFinish(owner: LifecycleOwner, observer: Observer<Boolean>) {
        mFinish.observe(owner, observer)
    }

    /**
     * ViewModel销毁同时也取消请求
     */
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
        if (mCompositeDisposable != null) {
            mCompositeDisposable?.dispose()
            mCompositeDisposable = null
        }
    }

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
        this.lifecycleOwner = owner
    }

    override fun onCreate() {

    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {

    }

    companion object {

        @JvmStatic
        fun <T : BaseViewModel> createViewModelFactory(viewModel: T): ViewModelProvider.Factory {
            return ViewModelFactory(viewModel)
        }
    }

}

/**
 * 创建ViewModel的工厂，以此方法创建的ViewModel，可在构造函数中传参
 */
class ViewModelFactory(private val viewModel: BaseViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModel as T
    }
}