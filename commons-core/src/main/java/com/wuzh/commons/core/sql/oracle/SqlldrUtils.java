/*
 * Copyright 2015-2021 the original author or authors.
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
package com.wuzh.commons.core.sql.oracle;

import com.wuzh.commons.core.common.Constants;
import com.wuzh.commons.core.reflect.ReflectUtils;
import com.wuzh.commons.core.util.DateUtil;
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
     * 文件后缀：控制文件
     */
    public static final String DEFAULT_CONTROL_FILE_SUFFIX = ".ctl";
    /**
     * 文件后缀：数据文件
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
        if (!StringUtils.endsWithIgnoreCase(ctlFileName, ".ctl")) {
            ctlFileName = StringUtils.join(ctlFileName, ".ctl");
        }
        File ctlFile = new File(folderPath, ctlFileName);
        if (ctlFile.exists()) {
            ctlFile.delete();
        }
        return createCtlFile(ctlFile, csvFilePaths, tableName, columns);
    }

    /**
     * 创建ctl控制器文件
     *
     * @param ctlFile      ctl文件
     * @param csvFilePaths csv文件路径
     * @param tableName    表名
     * @param columns      列名，多个以逗号分隔
     * @return
     */
    public static File createCtlFile(File ctlFile, List<String> csvFilePaths, String tableName, String columns) throws IOException {
        FileWriter fileWriter = new FileWriter(ctlFile);

        StringBuffer stringBuffer = new StringBuffer();
        // 0=从第一行开始;1=从第二行
        stringBuffer.append("OPTIONS (skip=0)\n");
        // 默认为关键字，占一行；
        stringBuffer.append("load data\n");
        // 字符集设定,这样才能导入中文
        stringBuffer.append("CHARACTERSET 'UTF8'\n");
        for (String csvFilePath : csvFilePaths) {
            // 需要装载的数据文件的路径；如果是导入多个格式一致的数据文件，另起一行。
            stringBuffer.append("infile '" + csvFilePath + "'\n");
        }
        // 导入规则：
        // append=追加数据
        // truncate=清空前面导入的，只保留当前文件内容
        stringBuffer.append("append into table " + tableName + "\n");
        // 指定每一列的间隔符号标识，占一行。`','`表示以逗号最为每一列的分隔符
        stringBuffer.append("fields terminated by ','\n");
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

    public static <T> File writeDataFile(File folderPath, String dataFileName, String fieldName, List<T> list) throws IOException, IllegalAccessException {
        String[] fieldNameArr = StringUtils.split(fieldName, Constants.SEPARATE_COMMA);
        return writeDataFile(folderPath, dataFileName, fieldNameArr, list);
    }

    public static <T> File writeDataFile(File folderPath, String dataFileName, String[] fieldNameArr, List<T> list) throws IOException, IllegalAccessException {
        if (StringUtils.indexOf(dataFileName, Constants.SEPARATE_SPOT) <= 0) {
            dataFileName = StringUtils.join(dataFileName, DEFAULT_DATA_FILE_SUFFIX);
        }
        // 删除历史文件
        File csvFile = new File(folderPath, dataFileName);
        if (csvFile.exists()) {
            FileUtils.forceDelete(csvFile);
        }
        return writeDataFile(csvFile, fieldNameArr, list);
    }

    public static <T> File writeDataFile(File dataFile, String[] fieldNameArr, List<T> list) throws IOException, IllegalAccessException {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataFile, true), Charset.forName("UTF-8")));

            // 字段值
            for (T item : list) {
                StringBuilder content = new StringBuilder();
                content.append(appendContent(fieldNameArr, item));
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

    public static <T> String appendContent(String[] fieldNameArr, T item) throws IllegalAccessException {
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
                content.append(Constants.SEPARATE_COMMA);
            }
            Object value = ReflectUtils.getValue(item, fieldName);
            if (value == null) {
                continue;
            }
            if (field.getType() == String.class) {
                value = StringUtils.join("\"", String.valueOf(value), "\"");
            } else if (field.getType() == java.util.Date.class) {
                value = StringUtils.join("\"", DateUtil.format((Date) value, DateUtil.PATTERN_DATE_TIME), "\"");
            } else if (field.getType() == java.sql.Date.class) {
                value = StringUtils.join("\"", DateUtil.format((Date) value, DateUtil.PATTERN_TIME), "\"");
            } else if (field.getType() == java.sql.Time.class) {
                value = StringUtils.join("\"", DateUtil.format((Date) value, DateUtil.PATTERN_TIME), "\"");
            } else if (field.getType() == BigDecimal.class
                    || field.getType() == Double.class
                    || field.getType() == Float.class) {
                value = StringUtils.join("\"", String.valueOf(value), "\"");
            } else {
                value = StringUtils.join("\"", String.valueOf(value), "\"");
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
        // 格式：sqlldr userid={username}/{password}@{database} control={ctlFile} log={logFile} bad={badFile}
        final String format = "sqlldr userid={0}/{1}@{2} control={3} log={4} bad={5} direct=true errors=0";

        String sqlldrCommand = MessageFormat.format(format, username, password, database, ctlFile, logFile, badFile);
        return sqlldrCommand;
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
        String ORACLE_HOME = System.getenv("ORACLE_HOME");
        if (StringUtils.isBlank(ORACLE_HOME)) {
            ORACLE_HOME = System.getProperty("ORACLE_HOME");
        }
        if (StringUtils.isBlank(ORACLE_HOME)) {
            throw new RuntimeException("请先设置Oracle环境变量！");
        }
        String[] cmd = null;
        if (OSInfo.OSType.WINDOWS.equals(OSInfo.getOSType())) {
            cmd = new String[]{"cmd.exe", "/C", ORACLE_HOME + "\\bin\\" + command};
        } else if (OSInfo.OSType.LINUX.equals(OSInfo.getOSType())) {
            cmd = new String[]{"/bin/bash", "-c", ORACLE_HOME + "/bin/" + command};
        }

        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            inputStream = process.getInputStream(); // 获取执行cmd命令后的信息

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String msg = new String(line.getBytes("ISO-8859-1"), "UTF-8");
                log.info(msg); // 输出
            }
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
            log.error(e.getMessage(), e);
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
        }
    }

}