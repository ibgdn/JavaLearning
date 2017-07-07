import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/7/7.
 *  创建一个解析类 ParseLikeHibernateAnnotation ，获取 Human 类上配置的注解信息。
    思路如下：
     1. 首先获取 Human.class类对象
     2. 判断本类是否进行了MyEntity 注解
     3. 获取注解 MyTable
     4. 遍历所有的方法，如果某个方法有MyId注解，那么就记录为主键方法 primaryKeyMethod
     5. 把主键方法的自增长策略注解 MyGeneratedValue 和对应的字段注解 MyColumn 取出来，并打印
     6. 遍历所有非主键方法，并且有 MyColumn 注解的方法，打印属性名称和字段名称的对应关系。
 */
public class ParseLikeHibernateAnnotation {
    public static void main(String[] args) {
        parse();
    }

    private static void parse() {
        Class<Human> clazz = Human.class;

        MyEntity myEntity = clazz.getAnnotation(MyEntity.class);

        if(myEntity == null){
            System.out.println("Human 不是实体类");
        }else{
            System.out.println("Human 是实体类");

            MyTable myTable = clazz.getAnnotation(MyTable.class);
            System.out.println("实体类对应的表明为：" + myTable.name());

            Method[] methods = clazz.getMethods();
            Method primaryKeyMethod = null;
            for (Method method : methods){
                MyId myId = method.getAnnotation(MyId.class);
                if(myId != null){
                    primaryKeyMethod = method;
                    break;
                }
            }

            if(primaryKeyMethod != null){
                System.out.println("主键：" + primaryKeyMethod.getName());

                MyGeneratedValue myGeneratedValue = primaryKeyMethod.getAnnotation(MyGeneratedValue.class);
                System.out.println(myGeneratedValue + " 的自增长策略是：" + myGeneratedValue.strategy());

                MyColumn myColumn = primaryKeyMethod.getAnnotation(MyColumn.class);
                System.out.println("对应数据库中的字段是：" + myColumn.value());
            }

            System.out.println("其他非主键属性对应数据库的字段是：");
            for(Method method : methods){
                if(primaryKeyMethod == method){
                    continue;
                }
                MyColumn myColumn = method.getAnnotation(MyColumn.class);
                if(myColumn == null){
                    continue;
                }
                System.out.format("属性：%s\t对应的数据库字段是：%s%n", method2Attribute(method.getName()),myColumn.value());
            }
        }
    }

    private static String method2Attribute(String methodName){
        String result = null;
        if(methodName.startsWith("get")){
            result = methodName.replaceFirst("get","");
        }else if(methodName.startsWith("is")) {
            result = methodName.replaceFirst("is", "");
        }

        if(result.length() <= 1){
            return result.toLowerCase();
        }else{
            return result.substring(0,1).toLowerCase() + result.substring(1);
        }
    }
}
