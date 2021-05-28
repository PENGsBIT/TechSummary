package Class继承;

public class FatherDemo {
    public String name="father";
    private String ID = "";
    private int age;

    public void getAge() {
        System.out.println(this.age);
    }
    public FatherDemo(int age) {
        this.age = age;
    }
    private void printname() {
        System.out.println(name);
    }

}
