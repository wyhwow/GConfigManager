package com.conf;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义类型
 * Created by chenmj on 2017/1/15.
 */
public class CustomType {
    /*int、 double、string和bool*/
    private static final String INT_STR = "int";
    private static final String DOUBLE_STR = "double";
    private static final String STRING_STR = "string";
    private static final String BOOL_STR = "bool";

    private final String typeName;                            // 自定义类名
    private Class[] attrsClass;
    private Map<String, Integer> map = new HashMap<>(); // key 成元名字,在attrArray中的index
    public CustomType(String TypeName, String[] attrsType, String[] attrsName) throws ClassNotFoundException {
        if (attrsType.length != attrsName.length)
            throw new AssertionError("attrsName.length must equal to attrsType.length");

        this.typeName = TypeName;
        this.attrsClass = new Class[attrsType.length];
        for (int i = 0; i < attrsType.length; i++) {
            switch (attrsType[i]) {
                case INT_STR:
                    attrsClass[i] = int.class;
                    map.put(attrsName[i], i);
                    break;
                case DOUBLE_STR:
                    attrsClass[i] = double.class;
                    break;
                case STRING_STR:
                    attrsClass[i] = String.class;
                    break;
                case BOOL_STR:
                    attrsClass[i] = boolean.class;
                    break;
            }
            map.put(attrsName[i], i);
        }
    }
    public Class getAttrClass(String att) {
        int index = map.get(att);
        return attrsClass[index];
    }
}
