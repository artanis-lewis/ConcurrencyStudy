package com.zhouhao.study.concurrency;

import java.util.HashMap;
import java.util.Random;

/**
 * 对输入值和其平方值进行缓存,当相同输入值请求时,直接返回已缓存的平方值,而不用再次计算.
 *
 * @author : zhouhao
 * @date : Created in 2019/7/31 10:52
 */
public class CashSquareValue {
    /**
     * 平方值缓存Map
     */
    private HashMap<Integer,Integer> squareValueMap = new HashMap<>();

    public int getSquareValue(int value){
        if(squareValueMap.containsKey(value)){
            return squareValueMap.get(value);
        }else{
            System.out.println("暂未缓存平方值:" + value);
            int squareValue = value * value;
            squareValueMap.put(value,squareValue);
            return squareValue;
        }
    }

    public int getCashSize(){
        return squareValueMap.size();
    }

    public static void main(String[] args){
        CashSquareValue cashSquareValue = new CashSquareValue();
        for(int i = 0;i < 5;i ++){
            Thread thread = new Thread(new CalculateThread(cashSquareValue));
            thread.start();
        }
    }


}

class CalculateThread implements Runnable{
    CashSquareValue cashSquareValue;

    CalculateThread(CashSquareValue cashSquareValue){
        this.cashSquareValue = cashSquareValue;
    }

    @Override
    public void run() {
        Random random = new Random();
        while(true){
            cashSquareValue.getSquareValue(random.nextInt(10));
        }

    }
}
