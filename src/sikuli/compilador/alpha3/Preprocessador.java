package sikuli.compilador.alpha3;

import java.util.ArrayList;
import java.util.Arrays;

public class Preprocessador {
	
	private static final int NAO_STRING = 0;
	private static final int STRING_ASPAS_SIMPLES = 1;
	private static final int STRING_ASPAS_DUPLAS = 2;
	
	private static ArrayList<Character> delimitadores = new ArrayList<>(Arrays.asList('(','[','{','#'));
	
	public static String scan(String codigoFonte) {
		char delimitador = 'N'; // 'N' = nenhum delimitador
		int countDelimitadoresIguais = 0;
		String codigoPreprocessado = "";
		int tipoToken = NAO_STRING;
		
		for (char c : codigoFonte.toCharArray()) {
			
			if(delimitador == '#') {

				//DEBUG
//				System.out.print(c + "[" + delimitador + "]");
				
				if(c == '\n') {
//					System.out.print("-");
					delimitador = 'N';
				}
				continue;
			}
			
			if(codigoPreprocessado.length() == 0 || codigoPreprocessado.charAt(codigoPreprocessado.length() - 1) == '\n')
				if(c == '\n')
					continue;
			
			if(c == '#') {
				delimitador = '#';
				continue;
			}
			
			if(delimitador != 'N' && c == '\n')
				continue;
		
			if(delimitadores.contains(c)) {
				if(delimitador == 'N') {
					delimitador = c;
					countDelimitadoresIguais = 1;
				} else if (c == delimitador)
						countDelimitadoresIguais++;
			} else if(c == delimitadorDeFechamento(delimitador))
				countDelimitadoresIguais--;

			if(countDelimitadoresIguais == 0)
				delimitador = 'N';
			
			codigoPreprocessado += c;
		}
		
		return codigoPreprocessado;
	}
	
	private static char delimitadorDeFechamento (char delimitadorDeAbertura) {
		switch(delimitadorDeAbertura) {
			case '(':
				return ')';
			case '[':
				return ']';
			case '{':
				return '}';
			case '#':
				return '\n';
			default:
				return '\n';
		}
	}

}
