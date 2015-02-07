package com.jd.uwp.core.util;

import com.jd.uwp.common.utils.StringReplace;
import com.jd.uwp.common.utils.WebApp;

import java.io.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * 根据模板生成javabean 类文件
 *
 * @author :    xushiheng
 * @version :
 * @date :
 * @since :
 */
public class PoCreater {
    /**
     * 包名称
     */
    String packagePath = "";
    String packageName = "";
    String importClass = "";
    String toStringStatement = "";
    String defineField = "";
    String defineFieldDesc = "";
    String rootPathString = WebApp.getProjectPath() + "/po/com/common/idda/db/table/structure/";

    public String getRootPathString() {
        return rootPathString;
    }

    public void setRootPathString(String rootPathString) {
        this.rootPathString = WebApp.getProjectPath() + rootPathString;
    }

    String modelpath = WebApp.getPlatformPatn() + "/uwp_core/target/classes/tableModelOfCommon.txt";

    public PoCreater() {
        // TODO Auto-generated constructor stub
    }

    public void ReadDBToJava(Connection conns, String tableName) throws IOException {
        List<String> importList = new LinkedList();
        importList.add("import com.core.util.db.TableBean;");
        ReadDBToJava(conns, tableName, "com.common.db.table.structure." + tableName + ";", "", importList);
    }


