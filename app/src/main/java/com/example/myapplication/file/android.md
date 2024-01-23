# Activity

## 1.Activity生命周期

onCreate（）：是 activity 的创建时候调用，做一些初始化布局之类的操作                        
onStart（）：即将显示页面的时候调用。用户无法操作，也是初始化操作，                               
onresume（）：当前 Activity 处于栈顶，获取焦点可以和用户进行交互，处于运行状态                
onPause（）：暂停状态。可能被其他的 activity 覆盖，仍然可见，但是失去焦点不能和用户进行交互                         
onstop（）：完全不可见的时候被调用。处于停止状态。内存不足这个 Activity 可能会被杀死，进行资源回收。                
ondestroy（）：activity 被销毁的时候调用，进行资源释放。                 
onRestart（）：从不可见的时候变成可见。            
成对出现：onCreate 和 onDestroy：根据 activity 创建和销毁               
onStart 和 onstop：根据 activity 是否可见，            
onResume 和 onpause：根据 activity 是否显示在前台                  

### (1)从A->B->A

    A 到 B 
    A(oncreate() -> onStart()->onResume()) ——> A(onpause()) ——> B（oncreate() -> onStart()->
    onResume()）——> A(onstop())

    返回 B
    B (onpause()) ——> A(onRestart() -> onStart()->onResume()) ——> B(onstop()->onDestroy())
    总结：页面可见性发生改变时候，当前页面先调用 onpause();

### (2)横竖屏切换时activity的生命周期

#### AndroidManifest没有设置configChange属性的时候

    启动：
    oncreate -> onstart -> onResume
    切换横竖屏->onPause -> onSaveInstaceState ->onstop->onDestrory 
    重新启动可见->oncreate -> onstart -> onReStoreInstanceState-> onResume   

总结: 没有设置configChanges属性，Android6.0，7.0，8.0系统手机表现都是一样的，当前的界面调用onSavelnstanceState走一遍流程，然后重启调用onRestorelnstanceState再走一遍完整流程，最终destory.

#### AndroidManifest设置了configChanges,android:configChanges="orientation”竖屏，

Android 6.0

    启动:
    onCreate --> onStart --> onResume
    切换横屏:
    onPause -->onSavelnstanceState -->onStop -->onDestroy 
    -->onCreate-->onStart-->onRestorelnstanceState-->onResume 

Android 7.0

    onConfigurationChanged-->onPause -->onSavelnstanceState -->onStop -->onDestroy 
    -->onCreate-->onStart -->onRestorelnstanceState-->onResume 

Android 8.0

    onConfigurationChanged

总结: 设置了configChanges属性为orientation之后Android6.0 同没有设置configChanges情况相同，完整的走完了两个生命周期，调用了onSavelnstanceState和onRestorelnstanceState方法;      
Android 7.0则会先回调onConfigurationChanged方法，剩下的流程跟Android6.0 保持一致;                      
Android 8.0 系统更是简单只是回调了onConfigurationChanged方法，并没有走Activity的生命周期方法。                                 

## 2.Activity 的四个启动模式

FLAG_ACTIVITY_SINGLE_TOP:对应 singleTop 启动模式。                                                             
FLAG_ACTIVITY_NEW_TASK:对应 singleTask 模式。 

### 标准模式

就是不管 activity 存不存在都 new 一个 Activity 出来放在当前任务栈的栈顶，
比如 ABCD 四个 Activity，D 处于栈顶，D 要通过 Intent 跳转到 A,则任务栈中就是 ABCDA。
比较常见的场景是：社交应用中，点击查看用户 A 信息->查看用户 A 粉丝->在粉丝中挑选查看用户 B 信息->查看用户
A 粉丝... 这种情况下一般我们需要保留用户操作 Activity 栈的页面所有执行顺序。

### Singletop

如果要启动的 Activity 处于栈顶，则调用 onNewIntent（）                             
比如 ABCD 四个 Activity，D 处于栈顶，D 要通过 Intent 要跳转到它本身的 D ,调用 onNewIntent（）
比如 ABCD 四个 Activity，D 处于栈顶，D 要通过 Intent 要跳转到 B，新建一个 B 压入栈顶，任务栈中就是 ABCDB

举例：三条推送点进去是一个 Activity，SingTop App
用户收到几条好友请求的推送消息，需要用户点击推送通知进入到请求者个人信息页，将信息页设置为SingleTop
模式就可以增强复用性。

