package main;

public class Aluno {

    private final String nome;
    private final double nota1;
    private final double nota2;
    private final double nota3;
    private final double notaExame;

    public Aluno(String nome, double[] notas) {
        this.nome = nome;
        nota1 = notas[0];
        nota2 = notas[1];
        nota3 = notas[2];
        notaExame = notas[3];
    }

    public Aluno (String nome, double nota1, double nota2, double nota3, double notaExame) {
        this.nome = nome;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.notaExame = notaExame;
    }

    public String getNome() {
        return nome;
    }

    public double getNota1() {
        return nota1;
    }

    public double getNota2() {
        return nota2;
    }

    public double getNota3() {
        return nota3;
    }

    public double getNotaExame() {
        return notaExame;
    }
}
