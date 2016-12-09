/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wuzh.commons.pager;

import java.util.List;

/**
 * 类Page.java的实现描述：分页信息
 * 
 * @author 伍章红 2015年11月6日 上午11:50:41
 * @version v1.0.0
 * @since JDK 1.7
 */
public interface Page {

    /**
     * 页码（当前所处页号）。页码从1开始
     * 
     * @author 伍章红 2015年11月6日 上午11:54:58
     * @return
     */
    public int getPageNo();

    /**
     * 每页显示数据量
     * 
     * @author 伍章红 2015年11月6日 上午11:54:58
     * @return
     */
    public int getPageSize();

    /**
     * 页面总数
     * 
     * @author 伍章红 2015年11月6日 上午11:54:58
     * @return
     */
    public int getPageTotal();

    /**
     * 行记录总数
     * 
     * @author 伍章红 2015年11月6日 上午11:54:58
     * @return
     */
    public long getRowCount();

    /**
     * 当前页面开始行
     * 
     * @author 伍章红 2015年11月6日 上午11:54:58
     * @return
     */
    public int getOffSet();

    /**
     * 当前页面结束行
     * 
     * @author 伍章红 2015年11月6日 上午11:54:58
     * @return
     */
    public int getEndSet();

    /**
     * 是否第一页
     * 
     * @author 伍章红 2015年11月6日 上午11:54:55
     * @return
     */
    public boolean isFirstPage();

    /**
     * 是否最后一页
     * 
     * @author 伍章红 2015年11月6日 上午11:54:58
     * @return
     */
    public boolean isLastPage();

    /**
     * 是否有上一页
     * 
     * @author 伍章红 2015年11月6日 上午11:54:58
     * @return
     */
    public boolean hasPreviousPage();

    /**
     * 是否有下一页
     * 
     * @author 伍章红 2015年11月6日 上午11:54:58
     * @return
     */
    public boolean hasNextPage();

    /**
     * 排序字段
     * 
     * @author 伍章红 2015年11月9日 上午11:53:19
     * @return
     */
    public List<Sort> getSorts();
}
