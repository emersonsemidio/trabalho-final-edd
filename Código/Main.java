package br.unirio;

public class Main
{
	public static void main(String args[])
	{
		final int numeroVertices = 20;
		final double prob = 0.13;
		OrdenacaoTopologica ord = new OrdenacaoTopologica();
		//Colocar o caminho inteiro até o arquivo de entrada
		String nomeEntrada = "C:\\Users\\Emidio\\Desktop\\Trabalho Final\\Código\\entrada.txt";



		//ord.realizaLeitura(nomeEntrada);
		//ord.geraGrafo(numeroVertices, 0.13);
		//ord.incializar();
		ord.executa(numeroVertices, prob);



	}
}
