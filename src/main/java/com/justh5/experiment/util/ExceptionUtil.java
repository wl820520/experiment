/**
 * Create date: 2017/12/20
 * Author jw
 * Description
 */
package com.justh5.experiment.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

    public static String exception2String(Throwable e){
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
                sw = new StringWriter();
                pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String eMsg = sw.toString();
                sw.close();
                pw.close();
                return eMsg;

            } catch (Exception e2) {
                return "异常转换字符串失败";
            }finally {
                try {
                    sw.close();
                } catch (IOException e1) {

                }finally {
                    pw.close();
                }
            }
    }
}
