### ShadowDecorate **布局无侵入(注意下面第五点)、不占用View空间**的弥散阴影处理

[![](https://jitpack.io/v/zhangjianhd/ShadowDecorate.svg)](https://jitpack.io/#zhangjianhd/ShadowDecorate)

#### 一、效果：

![demo效果](https://github.com/zhangjianhd/ShadowDecorate/blob/master/screenshot/shadowanim.gif?raw=true)

***[demo下载](https://github.com/zhangjianhd/ShadowDecorate/raw/master/demo/Shadow.apk)***

#### 二、解决的问题：

传统情况下，drawable只支持绘制在view对应的canvas,也就是从ViewRoot根据view区域裁剪得到的canvas.
但是当我们处理带阴影背景效果时，就会比较麻烦：

- 我们期望的是阴影不占用内容区区域，否则就直接影响到我们写布局的时候要**预留各种阴影区域**
- 当使用单边投影的阴影时，**内容难以居中**:由于**阴影的偏移宽度**，导致每个使用的地方要计算内容区偏移padding，以保证内容在视觉上居中。统一样式的drawable也很难适应各种尺寸的场景

使用ShadowDecorate，可以根据内容自由决定View的布局，不需要额外计算预留空间和偏移对齐，阴影会被画到View的外部。除去极少的特定场景下需要考虑布局的排布，几乎可以做到布局无侵入

#### 三：简单使用：

```groovy
allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```

```groovy
dependencies {
	        implementation 'com.github.zhangjianhd:ShadowDecorate:1.0.1'
	}
```



提供了底部偏移阴影的**DropShadowDrawable**以及四周弥散的阴影**DiffusionShadowDrawable**

构造方法可设置颜色以及圆角，直接创建出Drawable对象即可使用。

```kotlin
view.background = BlueDropShadowDrawable
```

```kotlin
view.background = DropShadowGradientDrawable(
            Color.parseColor("#FF646B"),
            Color.parseColor("#FE3439"),
            dp2px(this,5)
        )
```

![小圆角效果](https://github.com/zhangjianhd/ShadowDecorate/blob/master/screenshot/screenshot_red_rect_drop.png?raw=true)

***（DiffusionShadowDrawable同理，不再赘述）***
![DiffusionShadowDrawable同理](https://github.com/zhangjianhd/ShadowDecorate/blob/master/screenshot/screenshot_diffusion.png?raw=true)

可以重写contentDrawable()方法以自由绘制内容区域背景（如渐变或者带边框等等）：可以参考DropShadowGradientDrawable的实现，DropShadowGradientDrawable实现了内容区域y方向线性渐变

默认实现了几种样式（材料）

| 样式                                                   |
| ------------------------------------------------------ |
| BlueDropShadowDrawable/BlueDropShadowPressDrawable     |
| GrayDropShadowDrawable                                 |
| OrangeDropShadowDrawable/OrangeDropShadowPressDrawable |
| RedDropShadowDrawable/RedDropShadowPressDrawable       |

#### 四：使用StatefulDrawableDecorate：

同时提供了有状态的装饰器，将不同的材料组合起来，以提供按钮的点击、不可用等状态组合
默认实现了demo的三种样式效果

| 样式                          |                                                              |
| ----------------------------- | ------------------------------------------------------------ |
| BlueStatefulDrawableFactory   | ![BlueStatefulDrawableFactory](https://github.com/zhangjianhd/ShadowDecorate/blob/master/screenshot/screenshot_blue_drop.png?raw=true) |
| OrangeStatefulDrawableFactory | ![OrangeStatefulDrawableFactory](https://github.com/zhangjianhd/ShadowDecorate/blob/master/screenshot/screenshot_orange_drop.png?raw=true) |
| RedStatefulDrawableFactory    | ![RedStatefulDrawableFactory](https://github.com/zhangjianhd/ShadowDecorate/blob/master/screenshot/screenshot_red_drop.png?raw=true) |

使用方法：

```kotlin
StatefulDrawableDecorate.createStatefulDrawable(OrangeStatefulDrawableFactory::class.java)
            .init(view)
```

可自由实现接口**IStatefulDrawableFactory**创建自己的状态组合

#### 五：注意!!一些局限性

采用的方案是参考水波纹点击效果RippleDrawable的实现。方案是从当前View,向ViewTree的根节点查找，找到一个有背景的ViewGroup(源码中命名ProjectionReceiver)后，将绘制交给该ProjectionReceiver去绘制，从而可以绘制到当前View外部。也因此有产生了一些局限性：

- 当ProjectionReceiver的大小不足以显示阴影时，阴影会被裁剪。不过这种情况比较少。
- 当前View的ProjectionReceiver(PR1)本身也在使用ShadowDecorate时，由于ProjectionReceiver的背景是继续向上交给它的ProjectionReceiver(PR2)去绘制的，导致PR1本身其实不绘制背景，所以当前View设置的背景也就显示不出来了。此时可以考虑增加一个同级的View或者和PR1同级的View去处理PR1的阴影背景。也就这一种情况对现有布局产生侵入。
