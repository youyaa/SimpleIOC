package listeningrain.cn.test;


import listeningrain.cn.innerClass.InnerClassTest;

/**
 * @Author: listeningrain
 * @Date: 2019-01-23 21:33
 * @Description:
 */
public class OutterClass {
    private String name;
    private Integer age;

    // 省略getter和setter方法

    public OutterClass(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public void print(){
        System.out.println("外部类的print方法");
    }

     class InnerClass{
        public void print(){
            System.out.println("外部类name："+name+"  外部类age: "+age);
        }

        public OutterClass getOutterClass(){
            return OutterClass.this; //返回外部类的引用 .this
        }
    }

    /**
     * 静态内部类
     * @param
     */
    static class InnerClass2{
        private String name;
        public void staticprint(){
            System.out.println("静态内部类的print方法");
        }
    }

    public static void main(String[] args){

       /* OutterClass outterClass = new OutterClass("阿峰",23);

        //生成内部类的引用 .new
        OutterClass.InnerClass innerClass = outterClass.new InnerClass();

        innerClass.print();*/

        /**
         * 在外围类的作用范围外，静态内部类对象的生成不依赖外部类对象
         */
        OutterClass.InnerClass2 innerClass2 = new OutterClass.InnerClass2();
        innerClass2.staticprint();

       /* InnerClassTest outterClass = innerClass.getOutterClass();

        outterClass.print();*/

    }
}
