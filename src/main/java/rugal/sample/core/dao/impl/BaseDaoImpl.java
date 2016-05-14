package rugal.sample.core.dao.impl;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.NullableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import rugal.sample.core.dao.BaseDao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by liuchuan.gu on 15/7/3.
 */
@Repository
public class BaseDaoImpl<T> implements BaseDao<T>  {
    @Autowired
    public SessionFactory sessionFactory;

    public   Session getCurrSession(){
        return sessionFactory.getCurrentSession();
    }

    public <T> T get(Class<T> cls, Serializable id) {
        Session s = getCurrSession();
        T t = (T) s.get(cls, id);
        return t;
    }

    public <T> T getOneByHql(String hql) {
        Session s =  getCurrSession();
        Query query = s.createQuery(hql);
        List<T> list = query.setMaxResults(1).list();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public <T> List<T> getListByHql(String hql) {
        Session s =  getCurrSession();
        Query query = s.createQuery(hql);
        List<T> list = query.list();
        return list;
    }

    public Serializable save(T obj) {
        Session s =  getCurrSession();
        Serializable result =  s.save(obj);
        return result;
    }

    public boolean executeHql(String hql) {
        Session s =  getCurrSession();
        Query query = s.createQuery(hql);
        int result = query.executeUpdate();
        if (result > 0) {
            return true;
        }
        return false;
    }

    public <T> void delete(T obj) {
        Session s =  getCurrSession();
        s.delete(obj);
    }

    public List<T> find(String hql, Object... params) {
        Session s =  getCurrSession();
        Query query = s.createQuery(hql);
        // 为包含占位符的HQL语句设置参数
        for (int i = 0, len = params.length; i < len; i++) {
            query.setParameter(i, params[i]);
        }
       List<T> objList =  (List<T>) query.list();
        return objList;
    }

    public List<T> findByPage(String hql, int pageNo, int pageSize) {
        Session s =  getCurrSession();
       List<T> objList =  s.createQuery(hql)
                // 执行分页
                .setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize)
                .list();
        return objList;
    }

    public List<T> findByPage(String hql, int pageNo, int pageSize, Object... params) {
        return null;
    }

    public <T> void update(T obj) {
        Session s =  getCurrSession();
        s.update(obj);
    }

    public <T> List<T> createSQLQuery(String sql, T obj, Map<String, NullableType> type) {
        Session session =  getCurrSession();
        SQLQuery query = session.createSQLQuery(sql);
        if (type != null) {
            Iterator<String> it = type.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (type.get(key) != null) {
                    query.addScalar(key, type.get(key));
                } else {
                    query.addScalar(key);
                }
            }
        }
        List<T> objList = query.setResultTransformer(Transformers.aliasToBean(obj.getClass())).list();
        return objList;
    }
    public void executeSql(String sql) {
		 Session session =  getCurrSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.executeUpdate();
	}
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