### SingTask 
根据 TaskAffinity 寻找对应的任务栈。                                                   
如果任务栈不存在，那就新建任务栈，新建 activity 实例。                                                                           
如果任务栈存在：任务栈中不存在该 Activity 实例，就新建一个 Activity 压入栈                                                                        
             任务栈中存在该实例，将该实例顶部的实例出栈，并将自己置于栈顶                                                       

比如 ABCD，要从 D 通过 Intent 跳转到 B（走 onNewIntent），则弹出 CD 销毁，变成 AB          

举例，首页肯定在栈底。                 
使用举例：浏览器首页，用户从多个应用启动浏览器首页，主页面仅仅启动一次，其余都走onNewIntent,并且清空主页面上的其他页面。
                                
onNewIntent 的调用时机：singleTop、singleTask、singleInstance 模式下都会调用 onNewIntent()。
目的：复用Activity;
onCreate() 和 onNewIntent() 不会被同时调用。

调用 onNewIntent()生命周期如下：onNewIntent()->onRestart()->onStart()->onResume()。           

onNewIntent 方法， 如下所示:

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent); 
        setIntent(intent); 
    }

注意：没有在 onNewIntent()里面设置 setIntent()方法，将最新的 intent 设置给这个 activity 实例。那么在 onNewIntent()里面的 getIntent()得到的 intent 都是旧数据。             

onNewIntent(Intent intent)方法就是提供给 singleTask 模式这种特定实现的有效保持 intent 上下文的方法；             


### 单例模式

一个实例单独占一个任务栈，全局唯一性，如果使用时已经存在就将该任务栈调度到前台。
                                                      
任务栈是 APP 管理 Activity 的一种容器 ，一般一个应用程序一个任务栈，任务栈管理该应用的 activity 进出栈

taskAffinity 属性能给 Activity 指定 task,但必须使用 FLAG_ACTIVITY_NEW_TASK 标记
默认的 taskAffinity 常用于独立栈：

用例：比如闹钟的提醒页面，点击之后进入闹钟详情，再返回原app不影响原来的app

## 3.Activity/Dialog/PopupWindow/Toast与WindowState:

    * Activity/Dialog/PopupWindow/Toast在WMS都有对应的WindowState，
    * 只是Activity/Dialog/PopupWindow的WindowState属于同一个AppWindowToken，也就是Activity的token,
    * 而Toast的WindowState属于自己独有的WindowToken。


## 4. Android 程序中 Context 分成两种。
一种是 Activity Context，另一种是 Application Context。                            

凡是跟 UI 相关的，都应该使⽤Activity 做为 Context 来处理                 

通过 Application Context 来启动 Activity 的话。就需要 FLAG_ACTIVITY_NEW_TASK 属性，不管这个 Activity 是属于其他程序还是自己这个程序的。

使用：

    Intent intent = new Intent(new Intent(this, TestActivity.class));
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); MyApplication.getContext().startActivity(intent);

如果我们用ApplicationContext 去启动一个 LaunchMode 为 standard 的 Activity 的时候会报错

    android.util.AndroidRuntimeException: Calling startActivity from outside of an Activity
    context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?

因为 ⾮Activity 类型的 Context 并没有所谓的任务栈，所以待启动的 Activity 就找不到栈了。
为待启动的 Activity 指定 FLAG_ACTIVITY_NEW_TASK 标记位，这样启动的时候就为它创建一个新的任务栈，而此时 Activity 是以 singleTask 模式启动的。

在 Application 和 Service 中去 layout inflate 也是合法的，但是会使用系统默认的主题样式，如果你自定义了某些样式可能不会被使用。所以这种方式也不推荐使用。
                                                
对于 startActivity 操作                                    
①当为 Activity Context 则可直接使用                                                             
②当为其他 Context, 则必须带上 FLAG_ACTIVITY_NEW_TASK flags 才能使用，因为⾮ Activity context 启动 Activity 没有 Activity 栈，则无法启动，因此需要加开启新的栈;
                    
另外 UI 相关要 Activity 中使用

getApplication和getApplicationContext区别?

