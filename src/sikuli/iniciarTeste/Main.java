package sikuli.iniciarTeste;

import java.io.IOException;
import java.net.InetAddress;

public class Main {

	public static void main(String[] args) {
		baixarConfig(); // baixar arquivo de configuracoes
		Config config = null;
		long horaAtual = 0;
		try {
			config = new Config();
			
			String ipLocal = InetAddress.getLocalHost().getHostAddress();
			ipLocal = "10.66.66.30";
			horaAtual = System.currentTimeMillis();
			// horaAtual = 1520254800000L;
			// System.out.println("IP local: " + ipLocal);
			System.out.println("IP dentro do range: " + config.contemIP(ipLocal));
			System.out.println("Hora dentro do range: " + config.horaValida(horaAtual));
			
			if(config.contemIP(ipLocal) && config.horaValida(horaAtual)) {
				baixarJAR(); // baixar executavel de teste
			}
		} catch (IOException | ConfiguracaoIncompletaException | IpLocalInvalidoException e1) {
			e1.printStackTrace();
			System.out.println("FIM");
			return;
		}
		
		// DELETAR ARQUIVOS
		//File arqConfig = new File(Config.getEnderecoLocalConfiguracao());
		//arqConfig.delete();
		System.out.println("Arquivo de configuracao deletado.");
		//File arqExec = new File(Config.getEnderecoLocalJAR());
		//arqExec.delete();
		System.out.println("Arquivo executavel deletado.");
	}
	
	public static void baixarConfig() {
		AcessoWeb.baixar(Config.getEnderecoLocalConfiguracao(), Config.enderecoRemotoConfiguracao);
	}
	
	public static void baixarJAR() {
		AcessoWeb.baixar(Config.getEnderecoLocalJAR(), Config.enderecoRemotoJAR);
	}

}
