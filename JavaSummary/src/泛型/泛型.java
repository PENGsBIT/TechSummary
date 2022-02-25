package 泛型;

/**
 * @Program: JavaSummary
 * @Author: zhoupengcheng03
 * @Package: 泛型
 * @ClassName: 泛型
 * @Description:
 * @Create: 2022-02-15 11:35
 **/
public class 泛型 {
}
//BasicHolder<T>泛型类的类型参数并没有什么边界，在继承它的时候，你本可以class A extends BasicHolder<B> {}这样普普通通的用。
//但class Subtype extends BasicHolder<Subtype> {}这样用，就构成自限定了。从定义上来说，它继承的父类的类型参数是它自己。
// 从使用上来说，Subtype对象本身的类型是Subtype，且Subtype对象继承而来的成员(element)、方法的形参(set方法)、方
// 法的返回值(get方法)也是Subtype了（这就是自限定的重要作用）。这样Subtype对象就只允许和Subtype对象（而不是别的类型的对象）交互了。
class BasicHolder<T> {
    T element;
    void set(T arg) { element = arg; }
    T get() { return element; }
    void f() {
        System.out.println(element.getClass().getSimpleName());
    }
}

class Subtype extends BasicHolder<Subtype> {}

class CRGWithBasicHolder {
    public static void main(String[] args) {
        Subtype st1 = new Subtype(), st2 = new Subtype(), st3 = new Subtype();
        st1.set(st2);
        st2.set(st3);
        Subtype st4 = st1.get().get();
        st1.f();
    }
}
