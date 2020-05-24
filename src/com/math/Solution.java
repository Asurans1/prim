package com.math;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class Solution {
    public static void main(String[] args) {
        solution3();
    }

    /**
     * 第一问方法，调用即可
     */
    public static void solution1(){
        int isPrint = 1;
        double[][] map = getMap();
        HashMap<Integer, Integer> stationMap = new HashMap<>();
        HashMap<Integer, Integer> stationMap1 = new HashMap<>();
        //遍历选两个站作为A站
        for (int i = 0; i <13 ; i++) {
            stationMap.put(i,0);
        }
        System.out.println(creatStation1(map, 0, stationMap,isPrint));
        for (int i = 1; i <181 ; i++) {
            stationMap1.put(i,0);
        }
        for (int k = 1; k <13 ; k++) {
            stationMap1.put(k,1);
        }
        System.out.println(creatStation2(map,stationMap,stationMap1,isPrint));
    }

    /**
     * 第二问方法，调用即可
     */
    public static void solution2(){
        //
        int isPrint = 0;
        double[][] map = getMap();
        double min = 1000;
        int index1 = -1;
        int index2 = -1;
        for (int i = 13;i<181;i++){
            for (int j = 13; j <181; j++) {
                if(i!=j){
                    HashMap<Integer, Integer> stationMap = new HashMap<>();
                    HashMap<Integer, Integer> stationMap1 = new HashMap<>();
                    for (int k = 0; k <13 ; k++) {
                        stationMap.put(k,0);
                    }
                    stationMap.put(i,0);
                    stationMap.put(j,0);
                    double l1 = creatStation1(map, 0, stationMap,isPrint);
                    System.out.println("变动基站为"+i+"基站"+"和"+j+"基站");
                    System.out.println("1管道为"+l1);
                    for (int m = 1; m <181 ; m++) {
                        stationMap1.put(m,0);
                    }
                    for (int k = 1; k <13 ; k++) {
                        stationMap1.put(k,1);
                    }
                    stationMap1.put(i,1);
                    stationMap1.put(j,1);
                    double l2 = creatStation2(map,stationMap,stationMap1,isPrint);
                    System.out.println("2管道为"+l2);
                    if(l2<min){
                        index1 = i;
                        index2 = j;
                        min = l2;
                    }
                }
            }
        }
        System.out.println("基站"+index1+"基站"+index2+"发生了改变,最终管道二米数为"+min);
    }

    /**
     * 第三问方法，调用即可
     */
    public static void solution3(){
        double[][] map = getMap();
        double minDistance = 100000000;
        int index = -1;
        int isPrint = 0;
        for (int j = 13; j <181 ; j++) {
            HashMap<Integer, Integer> stationMap = new HashMap<>();
            HashMap<Integer, State> map1 = new HashMap<>();
            for (int k = 0; k < 13; k++) {
                stationMap.put(k, 0);
            }
            stationMap.put(j, 0);
            double l1 = creatStation1(map, 0, stationMap,isPrint);
            for (int i = 1; i < 181; i++) {
                map1.put(i, new State(i, 0));
            }
            //前13个全部置1  已经访问过
            for (int i = 1; i < 13; i++) {
                map1.get(i).isVisited = 1;
            }
            map1.get(j).isVisited = 1;
            double l2 = creatStation3(map,map1);
            System.out.println(j + "基站升级为一级基站");
            if ((l1 + l2) < 10000) {
                System.out.println("1管道为" + l1 + "2管道为" + l2 + ",总长度为" + (l1 + l2));
            } else {
                System.out.println("该方案不成立");
            }
            if ((l1 + l2) < minDistance) {
                index = j;
                minDistance = (l1 + l2);
            }
            for (int i = 1; i <181 ; i++) {
                if(map1.get(i).isVisited==0){
                    System.out.println("改基站"+map1.get(i)+"未连接");
                }
            }
            for (int i = 1; i <13 ; i++) {
                System.out.println("基站"+i+"的总负荷为"+map1.get(i).weight);
            }
        }
        System.out.println("第"+index+"个基站升级为1级基站,最小距离为"+minDistance);
    }
    public static double creatStation1(double[][]map, int vertexId, HashMap<Integer, Integer> stationMap,int isPrint){
        int index1 = -1;
        int index2 = -1;//两个下标用于记录从一个站到另一个站
        double sumOfDistance = 0;
        double minDistance = 100000;//设置一个最小值，初值为10000
        int vertexSum = stationMap.size();
        stationMap.put(vertexId,1);
        for (int k = 0; k <vertexSum-1; k++) {
            for (Integer key1:stationMap.keySet()){
                for (Integer key2:stationMap.keySet()){
                    if(stationMap.get(key1)==1&&stationMap.get(key2)==0&&map[key1][key2]<minDistance){
                        index1 = key1;
                        index2 = key2;
                        minDistance = map[key1][key2];
                    }
                }
            }
            //双层for循环遍历完毕时，说明已经找到最小的节点
            if (isPrint==1)
                System.out.println("节点"+index1+"->"+"节点"+index2+"距离"+map[index1][index2]);
            stationMap.put(index2,1);
            sumOfDistance += minDistance;
            //重新设置为最大值
            minDistance = 100000;
        }
        return sumOfDistance;

    }
    public static double creatStation2(double[][]map,HashMap<Integer, Integer> stationMap1,HashMap<Integer, Integer> stationMap,int isPrint){
        int index1 = -1;
        int index2 = -1;//两个下标用于记录从一个站到另一个站
        double sumOfDistance = 0;
        int vertexSum = stationMap.size();
        double minDistance = 100000;//设置一个最小值，初值为10000
        for (int k = 0; k <vertexSum-stationMap1.size()+1; k++) {
            for (Integer key1:stationMap.keySet()){
                for (Integer key2:stationMap.keySet()){
                    if(stationMap.get(key1)==1&&stationMap.get(key2)==0&&map[key1][key2]<minDistance){
                        index1 = key1;
                        index2 = key2;
                        minDistance = map[key1][key2];
                    }
                }
            }
            //双层for循环遍历完毕时，说明已经找到最小的节点
            if (isPrint==1)
                System.out.println("节点"+index1+"->"+"节点"+index2+"距离"+map[index1][index2]);
            stationMap.put(index2,1);
            sumOfDistance += minDistance;
            //重新设置为最大值
            minDistance = 100000;
        }
        return sumOfDistance;

    }
    public static double[][] getMap(){
        String string = ClassLoader.getSystemResource("station.txt").toString();
        List<Station> list = readStation(string.substring(6));
        double[][] map = new double[list.size()][list.size()];
        for (int i = 0; i <list.size(); i++) {
            for (int j = 0; j <list.size(); j++) {
                map[i][j] = distance(list.get(i),list.get(j));
            }
        }
        return map;
    }
    public static double distance(Station s1, Station s2){
        int x = Math.abs(s1.x-s2.x);
        int y = Math.abs(s1.y-s2.y);
        return Math.sqrt(x*x+y*y);
    }
    public static List<Station> readStation(String txtPath){
        List<Station> list = new ArrayList<>();
        File file = new File(txtPath);
        if(file.isFile() && file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuffer sb = new StringBuffer();
                String text = null;
                while((text = bufferedReader.readLine()) != null){
                    int p1 = text.indexOf('，');
                    int p2 = text.lastIndexOf('，');
                    int id = Integer.parseInt(text.substring(0, p1));
                    int x = Integer.parseInt(text.substring(p1+1, p2));
                    int y = Integer.parseInt(text.substring(p2 + 1, text.length()));
                    list.add(new Station(id,x,y));
                }
                return list;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static double creatStation3(double[][]map,HashMap<Integer,State> stationMap){
        int index1 = -1;
        int index2 = -1;//两个下标用于记录从一个站到另一个站
        double sumOfDistance = 0;
        double minDistance = 100000;//设置一个最小值，初值为10000
        int vertexSum = stationMap.size();
        for (int k = 0; k <vertexSum-13; k++) {
            for (Integer key1:stationMap.keySet()){
                for (Integer key2:stationMap.keySet()){
                    if(stationMap.get(key1).isVisited==1&&stationMap.get(key2).isVisited==0&&stationMap.get(stationMap.get(key1).id).weight+map[key1][key2]<40&&map[key1][key2]<minDistance){
                        index1 = key1;
                        index2 = key2;
                        minDistance = map[key1][key2];
                    }
                }
            }
            //双层for循环遍历完毕时，说明已经找到最小的节点
            //给后一个节点的距离赋值
            stationMap.get(index2).weight = map[index1][index2];
            stationMap.get(index2).id = stationMap.get(index1).id;
            stationMap.get(stationMap.get(index2).id).weight+=stationMap.get(index2).weight;
            stationMap.get(index1).nexts.add(stationMap.get(index2));
            //System.out.println(stationMap.get(index2));
            //System.out.println("节点"+index1+"->"+"节点"+index2+"距离"+map[index1][index2]);
            stationMap.get(index2).isVisited=1;
            sumOfDistance += minDistance;
            //重新设置为最大值
            minDistance = 100000;
        }
        return sumOfDistance;

    }
}
class Station{
    public int id;
    public int x;
    public int y;
    public Station(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
class State{
    int id;
    double weight;
    int isVisited;
    ArrayList<State> nexts = new ArrayList<>();

    public State(int id, int isVisited) {
        this.id = id;
        this.isVisited = isVisited;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", weight=" + weight +
                ", isVisited=" + isVisited +
                ", nexts=" + nexts +
                '}';
    }
}