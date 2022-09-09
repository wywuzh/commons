/*
 * Copyright 2015-2022 the original author or authors.
 *
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
package com.github.wywuzh.commons.pager;

import java.util.List;

/**
 * 类Page.java的实现描述：分页信息
 *
 * @author 伍章红 2015年11月6日 上午11:50:41
 * @version v2.0.2
 * @since JDK 1.8
 */
public interface Page {

  /**
   * 页码（当前所处页号）。页码从1开始
   *
   * @return 当前所处页号
   */
  public int getPageNo();

  /**
   * 每页显示数据量
   *
   * @return 每页显示数据量
   */
  public int getPageSize();

  /**
   * 页面总数
   *
   * @return 页面总数
   */
  public int getPageTotal();

  /**
   * 行记录总数
   *
   * @return 行记录总数
   */
  public long getRowCount();

  /**
   * 当前页面开始行
   *
   * @return 当前页面开始行
   */
  public int getOffSet();

  /**
   * 当前页面结束行
   *
   * @return 当前页面结束行
   */
  public int getEndSet();

  /**
   * 是否第一页
   *
   * @return 返回为true表示当前页是第一页，此时{@link #getPageNo()}的值为1；返回false则表示当前页不是第一页
   */
  public boolean isFirstPage();

  /**
   * 是否最后一页
   *
   * @return 返回为true表示当前页是最后一页；返回false则表示当前页不是最后一页
   */
  public boolean isLastPage();

  /**
   * 是否有上一页
   *
   * @return 返回为true表示有上一页，此时{@link #isFirstPage()}为false；返回false则表示当前页是第一页，此时{@link #isFirstPage()}为true，{@link #getPageNo()}的值为1
   */
  public boolean hasPreviousPage();

  /**
   * 是否有下一页
   *
   * @return 返回为true表示有下一页，此时{@link #isLastPage()}为false；返回false则表示当前页是最后一页，此时{@link #isLastPage()}为true
   */
  public boolean hasNextPage();

  /**
   * 排序字段
   *
   * @return 返回分页查询时的排序字段
   */
  public List<Sort> getSorts();
}
