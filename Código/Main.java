package br.unirio;

public class Main
{
	public static void main(String args[])
	{
		OrdenacaoTopologica ord = new OrdenacaoTopologica();
		
		String nomeEntrada = "C:\\Users\\Emidio\\Desktop\\Trabalho Final\\Código\\entrada.txt";
		
		ord.realizaLeitura(nomeEntrada);

		if(!ord.executa())
			System.out.println("O conjunto nao é parcialmente ordenado.");
		else
			System.out.println("O conjunto é parcialmente ordenado.");
	}
}
