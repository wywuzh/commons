/*
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.itfsw.mybatis.generator.plugins.utils;

import com.itfsw.mybatis.generator.plugins.CommentPlugin;
import com.itfsw.mybatis.generator.plugins.utils.enhanced.TemplateCommentGenerator;
import com.itfsw.mybatis.generator.plugins.utils.hook.HookAggregator;
import com.itfsw.mybatis.generator.plugins.utils.hook.ITableConfigurationHook;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.internal.util.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ---------------------------------------------------------------------------
 * 基础plugin
 * ---------------------------------------------------------------------------
 * @author: hewei
 * @time:2017/4/28 13:57
 * @author <a mailto="wywuzh@163.com">伍章红</a> 2020-01-09 09:47
 * ---------------------------------------------------------------------------
 */
public class BasePlugin extends PluginAdapter {
    protected static final Logger logger = LoggerFactory.getLogger(BasePlugin.class);
    protected CommentGenerator commentGenerator;
    protected List<String> warnings;

    /**
     * mybatis 版本
     */
    public static final String PRO_MYBATIS_VERSION = "mybatisVersion";
    protected String mybatisVersion = "3.5.3";

    /**
     * Set the context under which this plugin is running.
     * @param context the new context
     */
    @Override
    public void setContext(Context context) {
        super.setContext(context);

        // 添加插件
        HookAggregator.getInstance().setContext(context);

        // 配置插件使用的模板引擎
        PluginConfiguration cfg = PluginTools.getPluginConfiguration(context, CommentPlugin.class);

        // 检查context上下文中是否有配置 CommentPlugin 插件，并且 template 属性定义的模板路径是否配置
        if (cfg == null || cfg.getProperty(CommentPlugin.PRO_TEMPLATE) == null) {
            // 如果没有配置 CommentPlugin 插件，或者配置了 CommentPlugin 插件但 template 属性
            commentGenerator = context.getCommentGenerator();
        } else {
            // 如果配置了 CommentPlugin 插件，并且 template 属性指定了模板路径，则根据其指定的模板路径生成一个 TemplateCommentGenerator 模板注释器
            TemplateCommentGenerator templateCommentGenerator = new TemplateCommentGenerator(context, cfg.getProperty(CommentPlugin.PRO_TEMPLATE));

            // ITFSW 插件使用的注释生成器
            commentGenerator = templateCommentGenerator;

            // 修正系统插件
            try {
                // 先执行一次生成CommentGenerator操作，然后再替换
                // 预处理 mybatis-generator-core 内置的内容插件
                context.getCommentGenerator();

                // 通过反射机制，将 context 上下文中的 commentGenerator 字段值设置为我们自定义的 TemplateCommentGenerator 模板注释器对象
                BeanUtils.setProperty(context, "commentGenerator", templateCommentGenerator);
            } catch (Exception e) {
                logger.error("反射异常", e);
            }
        }

        // mybatis版本
        if (StringUtility.stringHasValue(context.getProperty(PRO_MYBATIS_VERSION))) {
            this.mybatisVersion = context.getProperty(PRO_MYBATIS_VERSION);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(List<String> warnings) {
        this.warnings = warnings;
        // 插件使用前提是targetRuntime为MyBatis3
        if (StringUtility.stringHasValue(getContext().getTargetRuntime()) && "MyBatis3".equalsIgnoreCase(getContext().getTargetRuntime()) == false) {
            warnings.add("itfsw:插件" + this.getClass().getTypeName() + "要求运行targetRuntime必须为MyBatis3！");
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     * @param introspectedTable
     */
    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        super.initialized(introspectedTable);
        if (StringUtility.stringHasValue(introspectedTable.getTableConfiguration().getAlias())) {
            warnings.add("itfsw:插件" + this.getClass().getTypeName() + "请不要配置alias属性，这个属性官方支持也很混乱，导致插件支持会存在问题！");
        }
        PluginTools.getHook(ITableConfigurationHook.class).tableConfiguration(introspectedTable);

        // mybatis版本
        if (StringUtility.stringHasValue(this.getProperties().getProperty(PRO_MYBATIS_VERSION))) {
            this.mybatisVersion = this.getProperties().getProperty(PRO_MYBATIS_VERSION);
        }
    }
}
