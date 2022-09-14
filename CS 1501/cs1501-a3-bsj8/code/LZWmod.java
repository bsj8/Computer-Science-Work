/*************************************************************************
 *  Compilation:  javac LZWmod.java
 *  Execution:    java LZWmod - < input.txt   (compress)
 *  Execution:    java LZWmod + < input.txt   (expand)
 *  Dependencies: BinaryStdIn.java BinaryStdOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *
 *************************************************************************/
import java.lang.Math;

public class LZWmod {
    private static final int R = 256;        // number of input chars]
    /**
    * @param userWantsReset is first parameter of compress method
    * for the compress method I made sure that a StringBuilder was being used to look up values in they symbol table
    * you can add or delete from a StringBuilder in constant time but with a string it is o(n) time
    * If the symbol table was full then I increased the code word width so that we can have more codewords
    * The max code word width is 16 bits, if the user wants to reset the code book then new code words will be added
    * when the user wants to reset this also means resetting the code word width
    */

    public static void compress(boolean userWantsReset) {
      int W=9;
      int L=(int) Math.pow(2,W);
        TSTmod<Integer> st = new TSTmod<Integer>();
        for (int i = 0; i < R; i++)
        // here a StringBuilder is used rather than a string
            st.put(new StringBuilder("" + (char) i), i);
        int code = R+1;  // R is codeword for EOF

        // if user wants reset, write a flag/sentinel at the beginning of compressed output
        if(userWantsReset){
          BinaryStdOut.write(true);
        }else{
          BinaryStdOut.write(false);
        }

        //initialize the current string
        StringBuilder current = new StringBuilder();
        //read and append the first char
        char c = BinaryStdIn.readChar();
        current.append(c);
        Integer codeword = st.get(current);
        while (!BinaryStdIn.isEmpty()) {
            codeword = st.get(current);
            c= BinaryStdIn.readChar();
            current.append(c);
            if(!st.contains(current)){
              BinaryStdOut.write(codeword, W);
              if (code < L){    // Add to symbol table if not full
                  st.put(current, code++);
                }else{ // symbol table is full
                  if(W<16){ // can still expand codeword width
                    W++;
                    L=(int) Math.pow(2,W);
                        st.put(current, code++);
                   } else if (userWantsReset) { // codebook full and codeword width cannot be expanded anymore; reset if user specified
                     // reset w and L and rest code codebook
                     // refill codebook with original 256 characters
                     int ratio= (current.length-1*8)/32;
                     System.out.println("This si the ratio" + ratio);
                     W=9;
                     L=(int) Math.pow(2,W);
                     st= new TSTmod<Integer>();
                     for (int i = 0; i < R; i++)
                         st.put(new StringBuilder("" + (char) i), i);

                    code = R+1;
                    st.put(current,code++);
                   }
                }
              current = new StringBuilder();
              current.append(c);

            }
        }

        BinaryStdOut.write(st.get(current),W);
        BinaryStdOut.write(R, W); //Write EOF
        BinaryStdOut.close();
    }


    public static void expand() {
      int W=9;
      int L=(int) Math.pow(2,W);
        String[] st = new String[65536];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        // check if codeword is a flag/sentinel indicating that resetting took place during compress()
        boolean check=BinaryStdIn.readBoolean();


        int codeword = BinaryStdIn.readInt(W);


        String val = st[codeword];


        while (true) {
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L)
            { st[i++] = val + s.charAt(0);
              if(i==L){// codebook is full
                if(W<16){// if codeword width is less than 16 bit then increment the width and update L
                  W++;
                  L=(int) Math.pow(2,W);
                }else if(check){
                  W=9;
                  L=(int) Math.pow(2,W);
                  st= new String[65536];
                  for (i = 0; i < R; i++)
                      st[i] = "" + (char) i;
                  st[i++] = "";

                }
              }
            }else{
              if(W<16){
                W++;
                 L=(int) Math.pow(2,W);
                if (i < L)
                {
                  st[i++] = val + s.charAt(0);

                }
              }else if(check){ // if user wants to reset then rest W and L as well as the code book then refill code book with 256 characters
                W=9;
                L=(int) Math.pow(2,W);
                st= new String[65536];
                for (i = 0; i < R; i++)
                    st[i] = "" + (char) i;
                st[i++] = "";
                st[i++] = val + s.charAt(0);

              }
            }
            val = s;

        }
        BinaryStdOut.close();
    }



    public static void main(String[] args) {
      boolean  userWantsReset=false;
        if(args.length>1 && args[1].equals("r")){
            userWantsReset=true;
        }
        if      (args[0].equals("-")) compress(userWantsReset);
        else if (args[0].equals("+")) expand();
        else throw new RuntimeException("Illegal command line argument");
    }

}
