public class Promedio{
    private double nota1, nota2, nota3;
    
    public Promedio(double nota1, double nota2, double nota3){
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
    }
    
    public double CalcularPromedio(){
        return (this.nota1 + this.nota2 + this.nota3) / 3;
    }
}
