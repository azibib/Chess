package chess.Board;

import java.util.HashSet;
import java.util.Iterator;

public class BoardUtils {

    public static int[] firstColumn =  {0,8,16,24,32,40,48,56};
    public static int[] secondColumn =  {1,9,17,25,33,41,49,57};
    public static int[] thirdColumn =  {2,10,18,26,34,42,50,58};
    public static int[] fourthColumn =  {3,11,19,27,35,43,51,59};
    public static int[] fifthColumn =  {4,12,20,28,36,44,52,60};
    public static int[] sixthColumn =  {5,13,21,29,37,45,53,61};
    public static int[] seventhColumn =  {6,14,22,30,38,46,54,62};
    public static int[] eigthColumn =  {7,15,23,31,39,47,55,63};

    private boolean touchingColumn(int position){
        int[] left = {0,8,16,24,32,40,48,56};
        int[] right = {7,15,23,31,39,47,55,63};

        for(int i : right){
            if(position==i){return true;}
        }
        for(int i : left){
            if(position==i){return true;}
        }
        return false;
    }

    

    public HashSet<Integer> getRow(int position){
        HashSet<Integer> row = new HashSet<>();
        if(position <= 7){
            for(int i =0;i<8;i++){
                row.add(i);
            }
            return row;
        }
        else if(position <=15){
            for(int i =8;i<16;i++){
                row.add(i);
            }
            return row;
        }
        else if(position <=23){
            for(int i =16;i<24;i++){
                row.add(i);
            }
            return row;
        }
        else if(position <=31){
            for(int i =24;i<32;i++){
                row.add(i);
            }
            return row;
        }
        else if(position <=39){
            for(int i =32;i<40;i++){
                row.add(i);
            }
            return row;
        }
        else if(position <=47){
            for(int i =40;i<48;i++){
                row.add(i);
            }
            return row;
        }
        else if(position <=55){
            for(int i =48;i<56;i++){
                row.add(i);
            }
            return row;
        }
        for(int i =56;i<64;i++){
            row.add(i);
        }
        return row;
    }


    public HashSet<Integer> getColomn(int position){
        HashSet<Integer> col = new HashSet<>();
        
        for(int i : firstColumn){
            if(i==position){
                for(int j : firstColumn){
                    col.add(j);
                }
                return col;
            }
           
        }
        for(int i : secondColumn){
            if(i==position){
                for(int j : secondColumn){
                    col.add(j);
                }
                return col;
            }
           
        }
        for(int i : thirdColumn){
            if(i==position){
                for(int j : thirdColumn){
                    col.add(j);
                }
                return col;
            }
            
        }
        for(int i : fourthColumn){
            if(i==position){
                for(int j : fourthColumn){
                    col.add(j);
                }
                return col;
            }
        }
        for(int i : fifthColumn){
            if(i==position){
                for(int j : fifthColumn){
                    col.add(j);
                }
                return col;
            }
            
        }
        for(int i : sixthColumn){
            if(i==position){
                for(int j : sixthColumn){
                    col.add(j);
                }
                return col;
            }
            
        }
        for(int i : seventhColumn){
            if(i==position){
                for(int j : seventhColumn){
                    col.add(j);
                }
                return col;
            }
            
        }
        
        for(int j : eigthColumn){
                col.add(j);
            }
        return col;
            
        

    }

    public String getColumnLabel(int pos){
        HashSet<Integer> set = getColomn(pos);

        if(set.contains(0)){
            return "a";
        }
        else if(set.contains(1)){
            return "b";
        }
        else if(set.contains(2)){
            return "c";
        }
        else if(set.contains(3)){
            return "d";
        }
        else if(set.contains(4)){
            return "e";
        }
        else if(set.contains(5)){
            return "f";
        }
        else if(set.contains(6)){
            return "g";
        }
        else{
            return "h";
        }

    }


    public int getRowLabel(int pos){
        HashSet<Integer> row = this.getRow(pos);
        if(row.contains(0)){
            return 1;
        }
        else if(row.contains(8)){
            return 2;
        }
        else if(row.contains(16)){
            return 3;
        }
        else if(row.contains(24)){
            return 4;
        }
        else if(row.contains(32)){
            return 5;
        }
        else if(row.contains(40)){
            return 6;
        }
        else if(row.contains(48)){
            return 7;
        }
        else{
            return 8;
        }
        
    }
    public boolean areOnSameDiagonal(int index1, int index2) {
        
        int row1 = index1 / 8;
        int col1 = index1 % 8;

        int row2 = index2 / 8;
        int col2 = index2 % 8;

        
        return Math.abs(row1 - row2) == Math.abs(col1 - col2);
    }


    public HashSet<Integer> getPiecesInBetween(int pieceOne, int pieceTwo){
        HashSet<Integer> set = new HashSet<>();
        boolean column = this.getColumnLabel(pieceTwo).equals(this.getColumnLabel(pieceOne));
        boolean row = this.getRowLabel(pieceTwo)==this.getRowLabel(pieceOne);
        
        int difference = pieceOne-pieceTwo;
        if(column){
           if(difference<0){
                int count=pieceOne+8;
                while(count<pieceTwo){
                    set.add(count);
                    count+=8;
                }
                return set;
           }
           else{
            int count=pieceOne-8;
                while(count>pieceTwo){
                    set.add(count);
                    count-=8;
                }
                return set;
           }
        
        }
        if(row){
            if(difference<0){
                int count=pieceOne+1;
                while(count<pieceTwo){
                    set.add(count);
                    count+=1;
                }
                return set;
           }
           else{
            int count=pieceOne-1;
                while(count>pieceTwo){
                    set.add(count);
                    count-=1;
                }
                return set;
           }
        }
        else{
            if(difference<0){
                if(areOnSameDiagonal(pieceOne,pieceTwo-7)){
                    int count = pieceTwo-7;
                    while(count>pieceOne){
                        set.add(count);
                        count-=7;
                    }
                    return set;
                }
                if(areOnSameDiagonal(pieceOne,pieceTwo-9)){
                    int count = pieceTwo-9;
                    while(count>pieceOne){
                        set.add(count);
                        count-=9;
                    }
                    return set;
                }

            }
            else{
                if(areOnSameDiagonal( pieceOne,pieceTwo+7)){
                    int count = pieceTwo+7;
                    while(count<pieceOne){
                        set.add(count);
                        count+=7;
                    }
                    return set;
                }
                int count = pieceTwo+9;
                while(count<pieceOne){
                    set.add(count);
                    count+=9;
                }
                return set;
                
            }
            
        }
        return set;
        



        

    }
}
