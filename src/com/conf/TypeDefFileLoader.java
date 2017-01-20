package com.conf;


import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by chenmj on 2017/1/16.
 */
public class TypeDefFileLoader {
    private static HashMap<String, CustomType> customTypeMap = new HashMap<>();

    TypeDefFileLoader(String fileName) throws IOException {
        FileInputStream fin = new FileInputStream(fileName);
        ANTLRInputStream input = new ANTLRInputStream(fin);
        TypeDefLexer typeDefLexer = new TypeDefLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(typeDefLexer);
        TypeDefParser parser = new TypeDefParser(tokens);
        parser.setErrorHandler(new BailErrorStrategy());    // 用BailErrorStrategy这种策略,遇到错误自己报出来,不恢复
        ParseTree tree = parser.type_defs();
        ParseTreeWalker walker = new ParseTreeWalker();
        PropertyFileLoader loader = new PropertyFileLoader();
        walker.walk(loader, tree);
    }

    public HashMap<String, CustomType> getCustomTypeMap() {
        return customTypeMap;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, CustomType> entry : customTypeMap.entrySet()) {
            stringBuilder.append(entry.getValue());
        }
        return stringBuilder.toString();
    }

    public static class PropertyFileLoader extends TypeDefBaseListener {
        private static String typeName;
        private static ArrayList<String> attrsName;
        private static ArrayList<String> attrsType;

        @Override
        public void enterType_def(TypeDefParser.Type_defContext ctx) {
            typeName = ctx.ID().getText();
            attrsName = new ArrayList<>();
            attrsType = new ArrayList<>();
        }

        @Override
        public void enterAttr(TypeDefParser.AttrContext ctx) {
            attrsType.add(ctx.type().getText());
            attrsName.add(ctx.ID().getText());
        }

        @Override
        public void exitType_def(TypeDefParser.Type_defContext ctx) {
            CustomType customType = new CustomType(typeName, attrsType, attrsName);
            if (customTypeMap.containsKey(typeName))
                throw new AssertionError("类型" + typeName + "重复定义");
            customTypeMap.put(typeName, customType);
        }
    }
    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        System.out.println(fileName);
        System.out.println(new TypeDefFileLoader(fileName));
    }
}