    public void ReadDBToJava(Connection conns, String tableName, String sourcePath, String packetPath, List<String> importClasses) throws IOException {

        modelpath = modelpath.replace("%20", " ");

        rootPathString = WebApp.getProjectPath() + sourcePath + "/" + packetPath.replace(".", "/") + "/";
        rootPathString = rootPathString.replace("%20", " ");

        Connection conn = null;
        ResultSet re = null;
        PreparedStatement pstmt = null;

        String sql = "select * from " + tableName + " where 1>2";

        String filePath = rootPathString + "/" + UCfirst(tableName) + ".java";

        String xmlfilePath = rootPathString + "xml" + "/" + UCfirst(tableName) + ".xml";

//		CreateFolder(rootPathString+tableName);
        CreateFolder(rootPathString);

        this.packagePath = "package " + packetPath + ";";

//		this.importClass = "import com.common.core.db.table.structure.TableBean;";
        this.importClass = "";
        for (String classStr : importClasses) {
            this.importClass += "\r\nimport " + classStr + ";";
        }
        this.importClass += "\r\nimport java.util.Date;";
        this.importClass += "\r\nimport java.sql.ResultSet;";
        this.importClass += "\r\nimport java.sql.SQLException;";
        this.importClass += "\r\nimport java.math.BigDecimal;";

        try {
            conn = conns;
            pstmt = conn.prepareStatement(sql);
            re = pstmt.executeQuery();
            ResultSetMetaData rsmd = re.getMetaData();

            StringBuffer sbField = new StringBuffer();
            StringBuffer sbsField = new StringBuffer();
            StringBuffer sbcField = new StringBuffer();
            StringBuffer sbsetField = new StringBuffer();
            String filedType = "";
            String filedName = "";
            StringBuffer sbToDBClas = new StringBuffer();

            int sum = rsmd.getColumnCount();
            for (int i = 1; i < sum + 1; i++) {

                filedType = getDataType(rsmd.getColumnType(i), rsmd.getScale(i));
                filedName = rsmd.getColumnName(i);
                //根据不同的数据字段库类型生成相应的方法
                if (filedType != null && filedType.equals("int")) {
                    sbsetField.append("      this." + filedName + "=rs.getInt(\"" + filedName + "\");\r\n");
                    sbcField.append("this." + filedName + "=0;\r\n");
                } else if (filedType != null && filedType.equals("BigDecimal")) {
                    sbsetField.append("      this." + filedName + "=rs.getBigDecimal(\"" + filedName + "\");\r\n");
                    sbcField.append("        this." + filedName + "=new BigDecimal(0);\r\n");
                } else if (filedType != null && filedType.equals("double")) {
                    sbsetField.append("      this." + filedName + "=rs.getDouble(\"" + filedName + "\");\r\n");
                    sbcField.append("        this." + filedName + "=0;\r\n");
                } else if (filedType != null && filedType.equals("String")) {
                    sbsetField.append("      this." + filedName + "=rs.getString(\"" + filedName + "\");\r\n");
                    sbcField.append("        this." + filedName + "=\"\";\r\n");
                } else if (filedType != null && filedType.equals("Date")) {
                    sbsetField.append("      this." + filedName + "=rs.getTimestamp(\"" + filedName + "\");\r\n");
                    sbcField.append("        this." + filedName + "=new Date();\r\n");
                } else if (filedType != null && filedType.equals("Blob")) {

                }

                String fildString = "";
                fildString += "    private " + filedType;
                fildString += " " + filedName;

                sbField.append(fildString + ";\r\n");
                sbField.append("    public void set" + UCfirst(filedName) + "(" + filedType + " " + filedName + "){\r\n");
                sbField.append("        this." + filedName + "=" + filedName + ";\r\n");
                sbField.append("        conditionMap.put(\"" + filedName + "\"," + filedName + ");\r\n");
                sbField.append("    }\r\n");
                sbField.append("    public " + filedType + " get" + UCfirst(filedName) + "(){\r\n");
                sbField.append("        return this." + filedName + ";\r\n");
                sbField.append("    }\r\n");
                sbField.append("\r\n");

                sbsField.append("tmpStr.append(fieldList.get(\"" + filedName + "\")+  delimiterNameValue +" + filedName + ")");
                sbsField.append(".append(specDelimiterRecord);\r\n");
            }

            sbsField.append("        return tmpStr.toString();");
            String line = "";
            File file = new File(modelpath);
            FileWriter fileWriter = new FileWriter(filePath);
            System.out.println(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(modelpath), "UTF-8"));
            line = reader.readLine();
            while (line != null) {
                line = StringReplace.replace(line, "%packagePath%", packagePath, false);
                line = StringReplace.replace(line, "%importClass%", importClass, false);
                line = StringReplace.replace(line, "%tableName%", UCfirst(tableName), false);
                line = StringReplace.replace(line, "%clearStatement%", sbcField.toString(), false);
                line = StringReplace.replace(line, "%toStringStatement%", toStringStatement, false);
                line = StringReplace.replace(line, "%defineField%", sbField.toString(), false);
                line = StringReplace.replace(line, "%toStringStatement()%", sbsField.toString(), false);
                line = StringReplace.replace(line, "%defineFieldDesc%", defineFieldDesc, false);
                line = StringReplace.replace(line, "%setResultSet%", sbsetField.toString(), false);

                fileWriter.write(line + "\n");
                line = reader.readLine();
            }
            fileWriter.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (re != null)
                    re.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getDataType(int type, int scale) {

        String dataType = "";
        switch (type) {
            case Types.LONGVARCHAR://-1
                dataType = "String";
                break;
            case Types.CHAR://1
                dataType = "Character";
                break;
            case Types.INTEGER://4
                dataType = "int";
                break;
            case Types.NUMERIC://2
                switch (scale) {
                    case 0:
                        dataType = "int";
                        break;
                    case -127:
                        dataType = "Float";
                        break;
                    default:
                        dataType = "int";
                }
                break;
            case Types.VARCHAR://12
                dataType = "String";
                break;
            case Types.DATE://91
                dataType = "Date";
                break;
            case Types.TIMESTAMP://93
                dataType = "Date";
                break;
            case Types.BLOB:
                dataType = "Blob";
                break;
            case Types.DECIMAL://3
                dataType = "BigDecimal";
                break;
            case Types.DOUBLE://8
                dataType = "double";
                break;
            default:
                dataType = "String";
        }
        return dataType;
    }

    public static boolean CreateFolder(String targetDir) {
        File target = new File(targetDir);
        if (target.canExecute()) {
            return false;
        }
        (new File(targetDir)).mkdirs();
        return true;
    }


    public static String UCfirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
