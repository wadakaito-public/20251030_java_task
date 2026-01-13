import java.util.*;
public class JavaTask20251030 {
    public static void main(String[] args){
        Scanner sc= new Scanner(System.in);
        if(!sc.hasNext()){
            System.out.println("数字,基数,変換後の基数が入力されていません");
            return;
        }
        String input = sc.next();
        if(!sc.hasNextInt()){
            System.out.println("基数,変換後の基数が入力されていません");
            return;
        }
        int base = sc.nextInt();
        if(!sc.hasNextInt()){
            System.out.println("変換後の基数が入力されていません");
            return;
        }
        int toBase = sc.nextInt();
        calc(input,base,toBase,true);
    }
    
    public static void calc(String input,int base, int toBase ,boolean firstCall){
        boolean baseToF = false;
        boolean toBaseToF = false;
        int n = 0 ;//数値計算用に引数を格納する場所
        ArrayList<Integer> digits = new ArrayList<>();//基数を落とすときに使う桁記録用の配列
        if(firstCall == true) System.out.println(input +"("+base+")を"+toBase+"進数に変換");
        if(input.equals("0")){
            System.out.println("0("+toBase+")");
                return;
        }
        if(base == 2 || base == 10 || base == 16)baseToF = true;
            else System.out.println("未対応の基数です");
        if(toBase == 2 || toBase == 10 || toBase == 16)toBaseToF = true;
            else System.out.println("この基数への変換は未対応です");
            
        //16進以外なら整数型にする
        if(base != 16)n = Integer.parseInt(input);
        //変換なし
        if(base == toBase){
            System.out.println("変換前と変換後の基数の指定が同じなため変換は行いませんでした。");
            return;
            }
        

        if(baseToF == true && toBaseToF == true){
            //16進の場合、一度10進にする
            if(base == 16){
                int sum = 0;
                for(int i = 0; i < input.length(); i++){
                    //末尾位置文字だけ取り出す
                    String digitStr = input.substring(input.length() - 1 - i, input.length() - i );
                    digits.add(Integer.parseInt(convert(digitStr,"10")));
                    sum += (digits.get(i)*Math.pow(base,i));
                }
                
                if(toBase == 10){//変換完了
                    System.out.println(sum+"("+toBase+")");
                    return;
                }else{//もう一度変換が必要なため再帰呼び出し
                    calc(Integer.toString(sum),10,toBase,false);
                    return;
                }
            }
            //ひたすら割る（10進→2進、10進→16進）
            if(base > toBase || (base == 10 && toBase == 16) ){
                String out = "";
                for(int i = 0; n > 0 ; i++){
                    int mod = n%toBase;
                    if(toBase == 16 && mod >= 10){
                        out = convert(Integer.toString(mod),"16") + out;
                    }else{
                        out = Integer.toString(mod) + out;
                    }
                    n /= toBase;
                }
                System.out.println(out+"("+toBase+")");
                return;
            }
            //基数を増やす(10進に一度変換)
            if(base < toBase){
                int sum = 0;
                for(int i = 0; n > 0; i++){
                digits.add(n % 10);
                    n /=10;
                    sum += (digits.get(i)*Math.pow(base,i));
                }
                if(toBase == 10 ){
                    System.out.println(sum+"("+toBase+")");
                    return;
                }else//まだ変換したいので再帰
                calc(Integer.toString(sum),10,toBase,false);
                return;
            }
        }
    }
    
    //16進、10進を変換 convert((str)変換対象,(str)変換後の基数)
    public static String convert(String input,String to){
        String output = "error!!";
        String[][] hexToDec ={
            {"10","11","12","13","14","15"},{"A","B","C","D","E","F"}
        };
        
        //16進、10進か？正規表現で判断
        if(input.matches("^([A-F]|1[0-5]|[0-9])$")){
            if(to.equals("10")){  //10進変換
                for(int i = 0; i < hexToDec[0].length; i++){
                    if(input.equals(hexToDec[1][i])){
                        return hexToDec[0][i];
                        
                    }
                }
                return input;//10未満はそのまま返す
            }
            if(to.equals("16")){  //16進変換
                for(int i = 0; i < hexToDec[0].length; i++){
                    if(input.equals(hexToDec[0][i])){
                        return hexToDec[1][i];
                    }
                }
                return input;//10未満はそのまま返す
            }
        }
        return output; //error!!
    }
}