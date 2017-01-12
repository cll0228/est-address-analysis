package com.address.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.address.model.AssessParam;
import com.address.model.AssessResult;
import com.address.service.IAssService;

/**
 * 用于模拟HTTP请求中GET/POST方式 
 * @author landa
 *
 */
public class ConsumerApp {
	
	private IAssService assService;
	
	private volatile int count = 10;

    public static final int MAX_COUNT = 980;


    private static File qpsFile = new File("output/qps.log" + System.currentTimeMillis());

    private static File cannotass = new File("D:\\无法估价.log");

    private static File assessOkIds = new File("可以正常估价的ID记录.log");

    private static File assessResult = new File("估价結果.log");
    
    public void logAssessResult(int index, double totalPrice) {
        try {
            FileUtils.writeStringToFile(assessResult, index + ":" + totalPrice + "\r\n", "utf-8", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logQps(String str) {
        try {
            FileUtils.writeStringToFile(qpsFile, str + "\r\n", "utf-8", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logCannotAss(int id) {
        try {
            FileUtils.writeStringToFile(cannotass, id + "\r\n", "utf-8", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void assOk(int id) {
        try {
            FileUtils.writeStringToFile(assessOkIds, id + "\r\n", "utf-8", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /** 
     * 发送GET请求 
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     */  
    public static String sendGet(String url, Map<String, String> parameters) { 
        String result="";
        BufferedReader in = null;// 读取响应输入流  
        StringBuffer sb = new StringBuffer();// 存储参数  
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数  
            if(parameters.size()==1){
                for(String name:parameters.keySet()){
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),  
                            "UTF-8"));
                }
                params=sb.toString();
            }else{
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8")).append("&");  
                }  
                String temp_params = sb.toString();  
                params = temp_params.substring(0, temp_params.length() - 1);  
            }
            String full_url = url + "?" + params; 
            System.out.println(full_url); 
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(full_url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                    .openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            httpConn.setRequestProperty("User-Agent",  
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 建立实际的连接  
            httpConn.connect();  
            // 响应头部获取  
            Map<String, List<String>> headers = httpConn.getHeaderFields();  
            // 遍历所有的响应头字段  
            for (String key : headers.keySet()) {  
                System.out.println(key + "\t：\t" + headers.get(key));  
            }  
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式  
            in = new BufferedReader(new InputStreamReader(httpConn  
                    .getInputStream(), "UTF-8"));  
            String line;  
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }
        return result ;
    }  
  
    /** 
     * 发送POST请求 
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     */  
    public static String sendPost(String url, Map<String, String> parameters) {  
        String result = "";// 返回的结果  
        BufferedReader in = null;// 读取响应输入流  
        PrintWriter out = null;  
        StringBuffer sb = new StringBuffer();// 处理请求参数  
        String params = "";// 编码之后的参数  
        try {  
            // 编码请求参数  
            if (parameters.size() == 1) {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8"));  
                }  
                params = sb.toString();  
            } else {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8")).append("&");  
                }  
                String temp_params = sb.toString();  
                params = temp_params.substring(0, temp_params.length() - 1);  
            }  
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                    .openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            httpConn.setRequestProperty("User-Agent",  
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 设置POST方式  
            httpConn.setDoInput(true);  
            httpConn.setDoOutput(true);  
            // 获取HttpURLConnection对象对应的输出流  
            out = new PrintWriter(httpConn.getOutputStream());  
            // 发送请求参数  
            out.write(params);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式  
            in = new BufferedReader(new InputStreamReader(httpConn  
                    .getInputStream(), "UTF-8"));  
            String line;  
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
  
    /** 
     * 主函数，测试请求 
     *  
     * @param args 
     */  
    /*public static void main(String[] args) {  
        Map<String, String> parameters = new HashMap<String, String>();  
        parameters.put("appId", "20001");
        parameters.put("version", "V2.0.0"); 
        parameters.put("signType", "01"); 
        parameters.put("signCode", "33CE22FD701ED91E6A7D8964749B7482");
        String params = "{\"hallNum\":\" \",\"placeFloor\":\" \",\"planeType\":\"1\",\"propertyArea\":\"200\",\"propertyTypeId\":\"5\",\"residenceId\":\"3177\",\"roomNum\":\"4\",\"toiletNum\":\" \",\"totalFloor\":\"}";
        parameters.put("params", params); 
        String result =sendPost("http://192.168.201.33:8080/estimate/V2.0.0", parameters);
        System.out.println(result); 
    }*/
    
    
    
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/conf/applicationContext-dubbo-consumer.xml");
        context.start();
        IAssService assService = null;
        assService = (IAssService) context.getBean("assService");
        System.out.println("spring framework started");

        List<String> lines = FileUtils.readLines(new File("D:\\projects\\assess_sh_distrib\\assess_sh_server\\src\\test\\resources\\待估对象，小区存在.txt"), "utf-8");

        for (String line : lines) {
            String arr[] = line.split("\t");
            AssessParam AssessParam = new AssessParam();
            //AssessParam.setBlockId(toInt(arr[0]));
            AssessParam.setResidenceId(Integer.parseInt(arr[1]));
            AssessParam.setPropertyType(toInt(arr[2]));
            AssessParam.setBuildingNo(arr[3]);
            AssessParam.setRoom(arr[4]);   //502
            AssessParam.setFloor(toInt(arr[5]));
            AssessParam.setPropertyStorey(toInt(arr[6]));
            AssessParam.setPropertyArea(toDouble(arr[7]));
            AssessParam.setRoomNum(toInt(arr[8])); //2
            AssessParam.setHallNum(toInt(arr[9]));
            AssessParam.setToiletNum(toInt(arr[10]));
            AssessParam.setToward(toInt(arr[11]));
            objects.add(AssessParam);
        }

        ConsumerApp app = new ConsumerApp();
        app.assService = assService;
        app.sendAll();
    }
    
    private static Integer toInt(String str) {
        if (StringUtils.isBlank(str))
            return null;
        return Integer.parseInt(str);
    }
    
    private static double toDouble(String str) {
        return Double.parseDouble(str);
    }
    
    public static final List<AssessParam> objects = new ArrayList<>();

    private static AtomicInteger index = new AtomicInteger(0);

    private static final AtomicInteger totalCount = new AtomicInteger(0);
    
    private void sendAll() {

        final AtomicInteger integer = new AtomicInteger(0);

        final long beginTime = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    long start = System.currentTimeMillis();
                    AssessParam AssessParam = getBean();

                    int itemId = 0;//AssessParam.getBlockId();
             //       AssessParam.setBlockId(null);
                    AssessResult t = assService.assHouse(AssessParam);

                    long end = System.currentTimeMillis();

                    double time = (end - start) / 1000.0D;

                    int returned = integer.addAndGet(1);

                    Double totalPrice = null;
                    if (t != null ) {
                        totalPrice = t.getTotalPrice();
                    }
                    if (totalPrice == null) {
                        logCannotAss(itemId);
                        throw new IllegalArgumentException("无法估价" + itemId);
                    } else {
                        assOk(itemId);
                    }
                    System.out.println(returned + ":返回结果,cost time: " + time + " s" + ",total price:" + totalPrice);

                    if (returned == count) {
                        double seconds = (end - beginTime) / 1000.0D;
                        String secondsStr = new DecimalFormat("#.000").format(seconds);
                        double qps = count / seconds;

                        String countStr = String.format("%03d", count);
                        System.out.println(countStr + "个记录全部执行完，花费的时间:" + secondsStr + ",qps:" + qps);

                        if (count < MAX_COUNT) {
                            count += 20;
                            sendAll();
                        } else {
                            System.out.println("查询记录总数:" + totalCount);
                        }
                    }

                }
            };

            t.start();
        }
    }
    
    public static AssessParam getBean() {
        totalCount.addAndGet(1);

        int i = index.addAndGet(1);
        if (i >= objects.size() - 1) {
            index.set(0);
            i = 0;
        }
        AssessParam src = objects.get(i);

        AssessParam dest = new AssessParam();

        dest.setResidenceId(src.getResidenceId());
        dest.setPropertyType(src.getPropertyType());
        dest.setBuildingNo(src.getBuildingNo());
        dest.setRoom(src.getRoom());   //502
        dest.setFloor(src.getFloor());
        dest.setPropertyStorey(src.getPropertyStorey());
        dest.setPropertyArea(src.getPropertyArea());
        dest.setRoomNum(src.getRoomNum()); //2
        dest.setHallNum(src.getHallNum());
        dest.setToiletNum(src.getToiletNum());
        dest.setToward(src.getToward());
 //       dest.setBlockId(src.getBlockId());
        return dest;
    }
}