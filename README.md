# EventBusDemo





## 单例模式 ##
确保某一个类只有一个实例,而且自行实例化并向整个系统提供这个实例.

### Singleton 模式的使用场景###

确保某个类有且只有一个对象场景,避免产生多个对象消耗过多的资源,或者某种类型的对象只应该有且只有一个,例如,创建一个对象需要消耗的资源过多,如果要访问IO和数据库等资源这时候就要考虑使用单例模式.


## 饿汉模式 ##
```java

	package com.softtanck;

	public class SingletonDemo {
		private static final SingletonDemo instance = new SingletonDemo();
	
		private SingletonDemo() {
		}
	
		public static SingletonDemo getInstance() {
			return instance;
		}
	}

```


## 懒汉模式 ##
```java

	package com.softtanck;
	
	public class SingletonDemo {
		private static SingletonDemo instance;
	
		private SingletonDemo() {
		}
	
		/**
		 * 同步
		 * 
		 * @return
		 */
		public static synchronized SingletonDemo getInstance() {
			if (null == instance)
				instance = new SingletonDemo();
			return instance;
		}
	}

```

- 懒汉模式优点:在使用懒汉模式中使用了同步,防止多线程并发的时候创建过多的实例,但是一旦对象已经被创建后,每一次都需要同步,造成同步没有必要的开销.
- 懒汉模式的优点:只有在使用单例的时候才会去实例化,在一定程度上节约了资源.


## Double CheckLock (DCL)单例 ##

DCL方式实现单例模式的优点是既能够在需要时候才初始化单例,又能够保证线程安全,且单例对象初始化后调用getInstance不进行同步锁.

```java
	package com.softtanck;
	
	public class SingletonDemo {
		private static SingletonDemo instance;
	
		private SingletonDemo() {
		}
	
		/**
		 * DCL
		 * 
		 * @return
		 */
		public static synchronized SingletonDemo getInstance() {
			if (null == instance) {
				synchronized (SingletonDemo.class) {
					if (null == instance)
						instance = new SingletonDemo();
				}
			}
			return instance;
		}
	}

```

在getInstance方法中,instance进行了2次判断为空的情况:

- 第一层判断主要是为了避免不必要的同步.
- 第二层判断主要是为了为Null的情况下创建实例.

### DCL优点 ###
资源利用率高,第一次执行getInstance时单例对象才会被实例化,效率高.

### DCL缺点 ###
第一次加载时反应稍慢,也是由于Java内存模型的原因偶尔会失败.在高并发环境下也有一定的缺陷,虽然发生概率很小.


## 静态内部类单例模式 ##

```java

	package com.softtanck;
	
	public class SingletonDemo {
	
		private SingletonDemo() {
		}
	
		public static SingletonDemo getInstance() {
	
			return SingletonHolder.instance;
		}
	
		/**
		 * 静态内部类
		 * 
		 * @author Tanck
		 *
		 */
		private static class SingletonHolder {
			private static final SingletonDemo instance = new SingletonDemo();
		}
	}

```

**当一次加载SINGLETONDEMO的时候并不会初始化INSTANCE,只有第一次调用SINGLETON的GETINSTANCE方法是才会导致INSTANCE被初始化,因此,第一次调用GETINSTANCE方法会导致虚拟机加载SINGLETONHOLDER类,这种方式不仅能够保证线程安全,也能够保证单例对象的唯一性,同时也延迟了单例的实例化,所以这种是推荐使用单例模式实现方式.**


## Android 源码中 ##
在ContextImpl类中有一块静态语句块,该语句块会在第一次加载该类时执行,并且只执行一次保证了实例的唯一性.

例如系统的一些服务:

```java
	registerService(LAYOUT_INFLATER_SERVICE,new ServiceFetcher(){
		public Object createService(ContextImpl ctx){
			return PolicyManager.makeNewLayoutInflater(ctx.getOuterContext());
		}
	});

```

创建服务后,会服务对象实例存在HasMap中,获取的时候是通过hasmap的Key获取.


```java
@Override
public Object getSystemService(String name){
	//根据Name来获取服务
	ServiceFetcher fetcher = SYSTEM_SERVICE_MAP.get(name);
	return fetcher == null ? null : fetcher.getService(this);
}

```

系统服务以单例形式存在,减少了资源消耗.
