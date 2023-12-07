package br.unirio;

public class Main
{
	public static void main(String args[])
	{
		final int numeroVertices = 20;
		final double prob = 0.13;
		OrdenacaoTopologica ord = new OrdenacaoTopologica();
		//Colocar o caminho inteiro até o arquivo de entrada
		String nomeEntrada = "C:\\Users\\Emidio\\Desktop\\Trabalho Final\\entrada.txt";


		//Método para ler o arquivo de entrada
		//ord.realizaLeitura(nomeEntrada);
		//ord.executaEntrada();

		//Método inicial para rodar os testes
		//ord.incializar();

		//Método para execução normal do código
		ord.executa(numeroVertices, prob);



	}
}
