package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;
import org.greenrobot.greendao.generator.ToMany;

public class ExampleDaoGenerator {
    //生成类的包名
   private static final String DEFAULT_PACKAGE_NAME = "cn.mobilizer.meetingplus.dao";
   public  static void  main(String[] args) throws  Exception{
       // 正如你所见的，你创建了一个用于添加实体（Entity）的模式（Schema）对象。
       // 两个参数分别代表：数据库版本号与自动生成代码的包路径。
       Schema schema = new Schema(1, "me.itangqi.greendao");
       //当然，如果你愿意，你也可以分别指定生成的 Bean 与 DAO 类所在的目录，只要如下所示：
       // Schema schema = new Schema(1, "me.itangqi.bean");
       //schema.setDefaultJavaPackageDao("me.itangqi.dao");
       // 模式（Schema）同时也拥有两个默认的 flags，分别用来标示 entity 是否是 activie 以及是否使用 keep sections。
       // schema2.enableActiveEntitiesByDefault();
       // schema2.enableKeepSectionsByDefault();
       // 一旦你拥有了一个 Schema 对象后，你便可以使用它添加实体（Entities）了。
       addNote(schema);
       addCustomerOrder(schema);
       // 最后我们将使用 DAOGenerator 类的 generateAll() 方法自动生成代码，此处你需要根据自己的情况更改输出目录（既之前创建的 java-gen)。
       // 其实，输出目录的路径可以在 build.gradle 中设置，有兴趣的朋友可以自行搜索，这里就不再详解。
       new DaoGenerator().generateAll(schema,"D:/WatchPlus/greendao/src/main/java-gen");
   }

   public static void  addNote(Schema schema){
       // 一个实体（类）就关联到数据库中的一张表，此处表名为「Note」（既类名）
       Entity note = schema.addEntity("Note");
       // 你也可以重新给表命名
       note.setTableName("NOTE");
       // greenDAO 会自动根据实体类的属性值来创建表字段，并赋予默认值
       // 接下来你便可以设置表中的字段：
       note.addIdProperty();
       note.addStringProperty("text").notNull();
       // 与在 Java 中使用驼峰命名法不同，默认数据库中的命名是使用大写和下划线来分割单词的。
       // For example, a property called “creationDate” will become a database column “CREATION_DATE”.
       note.addStringProperty("content");
       note.addDateProperty("date");
   }
   private static  void   addCustomerOrder(Schema schema){
       Entity customer = schema.addEntity("Customer");
       customer.addIdProperty();
       customer.addStringProperty("name").notNull();
       Entity order = schema.addEntity("Order");
       order.setTableName("ORDERS");
       order.addIdProperty();
       Property orderDate = order.addDateProperty("date").getProperty();
       Property customerId = order.addLongProperty("customerId").notNull().getProperty();
       //将自己指定给一个customer
       order.addToOne(customer,customerId);
       //一添加多
       ToMany customerToOrders = customer.addToMany(order,customerId);
       customerToOrders.setName("orders");
       customerToOrders.orderAsc(orderDate);
    }



}
