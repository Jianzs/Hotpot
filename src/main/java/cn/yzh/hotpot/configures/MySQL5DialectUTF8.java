package cn.yzh.hotpot.configures;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * 覆写MySQL5InnoDBDialect 的getTableTypeString() 方法
 */
public class MySQL5DialectUTF8 extends MySQL5InnoDBDialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}