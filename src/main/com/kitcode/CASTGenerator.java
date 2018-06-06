package com.kitcode;

import java.io.File;
import cantlr.CLexer;
import cantlr.CParser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.nio.charset.Charset;
import java.nio.file.Files;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

public class CASTGenerator {

    public ArrayList<String> LineNum = new ArrayList<String>();
    public ArrayList<String> Type = new ArrayList<String>();
    public ArrayList<String> Content = new ArrayList<String>();
    public ArrayList<String> kgram=new ArrayList<String>();
    static ArrayList<String> FileList=new ArrayList<String>();
    static ArrayList<String> DirectoryList=new ArrayList<String>();
    static private int k;
    //static private String num;
    private String readFile(String num) throws IOException {
        File file = new File(num);
        byte[] encoded = Files.readAllBytes(file.toPath());
        return new String(encoded, Charset.forName("UTF-8"));
    }
    private void writeFile(String num) throws IOException{
        File file=new File(num);
        OutputStream outputStream=new FileOutputStream(file);
        for(String tmp:Type) {
            byte[] data=tmp.getBytes();
            outputStream.write(data);
            outputStream.write('\n');
        }
        outputStream.close();
    }
    static ArrayList<String> readFileList(String path) throws IOException
    {
        ArrayList<String> tmp=new ArrayList<String>();
        File file=new File(path);
        File[] tempList=file.listFiles();
        for(int i=0;i<tempList.length;i++)
        {
            if(tempList[i].isFile())
            {
                tmp.add(tempList[i].getName());
            }
        }
        return tmp;
    }
    static ArrayList<String> readDirectoryList(String path) throws IOException
    {
        ArrayList<String> tmp=new ArrayList<String>();
        File file=new File(path);
        File[] tempList=file.listFiles();
        for(int i=0;i<tempList.length;i++)
        {
            if(tempList[i].isDirectory())
            {
                tmp.add(tempList[i].getName());
               // System.out.println(tempList[i].getName());
            }
        }
        return tmp;
    }
    public static void main(String args[]) throws IOException {
        k = 3;
        //num="8";
        String path = "resource/train/";
        DirectoryList = readDirectoryList(path);
        for (int k = 0; k < DirectoryList.size(); k++) {
            FileList = readFileList(path + DirectoryList.get(k) + "/");
            String newpath = "resource/trainoutfile/" + DirectoryList.get(k) + "/";
            File folder = new File(newpath);
            if (!folder.exists() && !folder.isDirectory()) {
                System.out.println("文件夹路径不存在，创建路径:" +newpath);
                folder.mkdir();
            }
            for (int i = 0; i < FileList.size(); i++) {
                CASTGenerator castGenerator = new CASTGenerator();
                String inputString = castGenerator.readFile(path + DirectoryList.get(k) + "/"+ FileList.get(i));
                ANTLRInputStream input = new ANTLRInputStream(inputString);
                CLexer lexer = new CLexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                CParser parser = new CParser(tokens);
                ParserRuleContext ctx = parser.compilationUnit();
                castGenerator.generateAST(ctx, false, 0);
                castGenerator.writeFile(newpath + FileList.get(i));
                /*System.out.println("digraph G {");
                printDOT();
                System.out.println("}");*/
            }
        }
    }
    private void generateAST(RuleContext ctx, boolean verbose, int indentation) {
        boolean toBeIgnored = !verbose && ctx.getChildCount() == 1 && ctx.getChild(0) instanceof ParserRuleContext;

        if (!toBeIgnored) {
            String ruleName = CParser.ruleNames[ctx.getRuleIndex()];
            LineNum.add(Integer.toString(indentation));
            Type.add(ruleName);
            Content.add(ctx.getText());
        }
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree element = ctx.getChild(i);
            if (element instanceof RuleContext) {
                generateAST((RuleContext) element, verbose, indentation + (toBeIgnored ? 0 : 1));
            }
        }
    }

    private void printDOT(){
        printLabel();
        int pos = 0;
        for(int i=1; i<LineNum.size();i++){
            pos=getPos(Integer.parseInt(LineNum.get(i))-1, i);
            System.out.println((Integer.parseInt(LineNum.get(i))-1)+Integer.toString(pos)+"->"+LineNum.get(i)+i);
        }
    }
    private void printLabel(){
        for(int i =0; i<LineNum.size()-k; i++){
            String tmp=Type.get(i);
            int test=Type.get(i).hashCode();
            for(int j=i+1;j<i+k;j++)
            {
                tmp+=Type.get(j);
            }
           /* String tmp="";
            tmp=Type.get(i)+Type.get(i+1)+Type.get(i+2);*/
            kgram.add(tmp);
            System.out.println(kgram.get(i));
           // System.out.println(LineNum.get(i)+i+"[label=\""+Type.get(i)+"\\n "+Content.get(i)+" \"]");
        }
    }

    private  int getPos(int n, int limit){
        int pos = 0;
        for(int i=0; i<limit;i++){
            if(Integer.parseInt(this.LineNum.get(i))==n){
                pos = i;
            }
        }
        return pos;
    }
}
