package rugal.sample.core.dao;

import org.hibernate.SessionFactory;
import org.hibernate.type.NullableType;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by liuchuan.gu on 15/7/3.
 */
public interface BaseDao<T>{
    /**
     * 根据ID获取数据库对象
     *
     * @param cls
     * @param id
     * @return
     */
    public <T> T get(Class<T> cls, Serializable id);

    /**
     * 根据HQL语句，获取数据库对象集合
     *
     * @param hql
     * @return
     */
    public <T> List<T> getListByHql(String hql);

    /**
     * 保存数据库对象
     *
     * @param obj
     */
    public Serializable save(T obj);

    /**
     * 执行HQL语句，一般用于更新或删除操作
     *
     * @param hql
     * @return
     */
    public boolean executeHql(String hql);

    /**
     * 更新数据库对象
     *
     * @param obj
     */
    public <T> void update(T obj);

    /**
     * 根据hql,获取一条数据库对象集合
     *
     * @param hql
     * @return
     */
    public <T> T getOneByHql(String hql);

    /**
     * 删除一个数据对象
     *
     * @param obj
     */
    public <T> void delete(T obj);

    /**
     * 根据带占位符参数HQL语句查询实体
     * @param hql
     * @param params
     * @return
     */
    public List<T> find(String hql , Object... params);

    /**
     *
     * @param hql
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<T> findByPage(String hql,int pageNo, int pageSize);
    /**
    * 使用hql 语句进行分页查询操作
    * @param hql 需要查询的hql语句
    * @param params 如果hql带占位符参数，params用于传入占位符参数
    * @param pageNo 查询第pageNo页的记录
    * @param pageSize 每页需要显示的记录数
    * @return 当前页的所有记录
    */
    public List<T> findByPage(String hql , int pageNo, int pageSize, Object... params);
    /**
     * 把数据映射到非持久化的对象中,支持多表查询,
     * @param sql
     * @param obj 要把数据映射到的对象中
     * @return
     */
    public <T> List<T> createSQLQuery(String sql,T obj,Map<String,NullableType> type);
    
    public void executeSql(String sql);

    public SessionFactory getSessionFactory();
    
    
}
