public class Main
{
	public static void main(String args[])
	{
		final int numeroVertices = 20;
		final double prob = 0.13;
		OrdenacaoTopologica ord = new OrdenacaoTopologica();
		//Colocar o caminho inteiro at� o arquivo de entrada
		String nomeEntrada = "C:\\Users\\Emidio\\Desktop\\Trabalho Final\\C�digo\\entrada.txt";



		//ord.realizaLeitura(nomeEntrada);
		//ord.geraGrafo(numeroVertices, 0.13);
		//ord.incializar();
		ord.executa(numeroVertices, prob);



	}
}
