import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

public class A1_RSA_Kulkirat {

	public static void main(String[] args) {
		BigInteger p = new BigInteger("19211916981990472618936322908621863986876987146317321175477459636156953561475008733870517275438245830106443145241548501528064000686696553079813968930084003413592173929258239545538559059522893001415540383237712787805857248668921475503029012210091798624401493551321836739170290569343885146402734119714622761918874473987849224658821203492683692059569546468953937059529709368583742816455260753650612502430591087268113652659115398868234585603351162620007030560547611");
		BigInteger q = new BigInteger("49400957163547757452528775346560420645353827504469813702447095057241998403355821905395551250978714023163401985077729384422721713135644084394023796644398582673187943364713315617271802772949577464712104737208148338528834981720321532125957782517699692081175107563795482281654333294693930543491780359799856300841301804870312412567636723373557700882499622073341225199446003974972311496703259471182056856143760293363135470539860065760306974196552067736902898897585691");
		
		BigInteger n = p.multiply(q); //n = p * q;
		
		BigInteger p_Minus_1 = p.subtract(BigInteger.ONE); // p-1
		BigInteger q_Minus_1 = q.subtract(BigInteger.ONE); // q-1
		BigInteger phi_n = p_Minus_1.multiply(q_Minus_1);  // (p-1)*(q-1)
		
		System.out.println("TEST");
		BigInteger e;
		//equivalent to this: for (int e = 2; e < z; e++)
        for( e = BigInteger.TWO; e.compareTo(phi_n) <=0; e = e.add(BigInteger.ONE)) {
        	
        	if(e.gcd(phi_n) == BigInteger.valueOf(1)) {
        		System.out.println("YES");
        		break;
        	} else {
        		do {
        			/*
        			 * Could use Random() as well but Random() is not cryptographically strong. 
        			 * Therefore I am using SecureRandom()
        			 * Please note I used the following reference to learn about Random() vs SecureRandom()
        			 * Reference used - https://www.geeksforgeeks.org/random-vs-secure-random-numbers-java/
        			 */
        			e = BigInteger.probablePrime(1538, new SecureRandom());
        		} while (e.gcd(phi_n) == BigInteger.valueOf(1));
        	}
        }
        
	
    	System.out.println("TEST2	");
		

		BigInteger d = e.modInverse(phi_n);
		
		String abcd = "ABCD";
		String random_msg = RandomMessage(abcd); //just scrambling the string to have a random message on each run of the program

		System.out.println("The encryption exponent e is: " + e);
		System.out.println("Composite modulus n value is: " +n);
		System.out.println("The decryption exponent d is: " + d );
		
		System.out.println("\nOriginal Message to be encryted is: " + random_msg);
		
		//converting the String message to bytes
		byte [] random_msg_into_bytes = random_msg.getBytes();
		BigInteger m = new BigInteger(random_msg_into_bytes);

		BigInteger encryption_c = encryption(e, n, m); //c = m^e mod n 
		BigInteger decryption = decryption(d, n , encryption_c); //m' = c^d mod n
	 
		System.out.println("\nEncryption: " + encryption_c);
		System.out.println("Decryption: " + decryption);
	
		//Converting BigInteger to a byte array so that original message is constructed again
		String decrypted = "";
		byte [] decryptionByteArray = decryption.toByteArray();
		for(byte a: decryptionByteArray){
			decrypted += (char) a;
		}
		
		System.out.println("\nDecrypted text is: " + decrypted);

	}

	private static BigInteger encryption(BigInteger e, BigInteger n, BigInteger m) {
		BigInteger c = m.modPow(e, n); //encryption c = m^e mod n 
		return c;
	}

	private static BigInteger decryption(BigInteger d, BigInteger n, BigInteger c) {
		BigInteger m_dash = c.modPow(d, n); //m' = c^d mod n
		return m_dash;
	}


	private static String RandomMessage(String s) {
	    //scrambling the passed string and creating a random message
		int i=0;
	    char a[] = s.toCharArray();
		while(i < s.toCharArray().length) {
		    int j = new Random().nextInt(s.toCharArray().length);
		    //The swaps are happening down here:
	        char temp = a[i]; 
	        a[i] = a[j];  
	        a[j] = temp;
			i++;
		}
	    return new String(a);
	}

}