1.对于Activity/Service来说,getApplication(和getApplicationContext0的返回值完全相同; 除非厂商修改过接口;                                  
2.BroadcastReceiver在onReceive的过程，能使用getBaseContext.getApplicationContext获取所在Application,而无法使用getApplication;                            
3.ContentProvider能使用getContext0.getApplicationContext获取所在应用程序。绝大多数情况下没有问题，但是有可能会出现空指针的问题，                    
情况如下:                        
当同一个进程有多个apk的情况下，对于第二个apk是由provider方式拉起的，前面介绍过provider创建过程并不会初始化所在应用程序，此时执行返回的结果便是空。所以对于这种情况要做好判空。                                  

## 5.activit相关问题

### (1)onDestory()一定会执行吗？

正常情况下的返回 onDestory 一定会执行的，
后台强杀可能会发生：
               
当前仅有一个 Activity,这时候强杀，会执行，                                
当前很多 activity 实例，从 A 到 B 到 C，后台强杀只会 A 的 onDestroy，BC 都不会执行了。

### (2)onStop()一定会执行吗？

如果要启动的是个透明的窗口,或者是对话框的样式,就不会执行。onstop 用于停止更新 UI。

### (3)怎么写一个Activity 的统一管理类：

⑴定义一个 ActivityManager 实现 Application.ActivityLifecycleCallbacks；                                        
⑵List<WeakReference<Activity>> mActivityStack；                                   

七个方法

    1,onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) { 
        Addactivity()
    }
    
    2,onActivityStarted(@NonNull Activity acvity) 

    3,onActivityResumed(@NonNull Activity activity){ 
        mCurrentResumedActivity = activity
    }

    4,onActivityPaused(@NonNull Activity activity){ 
        mCurrentResumedActivity = null 
    }

    5,onActivityStopped(@NonNull Activity activity) 

    6,onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) 

    7,onActivityDestroyed(@NonNull Activity activity){ 
        removeActivity
    }


## 6.Intent 可传递的数据类型有 3 种

1.java 的 8 种基本数据类型（boolean byte char short int long float double）、String 以及他们的数组形式；                            
2.Bundle 类，Bundle 是一个以键值对的形式存储可传输数据的容器；                                
3.实现了 Serializable 和 Parcelable 接口的对象，这些对象实现了序列化。                                  

    Intent 传输数据的大小有限制吗？如何解决？

    答：Intent 中的 Bundle 是使用 Binder 机制进行数据传送的，数据会写到内核空间，
    Binder 的缓冲区是有大小限制的,有些 ROM 是 1M, 有些 ROM 是 2M;

    解决办法：
    1.尽量减少传输数据量。
    2.Intent 通过绑定一个 Bundle 来传输，这个可以超过 1M，不过也不能过大。
    3.通过内存共享，使用静态变量或者使用 EventBus 等类似的通信工具。
    4.通过文件共享。

## 7.Activity加载的流程

App 启动流程(基于Android8.0)

点击桌面 App图标，Launcher进程采用 BinderIPC(具体为ActivityManager.getService 获取 AMS 实例)system server的AMS发起 startActivity请求                    
system _server 进程收到请求后，向 Zygote 进程发送创建进程的请求Zygote 进程 fork 出新的子进程，即 App 进程，

App 进程创建即初始化 ActivityThread，然后通过 BinderIPC 向 system server 进程的 AMS 发起 attachApplication 请求，system server 进程的 AMS 在收到 attachApplication 请求后，做一系列操作后，通知 ApplicationThreadbindApplication，然后发送 H.BIND APPLICATION 消息

主线程收到 H.BIND APPLICATION 消息，调用handleBindApplication 处理后做一系列的初始化操作初始化 Application 等

system server 进程的 AMS 在 bindApplication 后，会调用ActivityStackSupervisor.attachApplicationLocked，之后经过一系列操作，在 realStartActivityLocked 方法通过Binder IPC 向 App 进程发送scheduleLaunchActivity 请求。

app的binder 线程 (ApplicationThread) 在收到请求后，通过 handler 向主线程发送LAUNCH ACTIVITY 消息;

主线程收到 message后经过 handleLaunchActivity，performLaunchActivity 方法，然后通过反射机制创建目标Activity ;

通过Activityattach方法创建 window并目和 Activity 关联，然后设置 WindowManager 用来管理 window，然后通知 Activity 已创建，即调用 onCreate

然后调用 handleResumeActivity，Activity可见

![img.png](pic/img.png)

# 广播

## BroadcastReceiver 与LocalBroadcastReceiver 有什么区别?

BroadcastReceiver 是跨应用广播，利用Binder机制实现支持动态和静态两种方式注册方式。                                         
LocalBroadcastReceiver 是应用内广播，利用Handler 实现，利用了IntentFilter的match功能，提供消息的发布与接收功能，实现应用内通信，效率和安全性比较 高仅支持动态注册

# Services

## 1.IntentService
IntentService是Service的子类，继承与Service类，用于处理需要异步请求。

用户通过调用 Context.StartService(Intent)发送请求，服务根据需要启动，使用工作线程依次处理每个Intent，并在处理完所有工作后自身停止服务。            
使用时，扩展IntentService并实现onHandleIntent(android.content.Intent),IntentService接收Intent，启动工作线程，并在适当时机停止服务。                  
所有的请求都在同一个工作线程上处理，一次处理一个请求，所以处理完所以的请求可能会花费很长的时间，但由于IntentService是另外了线程来工作，所以保证不会阻止App的主线程。

