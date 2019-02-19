package com.kitcode;

import com.sun.istack.internal.FinalArrayList;

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import static com.kitcode.CASTGenerator.readDirectoryList;
import static com.kitcode.CASTGenerator.DirectoryList;
import static com.kitcode.CASTGenerator.readFileList;
import static com.kitcode.CASTGenerator.FileList;
public class CompareTwoCfile {
    public ArrayList<String> C1 = new ArrayList<String>();
    public ArrayList<String> C2 = new ArrayList<String>();
    public ArrayList<String> newC1 = new ArrayList<String>();
    public ArrayList<String> newC2 = new ArrayList<String>();
    static ArrayList<String> FileList2=new FinalArrayList<String>();
    static private int k;
    static private int txtnum;
    public ArrayList<String> readfile(String num)throws IOException
    {
       // File file=new File(("resource/outfile/"+num+".txt"));
        FileReader fileReader=new FileReader(num);
        //InputStream inputStream=new FileInputStream(file);
        BufferedReader reader=new BufferedReader(fileReader);
        ArrayList<String> read=new ArrayList<String>();
        String line="";
        while((line=reader.readLine())!=null)
            read.add(line);
        reader.close();
        return read;
    }
    public ArrayList<String> handleList(ArrayList<String> C)
    {
        ArrayList<String> test = new ArrayList<String>();
        for(int i=0;i<C.size()-k;i++)
        {
            String tmp=C.get(i);
            for(int j=i+1;j<i+k;j++)
            {
                tmp+=" "+C.get(j);
            }
           test.add(tmp);
        }
        return test;
    }
    public int countSimilar(ArrayList<String> K1,ArrayList<String> K2)
    {
        int count=0;
        for(int i=0;i<K1.size();i++)
        {
            if(K2.contains(K1.get(i)))
                count++;
            else
                continue;
        }
        return count;
    }
    public String NeedlemanWunsch(ArrayList<String> K1,ArrayList<String> K2)
    {
        int max=0;
        int [][]h =new int[K1.size()+1][K2.size()+1];
        for(int i=0;i<=K1.size();i++)
            h[i][0]=0;
        for(int j=0;j<K2.size();j++)
            h[0][j]=0;
        int size=K1.size()>K2.size()?K1.size():K2.size();
        for(int i=1;i<K1.size();i++)
        {
            for(int j=1;j<K2.size();j++) {
                if(K1.get(i-1).equals(K2.get(j-1))) {
                    h[i][j]=h[i-1][j-1]+1;
                    if(h[i][j]>max)
                        max=h[i][j];
                }
                else {
                    h[i][j] = (h[i - 1][j] >= h[i][j - 1] ? h[i - 1][j]
                            : h[i][j - 1]);
                    if(h[i][j]>max)
                        max=h[i][j];
                }
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        //System.out.println(test1.size()+"  "+test2.size());
        return df.format((double)max/(double)size);
    }
    public String yuxian(ArrayList<String> K1,ArrayList<String> K2)
    {
        ArrayList<String> test = new ArrayList<String>();
        ArrayList<Integer> t1 = new ArrayList<Integer>();
        ArrayList<Integer> t2 = new ArrayList<Integer>();
        for(String t:K1) {
            if(test.contains(t)==false)
                test.add(t);
        }
        for(String t:K2) {
            if(test.contains(t)==false)
                test.add(t);
        }
        for(String t:test) {
            int count=0;
            for(String tmp:K1) {
                if(tmp.equals(t))
                    count++;
            }
            t1.add(count);
            int count1=0;
            for(String tmp1:K2) {
                if(tmp1.equals(t))
                    count1++;
            }
            t2.add(count1);
        }
        int son=0;
        double mon1=0;
        double mon2=0;
        for(int i=0;i<test.size();i++) {
            son += t1.get(i) * t2.get(i);
            mon1+=t1.get(i)*t1.get(i);
            mon2+=t2.get(i)*t2.get(i);
        }
        double mon=Math.sqrt(mon1)* Math.sqrt(mon2);
        DecimalFormat df = new DecimalFormat("0.0000");
        df.setRoundingMode(RoundingMode.HALF_UP);
        //System.out.println(test1.size()+"  "+test2.size());
        return df.format((double)son/(double)mon);
    }
    public String naiveRead(String num)throws Exception
    {
        FileReader fileReader=new FileReader(num);
        //InputStream inputStream=new FileInputStream(file);
        BufferedReader reader=new BufferedReader(fileReader);
        String read="";
        String line="";
        while((line=reader.readLine())!=null)
        {
            line.replace(" ","");
            line.replace(";","");
            read+=line;
        }
        reader.close();
        return read;
    }
    public String naiveCompare(String path1,String path2)throws Exception
    {
        String K1=this.naiveRead(path1);
        String K2=this.naiveRead(path2);
        //System.out.println(K1);
        int max=0;
        int [][]h =new int[K1.length()+1][K2.length()+1];
        for(int i=0;i<=K1.length();i++)
            h[i][0]=0;
        for(int j=0;j<K2.length();j++)
            h[0][j]=0;
        //int size=K1.length()>K2.length()?K1.length():K2.length();
        int size=(K1.length()+K2.length())/2;
        for(int i=1;i<K1.length();i++)
        {
            for(int j=1;j<K2.length();j++) {
                if(K1.substring(i-1,i).equals(K2.substring(j-1,j))) {
                    h[i][j]=h[i-1][j-1]+1;
                    if(h[i][j]>max)
                        max=h[i][j];
                }
                else {
                    h[i][j] = (h[i - 1][j] >= h[i][j - 1] ? h[i - 1][j]
                            : h[i][j - 1]);
                    if(h[i][j]>max)
                        max=h[i][j];
                }
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        //System.out.println(test1.size()+"  "+test2.size());
        return df.format((double)max/(double)size);
    }
    public String jaccard(ArrayList<String> K1,ArrayList<String> K2)
    {
        ArrayList<String> test1 = new ArrayList<String>();
        ArrayList<String> test2 = new ArrayList<String>();
     /*   for(String t:K2)
        {
            if(test2.contains(t)==false)
                test2.add(t);
        }*/
        for(int i=0;i<K1.size();i++)
        {
            if(K2.contains(K1.get(i))) {
               // if(test1.contains(K1.get(i))==false)
                    test1.add(K1.get(i));
            }
        }
        for(int i=0;i<K2.size();i++)
        {
            if(K1.contains(K2.get(i))) {
                // if(test1.contains(K1.get(i))==false)
                test2.add(K2.get(i));
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        double temp=test1.size()+test2.size();
        //System.out.println(((double)(test1.size()+test2.size())/2));
        //System.out.println(test1.size()+"  "+test2.size());
        return df.format((double)(temp/2)/(double)(K1.size()+K2.size()-test1.size()));
    }

    public float getDistance(String source, String target) {
        final int sl = source.length();
        final int tl = target.length();

        if (sl == 0 || tl == 0) {
            if (sl == tl) {
                return 1;
            }
            else {
                return 0;
            }
        }
        int n=100;
        int cost = 0;
        if (sl < n || tl < n) {
            for (int i=0,ni=Math.min(sl,tl);i<ni;i++) {
                if (source.charAt(i) == target.charAt(i)) {
                    cost++;
                }
            }
            return (float) cost/Math.max(sl, tl);
        }
        char[] sa = new char[sl+n-1];
        float p[]; //'previous' cost array, horizontally
        float d[]; // cost array, horizontally
        float _d[]; //placeholder to assist in swapping p and d
        //construct sa with prefix
        // 填充前缀，满足n-gram
        for (int i=0;i<sa.length;i++) {
            if (i < n-1) {
                sa[i]=0; //add prefix
            }
            else {
                sa[i] = source.charAt(i-n+1);
            }
        }
        p = new float[sl+1];
        d = new float[sl+1];
        // indexes into strings s and t
        int i; // iterates through source
        int j; // iterates through target
        char[] t_j = new char[n]; // jth n-gram of t
        // 初始化第一横排的编辑距离
        for (i = 0; i<=sl; i++) {
            p[i] = i;
        }
        for (j = 1; j<=tl; j++) { // 开始处理第二个横排，...到tl最后一个横排
            //construct t_j n-gram，构建n-gram
            if (j < n) { // 补充前缀
                for (int ti=0;ti<n-j;ti++) {
                    t_j[ti]=0; //add prefix
                }
                for (int ti=n-j;ti<n;ti++) {
                    t_j[ti]=target.charAt(ti-(n-j));
                }
            }
            else { // 直接取n-gram
                t_j = target.substring(j-n, j).toCharArray();
            }
            d[0] = j;
            for (i=1; i<=sl; i++) {
                cost = 0;
                int tn=n;
                //compare sa to t_j，计算f(i,j)
                for (int ni=0;ni<n;ni++) {
                    if (sa[i-1+ni] != t_j[ni]) {
                        cost++;
                    }
                    else if (sa[i-1+ni] == 0) { //discount matches on prefix
                        tn--;
                    }
                }
                float ec = (float) cost/tn;
                // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
                d[i] = Math.min(Math.min(d[i-1]+1, p[i]+1),  p[i-1]+ec);
            }
            // copy current distance counts to 'previous row' distance counts
            _d = p;
            p = d;
            d = _d;
        }
        // our last action in the above loop was to switch d and p, so p now
        // actually has the most recent cost counts
        return 1.0f - ((float) p[sl] / Math.max(tl, sl));
    }
    public void useModel(String path,String readcsv,String writecsv) throws Exception
    {
        BufferedReader reader = new BufferedReader(new FileReader(readcsv));
        BufferedWriter writer = new BufferedWriter(new FileWriter(writecsv));
        writer.write(reader.readLine());
        writer.newLine();

        String line = null;
        while((line=reader.readLine())!=null) {
            String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
            String file[] = item[0].split("_");
            String filename1 = new String(path+"\\\\"+file[0].replace("\"","")+".txt");
            String filename2 = new String(path+"\\\\"+file[1].replace("\"","")+".txt");
            //int value = Integer.parseInt(last);//如果是数值，可以转化为数值
            // System.out.println(filename1+" "+filename2);
            //System.out.println(file[0]+" "+file[1]);
            //Instance instance=FeatureExtract.makeInstance(FeatureExtract.featureExtract(filename1,filename2,varfile1,varfile2));
            CompareTwoCfile compareTwoCfile=new CompareTwoCfile();
            compareTwoCfile.C1 = compareTwoCfile.readfile(filename1);
            compareTwoCfile.C2 = compareTwoCfile.readfile(filename2);
            compareTwoCfile.newC1 = compareTwoCfile.handleList(compareTwoCfile.C1);
            compareTwoCfile.newC2 = compareTwoCfile.handleList(compareTwoCfile.C2);
            String s = compareTwoCfile.jaccard(compareTwoCfile.newC1, compareTwoCfile.newC2);
            System.out.println(s);
            double predicted = Double.parseDouble(s);
            if (predicted > 0.3)
                predicted = 1;
            else
                predicted = 0;
            writer.write(item[0] + "," + (int) predicted);
            writer.newLine();
            writer.flush();
              //  }
          //  }
        }
        reader.close();
        writer.close();

    }

    public static void main(String args[]) throws Exception
    {
        int k=5;
        txtnum=499;
        String path="resource/trainoutfile/";
        //String path="resource/train/";
        DirectoryList = readDirectoryList(path);
        //for(int i1=0;i1<DirectoryList.size();i1++){
        String path0=path+DirectoryList.get(0)+"/";
        FileList2=readFileList(path0);
        String path1=path+DirectoryList.get(1)+"/";
        FileList=readFileList(path1);
       // String path3=path1+FileList.get(3);
       // CompareTwoCfile compareTwoCfile=new CompareTwoCfile();
       // String path1=path+DirectoryList.get(0)+"/"+0+".txt";
        //CompareTwoCfile compareTwoCfile=new CompareTwoCfile();
        //.useModel("resource/test/", "resource/sample_submission.csv", "resource/1.csv");
        for(int j=0;j<50;j++){
            String path3=path0+FileList2.get(j);
             int count=0;
            for(int i=0;i<50;i++)
            {
                String path2=path0+FileList2.get(i);
                CompareTwoCfile compareTwoCfile=new CompareTwoCfile();
                compareTwoCfile.C1=compareTwoCfile.readfile(path3);
                compareTwoCfile.C2=compareTwoCfile.readfile(path2);
                compareTwoCfile.newC1=compareTwoCfile.handleList(compareTwoCfile.C1);
                compareTwoCfile.newC2=compareTwoCfile.handleList(compareTwoCfile.C2);
                String i2=compareTwoCfile.jaccard(compareTwoCfile.newC1,compareTwoCfile.newC2);
                String i3=compareTwoCfile.NeedlemanWunsch(compareTwoCfile.newC1,compareTwoCfile.newC2);
                String i4=compareTwoCfile.yuxian(compareTwoCfile.newC1,compareTwoCfile.newC2);
                //String path6="resource/test/0a5f9c6754984136.txt";
                //String  path7="resource/test/0a4bdb125e7846e9.txt";
                String K1=compareTwoCfile.naiveRead(path2);
                String K2=compareTwoCfile.naiveRead(path3);
                System.out.println(compareTwoCfile.getDistance(K1,K2));
                //String naiv=compareTwoCfile.naiveCompare(path2,path3);
                //if(Double.parseDouble(naiv)>0.4)
                  //   {System.out.println(naiv);}
                if(Double.parseDouble(i2)>0.3) {
                    count++;
                    //System.out.println("!!!!!!!!!!!!!!!!!!!!!");
                    //System.out.println("FileSizeCompare: "+compareTwoCfile.newC1.size()+"  ||||    "+compareTwoCfile.newC2.size());
                    //System.out.println("txt:"+i + ": "  + "  jaccard: " + i2+"  needleman: "+i3+"  yuxian: "+i4);
                }
                //System.out.println(i4);
            }
        System.out.println(count);
        }
    }
    }

