package br.unirio;

public class Main
{
	public static void main(String args[])
	{
		final int numeroVertices = 5;
		OrdenacaoTopologica ord = new OrdenacaoTopologica();

		String nomeEntrada = "src/entrada.txt";
		//ord.montarGrafo(numeroVertices);

		//ord.realizaLeitura(nomeEntrada);

		ord.geraGrafo(numeroVertices, 0.13);
		ord.executa();


		/*

			Criar uma Lista com os Elementos que não tem predecessores

				 busca elementos sem predecessores
				p = prim; prim = NULL;
				WHILE p # NULL DO
				 q = p;
				 p = q->prox;
				 IF q->contador == 0 THEN
				  insere q na nova cadeia
				 q->prox = prim;
				 prim = q;
				 END
				END


				para cada elemento q da lista de elementos com zero
				predecessores
					 imprimir a chave de q;
				 		decrementar o número de elementos da lista (n);
				 			para cada elemento t da lista de sucessores de q
				 decrementar o contador do predecessor de t
				 se o contador de t se tornar zero,
				 insere este elemento no fim da lista de
				elementos com zero predecessores
				 remover o elemento t da lista de sucessores de q
				 remover o elemento q da lista de elementos com zero
				predecessores
				fim-para

		 */
	}
}
