package com.kitcode;

import com.sun.istack.internal.FinalArrayList;

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

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
    public String jaccard(ArrayList<String> K1,ArrayList<String> K2)
    {
        ArrayList<String> test1 = new ArrayList<String>();
        ArrayList<String> test2 = new ArrayList<String>();
        for(String t:K2)
        {
            if(test2.contains(t)==false)
                test2.add(t);
        }
        for(int i=0;i<K1.size();i++)
        {
            if(K2.contains(K1.get(i))) {
                if(test1.contains(K1.get(i))==false)
                    test1.add(K1.get(i));
            }
            else
                test2.add(K1.get(i));
        }
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        //System.out.println(test1.size()+"  "+test2.size());
        return df.format((double)test1.size()/(double)test2.size());
    }
    public static void main(String args[]) throws IOException
    {

        k=4;
        txtnum=499;
        String path="resource/trainoutfile/";
        DirectoryList = readDirectoryList(path);
        //for(int i1=0;i1<DirectoryList.size();i1++){
        String path0=path+DirectoryList.get(0)+"/";
        FileList2=readFileList(path0);
        String path1=path+DirectoryList.get(1)+"/";
        FileList=readFileList(path1);
        String path3=path0+FileList2.get(8);
       // String path1=path+DirectoryList.get(0)+"/"+0+".txt";
            for(int i=0;i<txtnum;i++)
            {
                String path2=path1+FileList.get(i);
                CompareTwoCfile compareTwoCfile=new CompareTwoCfile();
                compareTwoCfile.C1=compareTwoCfile.readfile(path3);
                compareTwoCfile.C2=compareTwoCfile.readfile(path2);
                compareTwoCfile.newC1=compareTwoCfile.handleList(compareTwoCfile.C1);
                compareTwoCfile.newC2=compareTwoCfile.handleList(compareTwoCfile.C2);
                String i2=compareTwoCfile.jaccard(compareTwoCfile.newC1,compareTwoCfile.newC2);
                if(Double.parseDouble(i2)>0.38) {
                    String i3=compareTwoCfile.NeedlemanWunsch(compareTwoCfile.newC1,compareTwoCfile.newC2);
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println("FileSizeCompare: "+compareTwoCfile.newC1.size()+"  ||||    "+compareTwoCfile.newC2.size());
                    System.out.println("txt:"+i + ": "  + "  jaccard: " + i2+"  needleman: "+i3);
                }
                //System.out.println(i4);
            //}
        }
/*        for(int i=0;i<newC1.size();i++)
            System.out.println(newC1.get(i));
        System.out.println("============newC2============");
        for(int i=0;i<newC2.size();i++)
            System.out.println(newC2.get(i));*/
    }
}
