package rugal.sample.common;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by liuchuan.gu on 15/7/16.
 */
public class Page<T> {

    private SessionFactory sessionFactory;
    private String hql = "";				// hql 查询语句
    private int totalRow = 0;				// 总记录数
    private int totalPage = 0;				// 总页数
    private int pageSize = 0;				// 每页多少条记录
    private int currentPageNumber = 0;		// 当前页号，当前页号为 0 时表时没有记录
    private boolean isGroup  = false;		//hql语句是否包含group by
    private List<T> currentPage = null;		// 当前页
    //private static Logger log = Logger.getLogger(Page.class);
    /**
    * 构造并初始化<br>
    * 示例：<br>
    * new Page("from Story s where s.id < 23 ", 4);
    * @param hql 查询语句
    * @param pageSize 每一页记录数
    */
    public  Page(String hql, int pageSize, SessionFactory sessionFactory) {
        this.hql = hql;
        this.pageSize = pageSize;
        this.sessionFactory = sessionFactory;
        this.caculateRow();
    }
    public void caculateRow()
    {
        Session session = null;
        try {
            session =sessionFactory.getCurrentSession();
            String sql = "select count(*) "+hql.substring(fromPosition(hql));
            List list  = session.createQuery(sql).list();
            if(!isGroup)
                totalRow = Integer.parseInt((list==null || list.size()==0)?"0":list.get(0).toString());
            else
                totalRow = (list==null?0:list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        totalPage = totalRow / pageSize;
        if (totalRow % pageSize != 0) {
            totalPage++;
        }
    }
    private int fromPosition(String hql){
        hql = hql.toLowerCase();
        if(hql.indexOf("group by")>0){
            isGroup = true;
        }
        return hql.indexOf("from");
    }

    /**
     * 构造并初始化<br>
     * 示例：<br>
     * new Page("from Story s where s.id < 23 order by date desc", "select count(s.id) from Story s where s.id < 23", 10);<br>
     * new Page("from Story s where s.id < 23 order by date desc", "select count( s ) from Story s where s.id < 23", 10);<br>
     * new Page("from Story s where s.id < 23 order by date desc", "select count( * ) from Story s where s.id < 23", 10);
     * @param hql 查询语句
     * @param countHql 用于计算总行数的查询语句，countHql的where子句必需和hql的where子句相同
     * @param pageSize 每一页记录数
     */
    public Page(String hql, String countHql, int pageSize) {
        this.hql = hql;
        this.pageSize = pageSize;
        Session session = null;
        try {
            session =sessionFactory.getCurrentSession();
            this.totalRow = ((Long) session.createQuery(countHql).uniqueResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        totalPage = totalRow / pageSize;
        if (totalRow % pageSize != 0) {
            totalPage++;
        }
    }

    /**
     * 构造并初始化<br>
     * 示例：<br>
     * new Page(10, "from Story　s", "where s.id < 23 order by date desc");
     * @param pageSize 每一页记录数
     * @param hqlFrom 查询语句from子句
     * @param hqlWhere 查询语句where子句
     */
    public Page(int pageSize, String hqlFrom, String hqlWhere) {
        this.hql = hqlFrom + hqlWhere;
        String countHql = "select count(*) " + hqlWhere;
        this.pageSize = pageSize;
        Session session = null;
        try {
            session =sessionFactory.getCurrentSession();
            this.totalRow = ((Long) session.createQuery(countHql).uniqueResult()).intValue();
        } catch (Exception e) {
        } finally {
            session.close();
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
//		if (currentPageNumber == targetPageNumber) {	// 要跳去的页面号与当前页号相同，则返回当前页列表，而不用查数据库
//			return currentPage;
//		}
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
            currentPage = session.createQuery(hql).setFirstResult(pageSize * (currentPageNumber - 1)).setMaxResults(pageSize).list();
        } catch (Exception e) {
            e.printStackTrace();
            //currentPage = NullList.getInstance();
        }finally{
           // session.close();
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
                target = 1;
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
    public String getHql() {
        return hql;
    }

}
