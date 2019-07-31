package com.zhouhao.study.concurrency;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对输入值和其平方值进行缓存,当相同输入值请求时,直接返回已缓存的平方值,而不用再次计算.
 *
 * @author : zhouhao
 * @date : Created in 2019/7/31 10:52
 */
public class CashSquareValue2 {
    /**
     * 平方值缓存Map
     */
    private ConcurrentHashMap<Integer,Integer> squareValueMap = new ConcurrentHashMap<>();

    public int getSquareValue(int value) throws InterruptedException {
        if(squareValueMap.containsKey(value)){
            Thread.sleep(1000);
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
        CashSquareValue2 cashSquareValue = new CashSquareValue2();
        for(int i = 0;i < 8;i ++){
            Thread thread = new Thread(new CalculateThread2(cashSquareValue));
            thread.start();
        }
    }


}

class CalculateThread2 implements Runnable{
    CashSquareValue2 cashSquareValue;

    CalculateThread2(CashSquareValue2 cashSquareValue){
        this.cashSquareValue = cashSquareValue;
    }

    @Override
    public void run() {
        Random random = new Random();
        while(true){
            try {
                cashSquareValue.getSquareValue(random.nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
