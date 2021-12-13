public class Aluno {

    private String nome;
    private int nota1, nota2, nota3, notaExame;

    public Aluno(String nome, int[] notas) {
        this.nome = nome;
        nota1 = notas[0];
        nota2 = notas[1];
        nota3 = notas[2];
        notaExame = notas[3];
    }

    public String getNome() {
        return nome;
    }

    public int getNota1() {
        return nota1;
    }

    public int getNota2() {
        return nota2;
    }

    public int getNota3() {
        return nota3;
    }

    public int getNotaExame() {
        return notaExame;
    }
}
