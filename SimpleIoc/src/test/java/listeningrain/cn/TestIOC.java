package listeningrain.cn;

import listeningrain.cn.config.BeansHolder;
import listeningrain.cn.test.model.People;
import listeningrain.cn.test.model.Student;

/**
 * Author: listeningrain
 * Date: 2019-03-25 20:46
 * Description:
 */
public class TestIOC{

    public static void main(String[] args)throws Exception{
        BeansHolder holder = new BeansHolder("beans.xml");
        Student student = (Student) holder.getBean("student");
        System.out.println(student);

        People people = (People) holder.getBean("people");
        System.out.println(people);
    }
}
