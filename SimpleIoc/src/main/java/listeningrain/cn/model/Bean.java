package listeningrain.cn.model;

import java.util.List;

/**
 * User:        sunqingfeng6
 * Date:        2019/3/19 15:41
 * Description: xml中bean标签的封装类
 */
public class Bean {

    private String name;
    private String ClassName;
    private String scope;
    private List<Property> properties;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}
