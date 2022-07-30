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
package com.github.wywuzh.commons.core.sql.oracle;

import com.github.wywuzh.commons.core.common.Constants;
import com.github.wywuzh.commons.core.reflect.ReflectUtils;
import com.github.wywuzh.commons.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import sun.awt.OSInfo;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * 类SqlldrUtils的实现描述：SQL*LOADER工具
 * <pre>
 * 参考：
 * https://blog.csdn.net/cqc18951/article/details/100236316
 * https://blog.51cto.com/411431586/774949
 * https://www.cnblogs.com/itlixian/archive/2019/08/27/11419742.html
 * </pre>
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-17 15:27:07
 * @version v2.3.6
 * @since JDK 1.8
 */
@Slf4j
public class SqlldrUtils {

    /**
     * 控制文件后缀：默认以“.ctl”结尾
     */
    public static final String DEFAULT_CONTROL_FILE_SUFFIX = ".ctl";
    /**
     * 数据文件后缀：默认以“.csv”结尾
     */
    public static final String DEFAULT_DATA_FILE_SUFFIX = ".csv";


    /*---------------------------------------- 创建ctl控制器文件 >>> Start ----------------------------------------*/

    /**
     * 创建ctl控制器文件
     *
     * @param folderPath   ctl、csv文件根路径
     * @param ctlFileName  ctl文件名，不含后缀
     * @param csvFilePaths csv文件路径
     * @param tableName    表名
     * @param columns      列名，多个以逗号分隔
     * @return
     */
    public static File createCtlFile(File folderPath, String ctlFileName, List<String> csvFilePaths, String tableName, String columns) throws IOException {
        return createCtlFile(folderPath, ctlFileName, csvFilePaths, tableName, columns, Constants.SEPARATE_COMMA);
    }

    /**
     * 创建ctl控制器文件
     *
     * @param folderPath       ctl、csv文件根路径
     * @param ctlFileName      ctl文件名，不含后缀
     * @param csvFilePaths     csv文件路径
     * @param tableName        表名
     * @param columns          列名，多个以逗号分隔
     * @param contentSeparator 数据文件内容分隔符，默认为“,”
     * @return
     * @since v2.5.2
     */
    public static File createCtlFile(File folderPath, String ctlFileName, List<String> csvFilePaths, String tableName, String columns, String contentSeparator) throws IOException {
        if (!StringUtils.endsWithIgnoreCase(ctlFileName, DEFAULT_CONTROL_FILE_SUFFIX)) {
            ctlFileName = StringUtils.join(ctlFileName, DEFAULT_CONTROL_FILE_SUFFIX);
        }
        File ctlFile = new File(folderPath, ctlFileName);
        if (ctlFile.exists()) {
            FileUtils.forceDelete(ctlFile);
        }
        return createCtlFile(ctlFile, csvFilePaths, tableName, columns, contentSeparator);
    }

    /**
     * 创建ctl控制器文件
     *
     * @param ctlFile       控制文件
     * @param dataFilePaths 数据文件路径
     * @param tableName     表名
     * @param columns       列名，多个以逗号分隔
     * @return
     */
    public static File createCtlFile(File ctlFile, List<String> dataFilePaths, String tableName, String columns) throws IOException {
        return createCtlFile(ctlFile, dataFilePaths, tableName, columns, Constants.SEPARATE_COMMA);
    }

    /**
     * 创建ctl控制器文件
     *
     * @param ctlFile          控制文件
     * @param dataFilePaths    数据文件路径
     * @param tableName        表名
     * @param columns          列名，多个以逗号分隔
     * @param contentSeparator 数据文件内容分隔符，默认为“,”
     * @return
     * @since v2.5.2
     */
    public static File createCtlFile(File ctlFile, List<String> dataFilePaths, String tableName, String columns, String contentSeparator) throws IOException {
        FileWriter fileWriter = new FileWriter(ctlFile);

        StringBuffer stringBuffer = new StringBuffer();
        // 0=从第一行开始;1=从第二行
        stringBuffer.append("OPTIONS (skip=0)\n");
        // 默认为关键字，占一行；
        stringBuffer.append("load data\n");
        // 字符集设定,这样才能导入中文
        stringBuffer.append("CHARACTERSET 'UTF8'\n");
        for (String dataFilePath : dataFilePaths) {
            // 需要装载的数据文件的路径；如果是导入多个格式一致的数据文件，另起一行。
            stringBuffer.append("infile '" + dataFilePath + "'\n");
        }
        // 导入规则：
        // append=追加数据
        // truncate=清空前面导入的，只保留当前文件内容
        stringBuffer.append("append into table " + tableName + "\n");
        // 指定每一列的间隔符号标识，占一行
        // ','：表示以逗号作为每一列的分隔符
        // x'09'：表示以“TAB”（制表符）作为每一列的分隔符
        stringBuffer.append("fields terminated by '").append(contentSeparator).append("'\n");
        stringBuffer.append("optionally enclosed by '\"'\n");
        // 允许字段为空值
        stringBuffer.append("TRAILING NULLCOLS\n");
        stringBuffer.append("(\n");
        stringBuffer.append(columns);
        stringBuffer.append("\n)");

        String data = stringBuffer.toString();
        fileWriter.write(data);
        fileWriter.close();

        return ctlFile;
    }

