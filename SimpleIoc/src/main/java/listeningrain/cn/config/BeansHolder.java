package listeningrain.cn.config;

import listeningrain.cn.config.ConfigManage;
import listeningrain.cn.model.Bean;
import listeningrain.cn.model.Property;
import org.apache.commons.beanutils.BeanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * User:        sunqingfeng6
 * Date:        2019/3/20 15:26
 * Description:  类的包裹类
 */
public class BeansHolder {

    private Map<String, Bean> map;
    //存放对象的上下文
    private Map<String, Object> context;

    //构造函数，初始化上下文
    public BeansHolder(String path) throws Exception {
        // TODO Auto-generated constructor stub
        //得到封装数据的map
        map = ConfigManage.getConfig(path);
        //初始化上下文
        context = new HashMap<String, Object>();
        //对map进行遍历
        for (Map.Entry<String, Bean> entry : map.entrySet()) {
            String beanName = entry.getKey();
            Bean obj = entry.getValue();

            //当容器中不存在这个对象，且scope为单例时，生成对象并放入上下文中
            if (context.get(beanName) == null && "singleton".equals(obj.getScope())) {
                Object bean = createBean(obj);
                context.put(beanName, bean);
            }
        }

    }



    private Object createBean(Bean obj) {
        // TODO Auto-generated method stub
        Object newObj = null;
        Class clazz = null;
        //加载类，获得字节码
        try {
            clazz = Class.forName(obj.getClassName());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(obj.getClassName() + " 不存在");
        }
        //通过反射生成对象
        try {
            newObj = clazz.newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("没有提供无参的构造函数");
        }

        //利用java的内省机制对相应的属性进行注入
        if (obj.getProperties() != null) {
            for (Property property : obj.getProperties()) {
                String name = property.getName();
                String value = property.getValue();
                String ref = property.getRef();

                //利用beanutils帮我们注入属性,但是要理解java的内省机制
                if (value != null) {
                    //构造beanutils需要的map集合
                    Map<String, String> paramMap = new HashMap<>();
                    paramMap.put(name, value);
                    try {
                        BeanUtils.populate(newObj, paramMap);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        throw new RuntimeException(name + " 属性没有相应的set方法");
                    }
                }

                //如果依赖不为空，则需要先生成依赖的对象
                if (ref != null) {
                    Object ext = context.get(ref);
                    if (ext == null) {
                        ext = createBean(map.get(ref));

                        if (map.get(ref).getScope().equals("singleton")) {
                            context.put(map.get(ref).getName(), ext);
                        }
                    }
                    try {
                        BeanUtils.setProperty(newObj, name, ext);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        throw new RuntimeException();
                    }
                }
            }

        }

        return newObj;

    }

    public Object getBean(String className){
        if(null != context){
            return context.get(className);
        }
        return null;
    }
}
