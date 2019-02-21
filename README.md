# RippleImage
具有涟漪效果的图片加载



## first

add repository
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

add dependency
```
dependencies {
        implementation 'com.github.SineyCoder:RippleImage:1.0'
}
```

## use
```
final RippleImage image = findViewById(R.id.image);
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            private int i = 0;
            @Override
            public void run() {
                if(i < 100){
                    i++;
                    image.setMask(i+"");//set mask, loading....
                    h.postDelayed(this, 10);
                }else{
                    image.animation();//start ripple animation
                }
            }
        }, 1000);
```

<img width="500" src="https://img-blog.csdnimg.cn/20190221132058183.gif?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2E1NjgyODM5OTI=,size_16,color_FFFFFF,t_70"/>