    /*---------------------------------------- 创建ctl控制器文件 <<< End ----------------------------------------*/


    /*---------------------------------------- 向csv文件写入数据 >>> Start ----------------------------------------*/

    /**
     * 向dataFileName文件中写入数据
     *
     * @param folderPath   数据文件根路径
     * @param dataFileName 数据文件名
     * @param fieldName    字段名，多个以逗号分割
     * @param list         需要写入数据文件的数据
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     */
    public static <T> File writeDataFile(File folderPath, String dataFileName, String fieldName, List<T> list, String contentSeparator) throws IOException, IllegalAccessException {
        String[] fieldNameArr = StringUtils.split(fieldName, Constants.SEPARATE_COMMA);
        return writeDataFile(folderPath, dataFileName, fieldNameArr, list, contentSeparator);
    }

    /**
     * 向dataFileName文件中写入数据
     *
     * @param folderPath   数据文件根路径
     * @param dataFileName 数据文件名，默认为“.csv”后缀，文件后缀支持格式：.csv/.txt/.dat。
     * @param fieldNameArr 字段名
     * @param list         需要写入数据文件的数据
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     */
    public static <T> File writeDataFile(File folderPath, String dataFileName, String[] fieldNameArr, List<T> list, String contentSeparator) throws IOException, IllegalAccessException {
        if (StringUtils.indexOf(dataFileName, Constants.SEPARATE_SPOT) <= 0) {
            dataFileName = StringUtils.join(dataFileName, DEFAULT_DATA_FILE_SUFFIX);
        }
        // 删除历史文件
        File csvFile = new File(folderPath, dataFileName);
        if (csvFile.exists()) {
            FileUtils.forceDelete(csvFile);
        }
        return writeDataFile(csvFile, fieldNameArr, list, contentSeparator);
    }

