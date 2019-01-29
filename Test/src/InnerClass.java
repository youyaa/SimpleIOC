public class InnerClass {

    public static void main(String[] args) {

        String name = "haha";
        ;

    }


    public void test(final String age) {

        new Runnable(){
            @Override
            public void run() {
                System.out.println(age);
            }
        };

    }
}
