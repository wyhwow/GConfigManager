package com.conf;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 约束条件
 * Created by chenmj on 2017/1/15.
 */
public class TabHead<T> {
    public static void main(String[] args) {

    }
    private final String tabName;
    // 数据类型
    private final Class dataClass;
    private final CustomType customType;
    private final RepeatType repeatType;

    // key类型
    private final KeyType keyType;

    // 约束条件
    private final boolean incr;
    private final boolean continuous;
    private final boolean uniq;
    private final String dependSheet;
    private final Range <T> range;
    private final boolean extendInterval;
    private final boolean binarySearchInterval;
    // 默认行为
    private final DefaultAction def_action;
    private Object defaultValue;

    private TabHead(Builder<T> builder) {
        this.tabName = builder.tabName;
        this.dataClass = builder.dataClass;
        this.customType = builder.customType;
        this.repeatType = builder.repeatType;

        this.keyType = builder.keyType;

        this.incr = builder.incr;
        this.continuous = builder.continuous;
        this.uniq = builder.uniq;
        this.dependSheet = builder.dependSheet;
        this.range = builder.range;
        this.extendInterval = builder.extendInterval;
        this.binarySearchInterval = builder.extendInterval;
        this.def_action = builder.def_action;
        this.defaultValue = builder.defaultValue;
    }


    public static final class Builder<T> {
        private String tabName;
        private Class dataClass;
        private CustomType customType;
        private RepeatType repeatType = RepeatType.SINGLE;
        private KeyType keyType = KeyType.NOT_KEY;
        private boolean incr = false;
        private boolean continuous = false;
        private boolean uniq = false;
        private String dependSheet = null;
        private Range<T> range = null;
        private boolean extendInterval = false;
        private boolean binarySearchInterval = false;
        private DefaultAction def_action = DefaultAction.MUST_EXIST;
        private Object defaultValue = null;

        // todo 测下每次创建实例的时候看Builder是不是重新创建
        private Builder(Class dataClass, String tabName) {
            this.tabName = tabName;
            this.dataClass = dataClass;
            this.repeatType = RepeatType.SINGLE;
        }
        private Builder(CustomType customType, String tabName) {
            this.tabName = tabName;
            this.dataClass = CustomType.class;
            this.customType = customType;
            this.repeatType = RepeatType.SINGLE;
        }
        private Builder setRepeatType(RepeatType repeatType) {
            this.repeatType = repeatType;
            return this;
        }

        public Builder setKeyType(KeyType keyType) {
            this.keyType = keyType;
            return this;

        }
        public Builder incr() {
            this.incr = true;
            if (this.dataClass != int.class && this.dataClass != double.class) {
                throw new IllegalArgumentException("Only int and double support incr");
            }
            return this;
        }
        public Builder continuous() {
            this.continuous = true;
            if (this.dataClass != int.class) {
                throw new IllegalArgumentException("Only int support continuous");
            }
            return this;
        }
        public Builder uniq() {
            this.uniq = true;
            return this;
        }
        public Builder setDependSheet(String dependSheet) {
            this.dependSheet = dependSheet;
            return this;
        }
        public Builder setRange(T min, T max, boolean eqMin, boolean eqMax) {
            if (this.dataClass != int.class && this.dataClass != double.class) {
                throw new IllegalArgumentException("Only int and double support range");
            }
            this.range = new Range<>(min, max, eqMin, eqMax);
            return this;
        }
        public Builder extendInterval() {
            if (this.dataClass != int.class) {
                throw new IllegalArgumentException("Only int support extendInterval");
            }
            if (this.binarySearchInterval) {
                throw new IllegalArgumentException("binarySearchInterval is conflict with extendInterval!");
            }
            this.extendInterval = true;
            return this;
        }
        public Builder binarySearchInterval() {
            if (this.dataClass != int.class && this.dataClass != double.class) {
                throw new IllegalArgumentException("Only int and double support binarySearchInterval");
            }
            if (this.extendInterval) {
                throw new IllegalArgumentException("binarySearchInterval is conflict with extendInterval!");
            }
            this.binarySearchInterval = true;
            return this;
        }
        public Builder nullable() {
            if (this.def_action == DefaultAction.HAS_DEF_VALUE) {
                throw new IllegalArgumentException("nullable is conflict with default!");
            }
            this.def_action = DefaultAction.NULLABLE;
            return this;
        }
        public Builder setDefaultValue(Object defaultValue) {
            if (this.def_action == DefaultAction.NULLABLE) {
                throw new IllegalArgumentException("default is conflict with nullable!");
            }
            if (this.repeatType == RepeatType.DICT || this.repeatType == RepeatType.LIST) {
                throw new IllegalArgumentException("dict and list are not support defaultvalue");
            }
            this.def_action = DefaultAction.HAS_DEF_VALUE;
            this.defaultValue = defaultValue;
            return this;
        }
        public Builder setEmptyAble() {
            if (this.repeatType == RepeatType.SINGLE) {
                throw new IllegalArgumentException("only dict or list support emptyable!");
            }
            this.def_action = DefaultAction.EMPTYABLE;
            return this;
        }
        public TabHead<T> build() {
            if (ArrayUtils.contains(KEY_TYPE_LS, this.keyType) || this.keyType == KeyType.INDEX) {
                if (this.dataClass == CustomType.class)
                    throw new IllegalArgumentException("key/index can only be basic types!");
            }
            if (this.dataClass == double.class && (this.keyType != KeyType.KEY || !this.binarySearchInterval)) {
                throw new IllegalArgumentException("double is only support key with binarySearchInterval!");
            }
            if (ArrayUtils.contains(KEY_ALIAS_TYPE_LS, this.keyType) && this.dataClass != String.class) {
                throw new IllegalArgumentException("key_alias's type must be string");
            }
            return new TabHead<>(this);
        }
    }
    private static final KeyType[] KEY_TYPE_LS =  new KeyType[]{KeyType.KEY, KeyType.KEY1,
            KeyType.KEY2, KeyType.KEY3, KeyType.KEY4};
    private static final KeyType[] KEY_ALIAS_TYPE_LS =  new KeyType[]{KeyType.KEY_ALIAS, KeyType.KEY_ALIAS1,
            KeyType.KEY_ALIAS2, KeyType.KEY_ALIAS3, KeyType.KEY_ALIAS4};

    public static class Range<T> {
        private boolean eqMin = false;
        private boolean eqMax = false;
        private T min;
        private T max;
        Range(T min, T max, boolean eqMin, boolean eqMax) throws IllegalArgumentException {
            if (min instanceof Integer) {   // 只有int型支持闭区间
                this.eqMin = eqMin;
                this.eqMax = eqMax;
            } else if (!(min instanceof Double)) {
                throw new IllegalArgumentException("Only int and double support \"range\"!");
            }
            this.min = min;
            this.max = max;
        }

        public boolean canEqMin() {
            return eqMin;
        }

        public boolean canEqMax() {
            return eqMax;
        }

        public T getMin() {
            return min;
        }

        public T getMax() {
            return max;
        }
    }
}

