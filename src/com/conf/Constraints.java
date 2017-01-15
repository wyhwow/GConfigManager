package com.conf;

/**
 * 约束条件
 * Created by chenmj on 2017/1/15.
 */
public class Constraints <T> {
    public static void main(String[] args) {

    }
    // 数据类型
    private final Class dataClass;
    private final CustomType customType;
    private final Enum<RepeatType> repeatType;

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

    // 如果不配置nullable和default的话,就不允许不配置
    private final DefaultType def;
    private T defaultValue;

    private Constraints(Builder<T> builder) {
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
        this.def = builder.def;
        this.defaultValue = builder.defaultValue;
    }


    public static final class Builder<T> {
        private Class dataClass;
        private CustomType customType;
        private Enum<RepeatType> repeatType = RepeatType.SINGLE;
        private KeyType keyType = KeyType.NOT_KEY;
        private boolean incr = false;
        private boolean continuous = false;
        private boolean uniq = false;
        private String dependSheet = null;
        private Range<T> range = null;
        private boolean extendInterval = false;
        private boolean binarySearchInterval = false;
        private DefaultType def = DefaultType.CANNOT_EMPTY;
        private T defaultValue = null;

        // todo 测下每次创建实例的时候看Builder是不是重新创建
        private Builder(Class dataClass) {
            this.dataClass = dataClass;
        }
        private Builder(CustomType customType) {
            this.dataClass = CustomType.class;
            this.customType = customType;
        }

        public Builder setKeyType(KeyType keyType) {
            this.keyType = keyType;
            return this;

        }
        public Builder incr() {
            this.incr = true;
            return this;
        }
        public Builder continuous() {
            this.continuous = true;
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
        public Builder extendInterval() {
            if (this.binarySearchInterval) {
                throw new IllegalArgumentException("binarySearchInterval is conflict with extendInterval!");
            }
            this.extendInterval = true;
            return this;
        }
        public Builder binarySearchInterval() {
            if (this.extendInterval) {
                throw new IllegalArgumentException("binarySearchInterval is conflict with extendInterval!");
            }
            this.binarySearchInterval = true;
            return this;
        }
        public Builder nullable() {
            if (this.def == DefaultType.DEFAULT) {
                throw new IllegalArgumentException("nullable is conflict with default!");
            }
            this.def = DefaultType.NULLABLE;
            return this;
        }
        public Builder setDefaultValue(T defaultValue) {
            if (this.def == DefaultType.DEFAULT) {
                throw new IllegalArgumentException("default is conflict with nullable!");
            }
            this.def = DefaultType.DEFAULT;
            this.defaultValue = defaultValue;
            return this;
        }
        public Builder setRange(Range<T> range) {
            this.range = range;
            return this;
        }
        public Constraints<T> build() {
            // todo:加检查
            return new Constraints<T>(this);
        }
    }
}

