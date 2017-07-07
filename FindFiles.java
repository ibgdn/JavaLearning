    /** 采用注解的方法，根据后缀名查找文件

        为了紧凑起见，把注解作为内部类的形式放在一个文件里。
        1. 注解FileTypes，其value()返回一个FileType数组
        2. 注解FileType，其@Repeatable的值采用FileTypes
        3. 运用注解：在work方法上重复使用多次@FileType注解
        4. 解析注解： 在work方法内，通过反射获取到本方法上的FileType类型的注解数组，然后遍历本数组 */

public class FindFiles{
    public static void main(String[] args){
        new FindFiles().work();
    }

    @FileType(".java")
    @FileType(".js")
    public void work(){
        try{
            FileType[] fileTypes = this.getClass().getMethod("work").getAnnotationsByType(FileType.class);
            System.out.println("将从如下后缀名的文件中查找文件内容：");
            for(FileType fileType : fileTypes){
                System.out.println(fileType.value());
            }
            System.out.println("查找完成");
        }catch(NoSuchMethodException | SecurityException e){
            e.printStackTrace();
        }
    }

    @Target(METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FileTypes{
        FileTpye[] value();
    }

    @Target(METHOD)
    @Rethetion(RetentionPolicy.RUNTIME)
    @Repeatable(FileTypes.class)
    public @interface FileType{
        String value();
    }
}
