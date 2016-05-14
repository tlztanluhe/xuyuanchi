package rugal.sample.common;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.NullableType;

import org.springframework.util.StringUtils;

/**
 * 数据库分页查询(用于SQL)<br>
 * 示例：<br>
 * Page<User> page = new Page<User>("from Story s where s.id < 23", 4);<br>
 * List<User> list = page.firstPage();<br>
 * List<User> list = page.prePage();<br>
 * List<User> list = page.nextPage();<br>
 * List<User> list = page.lastPage();<br>
 * List<User> list = page.jumpToPage("firstPage");<br>
 * List<User> list = page.jumpToPage("prePage");<br>
 * List<User> list = page.jumpToPage("nextPage");<br>
 * List<User> list = page.jumpToPage("lastPage");<br>
 * List<User> list = page.jumpToPage(18);<br>
 * List<User> list = page.jumpToPage("25");<br>
 * List<User> list = page.getCurrentPage();<br>
 * int currentPageNumber = page.getCurrentPageNumber();<br>
 * int totalPage = page.getTotalPage();
 * @author
 *
 */
public class SQLPage<T>
{
    private String sql = "";				// sql 查询语句
    private int totalRow = 0;				// 总记录数
    private int totalPage = 0;				// 总页数
    private int pageSize = 0;				// 每页多少条记录
    private int currentPageNumber = 0;		// 当前页号，当前页号为 0 时表时没有记录
    private List<T> currentPage = null;		// 当前页
    private SessionFactory sessionFactory;
    private T obj=null;
    private Map<String,NullableType> type =null;



    public SQLPage() {}
    /**
     * 构造并初始化<br>
     * 示例：<br>
     * new Page("select * from story s where s.id < 23 ", 4);
     * @param sql 查询语句
     * @param pageSize 每一页记录数
     */
    public SQLPage(String sql, int pageSize, SessionFactory sessionFactory) {
        this.sql = sql;
        this.pageSize = pageSize;
        this.sessionFactory = sessionFactory;
        calculatePageNumber();
    }

    /**
     *
     * @param sql
     * @param countsql
     * @param pageSize
     * @param obj
     * @param type
     */
    public SQLPage(String sql, String countsql, int pageSize, T obj, Map<String, NullableType> type){
        this.type=type;
        this.obj=obj;
        this.sql=sql;
        if(StringUtils.isEmpty(countsql))
            countsql = sql;
        calculatePageNumber(sql,countsql,pageSize);
    }
    /**
     * 按照pageSize计算总页数.如果初始化的sql中含有group by 则不能用此方法。
     * 示例：<br>
     */
    @SuppressWarnings("rawtypes")
    public void calculatePageNumber() {
        Session session = null;
        String tempSql="select count(*) "+sql.substring(fromPosition(sql));
        try {
            session = sessionFactory.getCurrentSession();
            List list  = session.createSQLQuery(tempSql).list();
            this.totalRow = Integer.parseInt((list==null || list.size()==0)?"0":list.get(0).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.totalPage = totalRow / pageSize;
        if (this.totalRow % this.pageSize != 0) {
            this.totalPage++;
        }
    }
    private int fromPosition(String hql){
        hql = hql.toLowerCase();
        return hql.indexOf("from");
    }
    /**
     * 构造并初始化<br>
     * @param sql 查询语句
     * @param countSql 用于计算总行数的查询语句.
     */
    @SuppressWarnings("rawtypes")
    public void calculatePageNumber(String sql,String countSql,int pageSize) {
        this.sql = sql;
        this.pageSize = pageSize;
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            if(countSql.equals(sql)){
                countSql = "select count(*) "+sql.substring(fromPosition(sql));
            }
            List list =  session.createSQLQuery(countSql).list();
            this.totalRow=Integer.parseInt((list==null || list.size()==0)?"0":list.get(0).toString());
        } catch (Exception e) {
        }
        totalPage = totalRow / pageSize;
        if (totalRow % pageSize != 0) {
            totalPage++;
        }
    }
    /**
     * 页面跳转
     * @param targetPageNumber 目标页号
     * @return 目标页列表
     */
    @SuppressWarnings("unchecked")
    public List<T> jumpToPage(int targetPageNumber) {
        if (totalPage == 0) {							// 总页数为0则返回null
            return null;
        }
        if (targetPageNumber > totalPage) { 			// 要跳去的页号大于总页数
            currentPageNumber = totalPage;
        } else if (targetPageNumber < 1) {				// 要跳去的页号小于 1
            currentPageNumber = 1;
        } else {
            currentPageNumber = targetPageNumber;
        }
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            SQLQuery sqlQuery =  session.createSQLQuery(sql);
            sqlQuery.setFirstResult(pageSize * (currentPageNumber - 1)).setMaxResults(pageSize);
            if(type!=null){
                Iterator<String> it = type.keySet().iterator();
                while(it.hasNext()){
                    String key =  it.next();
                    if(type.get(key)!=null){
                        sqlQuery.addScalar(key,type.get(key));
                    }else{
                        sqlQuery.addScalar(key);
                    }
                }
            }
            if(obj!=null){
                currentPage = sqlQuery.setResultTransformer(Transformers.aliasToBean(obj.getClass())).list();
            }else {
                currentPage = sqlQuery.list();
            }
        } catch (Exception e) {
            currentPage = null;
        } finally {
            session.close();
        }
        return currentPage;
    }


    /**
     * 页面跳转
     * @param targetPageNumber 取值可以为"firstPage", "prePage", "nextPage", "lastPage", "1", "2", "3", "4", "5", ......, "n"
     * @return 目标页列表
     */
    public List<T> jumpToPage(String targetPageNumber) {
        if ("firstPage".equals(targetPageNumber))
            currentPage = jumpToPage(1);
        else if ("prePage".equals(targetPageNumber))
            currentPage = jumpToPage(this.currentPageNumber - 1);
        else if ("nextPage".equals(targetPageNumber))
            currentPage = jumpToPage(this.currentPageNumber + 1);
        else if ("lastPage".equals(targetPageNumber))
            currentPage = jumpToPage(this.totalPage);
        else {
            int target = currentPageNumber;
            try {
                target = Integer.parseInt(targetPageNumber);
            } catch (Exception e) {
                target=1;
            } finally {
                currentPage = jumpToPage(target);
            }
        }
        return currentPage;
    }

    /**
     * 跳转到第一页
     * @return 第一页列表
     */
    public List<T> firstPage() {
        return jumpToPage(1);
    }

    /**
     * 跳转到上一页
     * @return 上一页列表
     */
    public List<T> prePage() {
        return jumpToPage(currentPageNumber - 1);
    }

    /**
     * 跳转到下一页
     * @return 下一页列表
     */
    public List<T> nextPage() {
        return jumpToPage(currentPageNumber + 1);
    }

    /**
     * 跳转到最后一页
     * @return 最后一页列表
     */
    public List<T> lastPage() {
        return jumpToPage(totalPage);
    }

    /**
     * 获得总页数
     * @return 总页数
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * 获得总记录数
     * @return 总记录数
     */
    public int getTotalRow() {
        return totalRow;
    }

    /**
     * 获得当前页号
     * @return 当前页号
     */
    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    /**
     * 获得当前页数据列表
     * @return 当前页数据列表
     */
    public List<T> getCurrentPage() {
        return currentPage;
    }

    /**
     * 获得hql查询语句
     * @return 当前页数据列表
     */
    public String getSql() {
        return sql;
    }
}