## 2.IntentService与Service的区别

### 何时使用

Service用于没有UI工作的任务，但不能执行长任务(长时间的任务)，如果需要Service来执行长时间的任务，则必须手动开启个线程来执行该Service。                                                   
IntentService可用于执行不与主线程沟通的长任务.                      

### 触发方法

Service通过调用 startService() 方法来触发。                                         
IntentService通过Intent来触发，开启一个新的工作线 程并在线程上调用onHandleIntent方法

### 运行环境

Service 在App主线程上运行，没有与用户交互，即在后台运行，如果执行长时间的请求任务会阻止主线程工作。                                     
IntentService在自己单独开启的工作线程上运行，即使执行长时间的请求任务也不会阻止主线程工作。

### 何时停止

如果执行了Service，我们是有责任在其请求任务完成后关闭服务，通过调用 stopSelf 或 stopService 来结束服务.                                    
IntentService会在执行完所有的请求任务后自行关闭服务，所以我们不必额外调用stopSelf去关闭它.                     

## 3. 谈一谈startService和bindService的区别，生命周期以及使用场景?

### 1、生命周期上的区别
执行startService时，Service会经历onCreate->onStartCommand。当执行stopService时，直接调用onDestroy方法。调用者如果没有stopService，Service
会一直在后台运行，下次调用者再起来仍然可以stopService。                                                                

执行bindService时，Service会经历onCreate->onBind。这个时候调用者和Service绑定在一起。调用者调用unbindService方法或者调用者Context不存在了 (
如Activity被finish了)，Service就会调用onUnbind->onDestroy。这里所谓的绑定在一起就是说两者共存亡了。                                    
                                               
多次调用startService,该Service只能被创建一次，即该Service的onCreate方法只会被调用一次。                                           
但是每次调用startService，onStartCommand方法都会被调用。Service的onStart方法在API5时被废弃，替代它的是onStartCommand方法。                        

第一次执行bindService时，onCreate和onBind方法会被调用，但是多次执行bindService方法，onCreate和onBind方法并不会被多次调用，即并不会多次创建服务和绑定服务。

### 2、调用者如何获取绑定后的Service的方法

onBind回调方法将返回给客户端一个IBinder接口实例，IBinder允许客户端回调服务的方法，比如得到Service运行的状态或其他操作。
我们需要IBinder对象返回具体的Service对象才能操作，所以说具体的Service对象必须首先实现Binder对象

### 3、既使用startService又使用bindService的情况

如果一个Service又被启动又被绑定，则该Service会一直在后台运行。首先不管如何调用，onCreate始终只会调用一次。对应startService调用多少次，Service的onStart
方法便会调用多少次。                                                

Service的终止，需要unbindService和stopService同时调用才行。不管startService与bindService的调用顺序。                              
如果先调用unbindService，此时服务不会自动终止，再调用stopService之后，服务才会终止;               

如果先调用stopService，此时服务也不会终止，而再调用unbindService或者之前调用bindService的Context不存在了(如Activity被finish的时候)之后，服务才会自动停止。            

那么，什么情况下既使用startService，又使用bindService呢?                               
如果你只是想要启动一个后台服务长期进行某项任务，那么使用startService便可以了。如果你还想要与正在运行的Service取得联系，那么有两种方法:
一种是使用broadcast，另一种是使用bindService。                                      
前者的缺点是如果交流较为频繁，容易造成性能上的问题，而后者则没有这些问题。因此，这种情况就需要startService和bindService一起使用了。

## 4.本地服务与远程服务

本地服务依附在主进程上，在一定程度上节约了资源。本地服务因为是在同一进程，因此不需要IPC，也不需要AIDL。相应bindservice会方便很多。
缺点是主进程被kill后，服务便会终止。                
              
远程服务是独立的进程，对应进程名格式为所在包名加上你指定的android:process字符串。由于是独立的进程，因此在Activity所在进程被kill的是偶，该服务依然在运行。
缺点是该服务是独立的进程，会占用一定资源，并且使用AIDL进行IPC稍微麻烦一点。     

对于startservice来说，不管是本地服务还是远程服务，我们需要做的工作都一样简单。             

## 5.Service如何进行保活?

利用系统广播拉活                               
利用系统service拉活                               
利用Native进程拉活<Android5.0以后失效> fork进行监控主进程，利用native拉活                           
利用JobScheduler机制拉活<Android5.0以后>利用账号同步机制拉活                           

