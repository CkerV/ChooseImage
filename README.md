#远程依赖
* Gradle Compile

  Add it in your root build.gradle at the end of repositories:

 ```
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
 	}
 ```
 Step 2. Add the dependency
 
 ```
 dependencies {
	    compile 'com.github.CkerV:ChooseImage:1.0'
 }
 ```
 
#用法说明
## 一句话启动ChooseImageActivity，可分单选模式和多选模式模式
* 单选模式

```java
ChooseImageActivity.startActivityInSingleMode(TestActivity.this);
```
* 多选模式

```java
//传进可选择图片的数量，例如 mSelectNum = 9
ChooseImageActivity.startActivity(TestActivity.this, mSelectNum);
```
## 启动ChooseImageActivity的Context必须实现**OnImageSelectedFinishedListener**接口，以实现完成选择图片后的逻辑处理
```java
public class TestActivity extends BaseActivity implements OnImageSelectedFinishedListener
```
```java
 @Override
    public void onFinish(List<String> selectedImages) {
        for(int i = 0; i < selectedImages.size(); i++) {
            Log.i(TAG, "已完成选择的图片:" + selectedImages.get(i));
        }
        mDatas.addAll(0, selectedImages);
        //处理已选择的图片数量超过可选择图片数量的逻辑
        if(mDatas.size() - 1 >= mSelectNum) {
            for(int i = mDatas.size() - 1; i >= mSelectNum; i--)
                mDatas.remove(i);
        }
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }
```

#library概述
* 基于Universal-Image-Loader封装的选择图片library，参考[Android 超高仿微信图片选择器 图片该这么加载](http://blog.csdn.net/lmj623565791/article/details/39943731/),采用**MVP**架构对其进行封装。
![图片1](http://img.blog.csdn.net/20161015160533509)
![图片2](http://img.blog.csdn.net/20161015160554602)

#封装思路
* 结构图
![结构图](http://img.blog.csdn.net/20161015173748393)
* ChooseImageActivity继承自IChooseImageView，作为**view**层，处理相关的界面展示；
* ChooseImagePresenterImpl继承自IChooseImagePresenter，作为**presenter**层，处理相关的业务逻辑；
* ChooseImageManager作为**model**层，用来统一管理相关变量；

#MVP相关代码
* view层
``` java
public interface IChooseImageView extends BaseView {

    /**
     * 增加此方法是为了暴露给p层进行逻辑处理时的判空
     * @return
     */
    RecyclerView getRecyclerView();

    /**
     * 初始化RecyclerView
     * @param allImages
     * @param selectedImages
     */
    void initRecyclerView(List<String> allImages, List<String> selectedImages);

    /**
     * 设置图片张数
     * @param imagesCount
     */
    void setImagesCountText(int imagesCount);

    /**
     * item点击时改变item背景
     * @param imageUrl
     * @param selected
     */
    void changeItemBackground(String imageUrl, boolean selected);

    /**
     * 文件夹改变时刷新界面
     * @param imageFolderBean
     */
    void refreshAfterChangeImageFolder(ImageFolderBean imageFolderBean);

    /**
     * 通知adapter刷新数据源
     */
    void notifyAdapterDataSetChanged();
}
```
* presenter层
``` java
public interface IChooseImagePresenter  {
    /**
     * 加载图片
     */
    void loadImages();

    /**
     * item点击时的逻辑处理
     * @param view
     * @param position
     * @param choiceMode
     * @param selectLimitNum
     */
    void doOnItemClick(View view, int position, int choiceMode, int selectLimitNum);

    /**
     * 文件夹改变时的逻辑处理
     * @param imageFolderBean
     */
    void doOnChangeImageFolder(ImageFolderBean imageFolderBean);

    /**
     * 完成图片选择时的逻辑处理
     * @param onImageSelectedFinishedListener 向外部暴露的回调接口,由外部进行实现,内部直接调用其回调方法
     */
    void chooseCompleted(OnImageSelectedFinishedListener onImageSelectedFinishedListener);

    /**
     * 选择图片返回
     */
    void chooseBack();
}
```

#listener说明
* **OnImageSelectedChangeListener**是用来监听选择的图片改变时所回调的接口，外部启动ChooseImageActivity的context可实现此接口（非必要）
* **OnImageSelectedFinishedListener**是用来监听完成选择图片后所回调的接口，外部启动ChooseImageActivity的context**必须**实现此接口，否则会抛出异常！

