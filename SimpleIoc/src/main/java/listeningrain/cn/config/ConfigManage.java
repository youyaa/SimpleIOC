package listeningrain.cn.config;

import listeningrain.cn.model.Bean;
import listeningrain.cn.model.Property;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User:        sunqingfeng6
 * Date:        2019/3/19 15:45
 * Description: 解析xml文件，通过反射生成对象，通过内省注入属性
 */
public class ConfigManage {

    public  static Map getConfig(String path) throws Exception {
        //定义一个reader对象
        SAXReader reader = new SAXReader();

        //定义一个Map
        Map<String, Bean> map = new HashMap<>();

        //通过流去读XML文件
        InputStream in = ConfigManage.class.getClassLoader().getResourceAsStream(path);

        //返回一个文档对象
        Document docu = reader.read(in);

        Element rootelement = docu.getRootElement();
        List<Node> nodes = rootelement.selectNodes("bean");

        for (Node node : nodes) {
            Element e = (Element)node;

            //对bean下属性进行遍历，并封装
            Bean bean = new Bean();

            bean.setName(e.attributeValue("name"));
            bean.setClassName(e.attributeValue("class"));
            bean.setScope(e.attributeValue("scope"));


            //对bean下的子目录Property属性进行遍历
            List<Node> properties = node.selectNodes("property");

            List<Property> l = new ArrayList<>();
            for (Node property : properties) {
                Element e1 = (Element)property;
                Property p = new Property();

                p.setName(e1.attributeValue("name"));
                p.setValue(e1.attributeValue("value"));
                p.setRef(e1.attributeValue("ref"));

                l.add(p);
            }
            bean.setProperties(l);
            map.put(e.attributeValue("name"), bean);
        }

        return map;
    }


}