# ContentProvider是如何实现数据共享的?

ContentProvider (内容提供者):对外提供了统一的访问数据的接口。
ContentResolver (内容解析者): 通过URI的不同来操作不同的ContentProvider中的数据
ContentObserver (内容观察者): 观察特定URI引起的数据库的变化。
通过ContentResolver进行注册，观察数据是否发生变化及时通知刷新页面(通过Handler通知主线程更 新UI)

ContentProvider ：内容提供者，对外提供了统一的访问数据的接口,用于对外提供数据,比如联系人应用中就是用了ContentProvider           
一个应用可以实现ContentProvider来提供给别的应用操作通过ContentResolver来操作别的应用数据            

ContentResolver ：内容解析者，用于获取内容提供者提供的数据,通过URI的不同来操作不同的ContentProvider中的数据              
ContentResolver.NotifyChanged(uri)发出消息                        

ContentObserver ：内容监听者,可以监听数据的改变状态 观察(捕捉)特定的Uri引起的数据库的变化,及时通知刷新页面(通过Handler通知主线程更 新UI)               
ContentResolver.registerContentObserver监听消息                          

概括:                              
使用ContentResolver来获取ContentProvider提供的数据，同时注册ContentObserver监听数据的变化    

# Fragment
## Fragment从创建到销毁整个生命周期中涉及到的方法依次为：
onAttach()→onCreate()→onCreateView()→onActivityCreated()→onStart()→onResume()                   
→onPause()→onStop()→onDestroyView()→onDestroy()→onDetach()， 

1.打开界面                       
onCreate ()->onCreateView()->onActivityCreated()->onStart()->onResume()                   
按下主屏幕键                       
onPause() ->onStop ()                
重新打开界面                    
onStart()->onResume ()                    
按后退键                 
onPause ()->onStop()->onDestroyView()->onDestroy()->onDetach()    

![img_1.png](img_1.png)
 
和Activity有不同的方法：          
onAttach()：当Fragment和Activity建立关联时调用；            
onCreateView()：当fragment创建视图调用，在onCreate之后；          
onActivityCreated()：当与Fragment相关联的Activity完成onCreate()之后调用；           
onDestroyView()：在Fragment中的布局被移除时调用；                          
onDetach()：当Fragment和Activity解除关联时调用；              

onViewCreated在onActivityCreated之前

## Fragment中add、remove、replace区别
首先获取FragmentTransaction对象：

    FragmentTransaction transaction = getFragmentManager().beginTransaction();

两种方法不同之处：是否要清空容器再添加fragment的区别，用法上add配合hide或是remove使用，replace一般单独出现。                          

添加add: 一般会配合hide使用

    transaction.add(R.id.fragment_container, oneFragment).hide(twoFragment).commit()

第一个参数是容器id， 第二个参数是要添加的fragment，添加不会清空容器中的内容，不停的往里面添加。 

不允许添加同一个fragment实例，这是非常重要的特点。
如果一个fragment已经进来的话，再次添加的话会报异常错误的 ，添加进来的fragment都是可见的（visible），后添加的fragment会展示在先添加的fragment上面，在绘制界面的时候会绘制所有可见的view                      
所以大多数add都是和hide或者是remove同时使用的。这样可以节省绘制界面的时间，节省内存消耗，是推荐的用法。                   

替换replace:                            

    transaction.replace(R.id.fragment_container, oneFragment).commit()

替换会把容器中的所有内容全都替换掉，有一些app会使用这样的做法，保持只有一个fragment在显示，减少了界面的层级关系。          

相同之处：每次add和replace都要走一遍fragment 的周期。                        

处理方式：
首先在add的时候，加上一个tab参数 

    transaction.add(R.id.content, ContentFragment,“tag”);

然后当ContentFragment引用被回收置空的话，先通过

    ContentFragment＝FragmentManager.findFragmentByTag("tag"); 
找到对应的引用，然后继续上面的hide,show来处理;

## FragmentPagerAdapter与FragmentStatePagerAdapter的区别与使用场景
相同点:二者都继承PagerAdapter                      
不同点:FragmentPagerAdapter的每个Fragment会持久的保存在FragmentManager中，只要用户可以返回到页面中，它都不会被销毁。 因此适用于那些数据相对静态的页，Fragment数量也比较少的那种；                          
FragmentStatePagerAdapter只保留当前页面，当页面不可见时，该Fragment就会被消除，释放其资源。因此适用于那些数据动态性较大、占用内存较多，多Fragment的情况；