    public static <T> File writeDataFile(File dataFile, String[] fieldNameArr, List<T> list, String contentSeparator) throws IOException, IllegalAccessException {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataFile, true), Charset.forName("UTF-8")));

            // 字段值
            for (T item : list) {
                StringBuilder content = new StringBuilder();
                content.append(appendContent(fieldNameArr, item, contentSeparator));
                content.append("\n");
                bufferedWriter.write(content.toString());
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw e;
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return dataFile;
    }

    public static <T> String appendContent(String[] fieldNameArr, T item, String contentSeparator) throws IllegalAccessException {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < fieldNameArr.length; i++) {
            String fieldName = fieldNameArr[i];
            Field field = FieldUtils.getDeclaredField(item.getClass(), fieldName, true);
            if (field == null) {
                field = FieldUtils.getField(item.getClass(), fieldName, true);
            }
            if (field == null) {
                continue;
            }
            if (i > 0) {
                content.append(contentSeparator);
            }
            Object value = ReflectUtils.getValue(item, fieldName);
            if (value == null) {
                continue;
            }
            if (field.getType() == String.class) {
                // html转义符
                value = String.valueOf(value)
                        .replaceAll("\"", "“")
                        .replaceAll("'", "‘")
                        .replaceAll("\n", "char(10)") // ascii码换行
                        .replaceAll("\r", "char(13)"); // ascii码回车
                value = StringUtils.join(Constants.SEPARATE_DOUBLE_QUOTATION_MARK, value, Constants.SEPARATE_DOUBLE_QUOTATION_MARK);
            } else if (field.getType() == java.util.Date.class) {
                value = StringUtils.join(Constants.SEPARATE_DOUBLE_QUOTATION_MARK, DateUtil.format((Date) value, DateUtil.PATTERN_DATE_TIME), Constants.SEPARATE_DOUBLE_QUOTATION_MARK);
            } else if (field.getType() == java.sql.Date.class) {
                value = StringUtils.join(Constants.SEPARATE_DOUBLE_QUOTATION_MARK, DateUtil.format((Date) value, DateUtil.PATTERN_TIME), Constants.SEPARATE_DOUBLE_QUOTATION_MARK);
            } else if (field.getType() == java.sql.Time.class) {
                value = StringUtils.join(Constants.SEPARATE_DOUBLE_QUOTATION_MARK, DateUtil.format((Date) value, DateUtil.PATTERN_TIME), Constants.SEPARATE_DOUBLE_QUOTATION_MARK);
            } else if (field.getType() == BigDecimal.class
                    || field.getType() == Double.class
                    || field.getType() == Float.class) {
                value = StringUtils.join(Constants.SEPARATE_DOUBLE_QUOTATION_MARK, String.valueOf(value), Constants.SEPARATE_DOUBLE_QUOTATION_MARK);
            } else {
                value = StringUtils.join(Constants.SEPARATE_DOUBLE_QUOTATION_MARK, String.valueOf(value), Constants.SEPARATE_DOUBLE_QUOTATION_MARK);
            }

            content.append(value);
        }
        return content.toString();
    }

    public static <T> File writeDataFile(File dataFile, List<String> list) throws IOException, IllegalAccessException {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataFile, true), Charset.forName("UTF-8")));

            // 字段值
            for (String item : list) {
                String content = item + "\n";
                bufferedWriter.write(content);
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw e;
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return dataFile;
    }

    public static <T> File writeDataFile(File dataFile, String content, boolean append) throws IOException, IllegalAccessException {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataFile, append), Charset.forName("UTF-8")));

            // 字段值
            bufferedWriter.write(content);
            bufferedWriter.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw e;
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return dataFile;
    }

    /*---------------------------------------- 向csv文件写入数据 <<< End ----------------------------------------*/


    /**
     * 构建sqlldr命令
     *
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param database 数据库名
     * @param ctlFile  ctl控制文件
     * @param logFile  日志文件
     * @param badFile  坏数据文件
     * @return
     */
    public static String buildSqlldrCommand(String username, String password, String database, String ctlFile, String logFile, String badFile) {
        // 格式：sqlldr userid={username}/{password}@{database} control={ctlFile} log={logFile} bad={badFile} direct=true
        final String format = "sqlldr userid={0}/{1}@{2} control={3} log={4} bad={5} errors=0";

        String sqlldrCommand = MessageFormat.format(format, username, password, database, ctlFile, logFile, badFile);
        return sqlldrCommand;
    }

    /**
     * @return Oracle环境变量
     */
    public static String getOracleHome() {
        // Oracle安装目录：优先取Oracle环境变量，如果没有则从系统属性中取
        String ORACLE_HOME = System.getenv("ORACLE_HOME");
        log.info("根据System.getenv(\"ORACLE_HOME\")方法取Oracle环境变量：{}", ORACLE_HOME);
        if (StringUtils.isBlank(ORACLE_HOME)) {
            log.info("System.getenv(\"ORACLE_HOME\")未取到Oracle环境变量，改用System.getProperty(\"ORACLE_HOME\")取Oracle环境变量：{}", ORACLE_HOME);
            ORACLE_HOME = System.getProperty("ORACLE_HOME");
        }
        return ORACLE_HOME;
    }

    /**
     * 执行命令
     *
     * @param command 命令
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static int executive(String command) throws IOException, InterruptedException {
        // Oracle安装目录：优先取Oracle环境变量，如果没有则从系统属性中取
        String ORACLE_HOME = getOracleHome();
        if (StringUtils.isBlank(ORACLE_HOME)) {
            log.error("未取到Oracle环境变量，无法执行传入的sqlldr命令：{}", command);
            throw new RuntimeException("请先设置Oracle环境变量！");
        }

        long startTime = System.currentTimeMillis();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            String[] cmd = null;
            if (OSInfo.OSType.WINDOWS.equals(OSInfo.getOSType())) {
                cmd = new String[]{"cmd.exe", "/C", ORACLE_HOME + "\\bin\\" + command};
            } else if (OSInfo.OSType.LINUX.equals(OSInfo.getOSType())) {
                cmd = new String[]{"/bin/bash", "-c", ORACLE_HOME + "/bin/" + command};
            }
            log.info("开始执行cmd命令：{}", StringUtils.join(cmd, Constants.SEPARATE_COMMA));
            Process process = Runtime.getRuntime().exec(cmd);
            inputStream = process.getInputStream(); // 获取执行cmd命令后的信息

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            StringBuilder execMsg = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                String msg = new String(line.getBytes("ISO-8859-1"), "UTF-8");
                execMsg.append(msg).append("<br/>");
            }
            log.info(execMsg.toString()); // 输出
            int exitValue = process.waitFor();

            log.info("导入数据返回值：{}", exitValue);

            if (exitValue == 0) {
                log.info("The records were loaded successfully");
            } else {
                log.info("The records were not loaded successfully");
            }

            process.getOutputStream().close(); // 关闭

            return exitValue;
        } catch (Exception e) {
            log.error("{} 执行sqlldr命令失败：", command, e);
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            log.error("{} 执行sqlldr命令结束，共耗时：{}毫秒", command, (System.currentTimeMillis() - startTime));
        }
    }

}
