package sikuli.compilador.alpha3;

import java.util.ArrayList;
import java.util.Arrays;

public class AnalisadorLexico {
	
	private static final int NAO_STRING = 0;
	private static final int STRING_ASPAS_SIMPLES = 1;
	private static final int STRING_ASPAS_DUPLAS = 2;
	
	private static ArrayList<Character> separadores = new ArrayList<>(Arrays.asList('{','}','(',')','[',']',':',',','+','-','_','=','*','/','%'));
	private static ArrayList<Character> separadoresDecorativos = new ArrayList<>(Arrays.asList(' ','\n','\t','#'));

	public static ArrayList<String> scan(String codigoFonte) {
		ArrayList<String> tokens = new ArrayList<>();
		String token = "";
		int tipoToken = NAO_STRING;
		boolean comentario = false , ehSeparador;
		
		for (char c : codigoFonte.toCharArray()) {
			
			// tratamento de comentarios
			if(comentario && c != '\n')
				continue;
			else
				comentario = false;
			
			// tratamento de separadores
			ehSeparador = (separadores.contains(c) || separadoresDecorativos.contains(c)) && tipoToken == NAO_STRING; 
			if(ehSeparador) {
				if(!token.equals("")) {
					tokens.add(token);
					token = "";
				}

				if(separadores.contains(c))
					tokens.add(String.valueOf(c));
				
				if(c == '#')
					comentario = true;
				
				if(c == '\n')
					tokens.add("\n");

			} else {
				token += c;
			}
			
			// tratamento de Strings
			if(c == '"') {
				if(tipoToken == STRING_ASPAS_DUPLAS)
					tipoToken = NAO_STRING;
				else if(tipoToken == NAO_STRING)
					tipoToken = STRING_ASPAS_DUPLAS;
			} else if(c == '\'') {
				if(tipoToken == STRING_ASPAS_SIMPLES)
					tipoToken = NAO_STRING;
				else if(tipoToken == NAO_STRING)
					tipoToken = STRING_ASPAS_SIMPLES;
			}
		}
		
		// caso o codigo acabe com um caracter nao-separador (ou seja, ha um ultimo token que nao foi adicionado a lista de tokens)
		if(!token.equals("")) {
			tokens.add(token);
			tokens.add("\n");
		}
		
		return tokens;
	}

}
