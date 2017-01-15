package com.conf;

import java.util.ArrayList;
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
    private String[] attrsName;
    private Map<String, Integer> map = new HashMap<>(); // key 成元名字,在attrArray中的index
    public CustomType(String TypeName, ArrayList<String> attrsType, ArrayList<String> attrsName) {
        int num = attrsType.size();
        if (num != attrsName.size())
            throw new AssertionError("attrsName.length must equal to attrsType.length");
        this.typeName = TypeName;
        this.attrsClass = new Class[num];
        this.attrsName = new String[num];
        for (int i = 0; i < num; i++) {
            switch (attrsType.get(i)) {
                case INT_STR:
                    attrsClass[i] = int.class;
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
            this.attrsName[i] = attrsName.get(i);
            this.map.put(attrsName.get(i), i);
        }
    }
    public Class getAttrClass(String att) {
        int index = map.get(att);
        return attrsClass[index];
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("struct ");
        sBuilder.append(typeName);
        sBuilder.append("{\n");
        for (int i = 0; i < this.attrsName.length; i++) {
            sBuilder.append("\t");
            sBuilder.append(attrsClass[i]);
            sBuilder.append("\t");
            sBuilder.append(attrsName[i]);
            sBuilder.append("\n");
        }
        sBuilder.append("}\n");
        return sBuilder.toString();
    }
}
