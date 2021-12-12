public class Aluno {

    private String nome;
    private int nota1, nota2, nota3, nota_exame;

    public Aluno(String nome, int[] notas) {
        this.nome = nome;
        nota1 = notas[0];
        nota2 = notas[1];
        nota3 = notas[2];
        nota_exame = notas[3];
    }

}
