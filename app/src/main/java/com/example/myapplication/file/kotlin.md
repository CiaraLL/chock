#

## 内联函数和高阶函数

内联函数：编译时把调用代码插入到函数中，避免方法调用的开销。          
高阶函数：接受一个或多个函数类型的参数，并/或返回一个函数类型的值.

我们常用的apply、run、let这些其实就是一个内联高阶函数。

    // apply 
    public inline fun <T> T.apply(block: T.() -> Unit): T { 
        block() 
        return this 
    }
    // run
    public inline fun <T, R> T.run(block: T.() -> R): R {
        return block()
    }
    // let
    public inline fun <T, R> T.let(block: (T) -> R): R {
        return block(this) 
    }