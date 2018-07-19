package sikuli.iniciarTeste;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Config {

	public final static String USUARIO_PC = "gustavo"; // aluno ou gustavo
	// public final static String enderecoRemotoConfiguracao = "https://raw.githubusercontent.com/gustavofl/Testes-Automatizados/master/config.txt";
	public final static String enderecoRemotoConfiguracao = "ftp://172.16.2.151:5555/config.txt";
	// public final static String enderecoRemotoJAR = "https://github.com/gustavofl/Testes-Automatizados/raw/master/teste.jar";
	public final static String enderecoRemotoJAR = "ftp://172.16.2.151:5555/teste.jar";
	
	private long inicio = 0;
	private long fim = 0;
	private String[] range = null;
	
	public Config() throws IOException, ConfiguracaoIncompletaException {
		lerArquivoConfig();
		if(!configCompleta()) {
			throw new ConfiguracaoIncompletaException();
		}
	}
	
	private boolean configCompleta() {
		if(inicio == 0 || fim == 0 || range == null)
			return false;
		return true;
	}

	private void lerArquivoConfig() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(getEnderecoLocalConfiguracao()));
		while(br.ready()){
			String[] linha = br.readLine().split("=");
			if(linha[0].equals("inicio") && linha.length>1) {
				inicio = Long.valueOf(linha[1]);
			}else if(linha[0].equals("fim") && linha.length>1) {
				fim = Long.valueOf(linha[1]);
			}else if(linha[0].equals("range") && linha.length>1) {
				range = linha[1].split(";");
			}
		}
		br.close();
	}
	
	public boolean contemIP(String ipString) throws IpLocalInvalidoException {
		int[] ip = getIP(ipString);
		
		for(int i=0 ; i<range.length ; i++) {
			String[] ipRange = range[i].split("\\.");
			if(ipRange.length != 4) {
				System.err.println("IP invalido no arquivo de configuracoes: "+ipRange);
				continue; // ip invalido
			}
			
			boolean contemIP = true;
			for(int j=0 ; j<4 ; j++) {
				int inicio, fim;
				inicio = Integer.parseInt(ipRange[j].split("-")[0]);
				if(ipRange[j].contains("-")) {
					fim = Integer.parseInt(ipRange[j].split("-")[1]);
				}else {
					fim = Integer.parseInt(ipRange[j].split("-")[0]);
				}
				
				if(inicio > fim || inicio > 255 || inicio < 0 || fim > 255 || fim < 0) {
					contemIP = false;
					System.err.println("IP invalido no arquivo de configuracoes: "+ipRange);
					break; // ip invalido
					
				}
				
				if(ip[j] < inicio || ip[j] > fim) {
					contemIP = false;
				}
			}
			
			if(contemIP)
				return true;
		}
		return false;
	}

	public int[] getIP(String ip) throws IpLocalInvalidoException {
		String[] ipLocalString = ip.split("\\.");
		if(ipLocalString.length != 4) {
			System.out.println(ipLocalString[0]);
			System.out.println(ipLocalString.length);
			throw new IpLocalInvalidoException();
		}
		int[] ipLocal = new int[4];
		for(int i=0 ; i<ipLocalString.length ; i++) {
			int octeto = Integer.parseInt(ipLocalString[i]);
			
			if(octeto > 255 || octeto < 0)
				throw new IpLocalInvalidoException();
			
			if(octeto >= 0 && octeto <= 255)
				ipLocal[i] = octeto;
			else
				throw new IpLocalInvalidoException();
		}
		return ipLocal;
	}
	
	public static String getEnderecoLocalConfiguracao() {
		if(USUARIO_PC.equals("gustavo"))
			return "C:\\Users\\Gustavo\\config.txt";
		else
			return "C:\\Users\\Aluno\\config.txt";
	}
	
	public static String getEnderecoLocalJAR() {
		if(USUARIO_PC.equals("gustavo"))
			return "C:\\Users\\Gustavo\\exec.jar";
		else
			return "C:\\Users\\Aluno\\exec.jar";
	}
	
	public boolean horaValida(long now) {
		if(now <= getFim() && now >= getInicio())
			return true;
		System.out.println(getFim());
		return false;
	}

	public long getInicio() {
		return inicio;
	}

	public void setInicio(long inicio) {
		this.inicio = inicio;
	}

	public long getFim() {
		return fim;
	}

	public void setFim(long fim) {
		this.fim = fim;
	}

	public String[] getRange() {
		return range;
	}

	public void setRange(String[] range) {
		this.range = range;
	}

}
