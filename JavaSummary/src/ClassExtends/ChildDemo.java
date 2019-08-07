package ClassExtends;

public class ChildDemo extends FatherDemo {
    //当子类中存在和父类同名属性,父类属性会隐藏起来,在多态的情况下属性被调用时会激活父类属性子类属性隐藏起来,
    // 而方法不会隐藏,一旦被重写,只能使用super来在子类调用
    public String name = "child";


    public void getName() {
        System.out.println(name);
    }

    public void getFatherName() {
        System.out.println(super.name);
        // error 父类对象中的私有属性和方法，子类是无法访问到的
        //super.printname();
    }

    public void getAge() {
        super.getAge();

    }
    public ChildDemo(int age) {
        super(age);
    }
    public static void main(String[] args) {
        ChildDemo child=new ChildDemo(13);
        //FatherDemo类型的引用，指向新建的ChildDemo类型的对象
        FatherDemo child1 = new ChildDemo(44);
        System.out.println("child class is :"+child.getClass().getName());
        System.out.println("child1 class is :"+child1.getClass().getName());
        child.getFatherName();
        child.getName();
        child.getAge();
        child1.getAge();
        System.out.println(child1.name);
    }

}
